package com.cfs.BookMyShow.service;

import com.cfs.BookMyShow.dto.*;
import com.cfs.BookMyShow.exception.ResourceNotFoundException;
import com.cfs.BookMyShow.exception.SeatUnavailableException;
import com.cfs.BookMyShow.model.*;
import com.cfs.BookMyShow.repository.BookingRepository;
import com.cfs.BookMyShow.repository.ShowRepository;
import com.cfs.BookMyShow.repository.ShowSeatRepository;
import com.cfs.BookMyShow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public BookingDto createBooking(BookingRequsetDto bookingRequsetDto){
        User user = userRepository.findById(bookingRequsetDto.getUserId()).orElseThrow(()-> new ResourceNotFoundException("user not found"));

        Show show = showRepository.findById(bookingRequsetDto.getShowId()).orElseThrow(()->new ResourceNotFoundException("Show not Found"));

        List<ShowSeat> selectSeats =showSeatRepository.findAllById(bookingRequsetDto.getSeatIds());

        for(ShowSeat seat :selectSeats)
        {
            if(!"AVAILABLE".equals(seat.getStatus())){
                throw  new SeatUnavailableException("seat"+seat.getSeat().getSeatNumber()+" is not abaibale");
            }
            seat.setStatus("LOCKED");
        }
        showSeatRepository.saveAll(selectSeats);
        Double totalAmount= selectSeats.stream()
                .mapToDouble(ShowSeat::getPrice)
                .sum();


        //payment
        Payment payment = new Payment();
        payment.setAmount(totalAmount);
        payment.setPaymentTime(LocalDateTime.now());
        payment.setStatus("SUCCESS");
        payment.setTransactionId(UUID.randomUUID().toString());

        //new booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setBookingTime(LocalDateTime.now());
        booking.setStatus("show");
        booking.setBookingNumber(String.valueOf(totalAmount));
        booking.setBookingNumber(UUID.randomUUID().toString());
        booking.setPayment(payment);

        Booking saveBooking = bookingRepository.save(booking);

        selectSeats.forEach(seat->{
            seat.setStatus("BOOKED");
            seat.setBooking(saveBooking);
        });
        showSeatRepository.saveAll(selectSeats);
        return mapToBookingDto(saveBooking,selectSeats);
    }


    public BookingDto getBookingById(Long id){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found"));
        List<ShowSeat> seats = showSeatRepository.findAll()
                .stream()
                .filter(seat ->
                        seat.getBooking() != null &&
                                seat.getBooking().getId() == booking.getId()
                )
                .collect(Collectors.toList());

        return mapToBookingDto(booking,seats);
    }

    private BookingDto getBookingByNumber(String bookingNumber){
        Booking booking = bookingRepository.findByBookingNumber(bookingNumber)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found"));
        List<ShowSeat> seats = showSeatRepository.findAll()
                .stream()
                .filter(seat ->
                        seat.getBooking() != null &&
                                seat.getBooking().getId() == booking.getId()
                )
                .collect(Collectors.toList());

        return mapToBookingDto(booking,seats);
    }

    private List<BookingDto> getBookingByUserId(Long userId) {

        List<Booking> bookings = bookingRepository.findByUser_Id(userId);

        return bookings.stream()
                .map(booking -> {

                    List<ShowSeat> seats = showSeatRepository.findAll()
                            .stream()
                            .filter(seat ->
                                    seat.getBooking() != null &&
                                            seat.getBooking().getId() == booking.getId()
                            )
                            .collect(Collectors.toList());

                    return mapToBookingDto(booking, seats);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public BookingDto cancelBooking(Long id){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Booking not found"));
        booking.setStatus("CANCELLED");

        List<ShowSeat> seats= showSeatRepository.findAll()
                .stream()
                .filter(seat ->
                        seat.getBooking() != null &&
                                seat.getBooking().getId() == booking.getId()
                )
                .collect(Collectors.toList());
        seats.forEach(seat->{
            seat.setStatus("AVAILABLE");
            seat.setBooking(null);
        });

        if(booking.getPayment()!=null){
            booking.getPayment().setStatus("REFUNDED");
        }

        Booking updateBooking = bookingRepository.save(booking);
        showSeatRepository.saveAll(seats);

        return mapToBookingDto(updateBooking,seats);
    }


    private BookingDto mapToBookingDto(Booking booking,List<ShowSeat> seats){
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setBookingNumber(booking.getBookingNumber());
        bookingDto.setBookingTime(booking.getBookingTime());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setTotalAmount(Double.parseDouble(booking.getTotalAmount()));


        //user
        UserDto  userDto = new UserDto();
        userDto.setId(booking.getUser().getId());
        userDto.setName(booking.getUser().getName());
        userDto.setEmail(booking.getUser().getEmail());
        userDto.setPhoneNumber(booking.getUser().getPhoneNumber());
        bookingDto.setUser(userDto);


        ShowDto showDto = new ShowDto();
        showDto.setId(booking.getShow().getId());
        showDto.setStartTime(booking.getShow().getStartTime());
        showDto.setEndTime(booking.getShow().getEndTime());

        MoiveDto moiveDto = new MoiveDto();
        moiveDto.setId(booking.getShow().getMovie().getId());
        moiveDto.setTitle(booking.getShow().getMovie().getTitle());
        moiveDto.setDescription(booking.getShow().getMovie().getDescription());
        moiveDto.setLanguage(booking.getShow().getMovie().getLanguage());
        moiveDto.setGenre(booking.getShow().getMovie().getGenre());
        moiveDto.setDescription(booking.getShow().getMovie().getDescription());
        moiveDto.setReeaseDate(booking.getShow().getMovie().getReleaseDate());
        moiveDto.setPosterUrl(booking.getShow().getMovie().getPosterUrl());
        showDto.setMoive(moiveDto);

        ScreenDto screenDto = new ScreenDto();
        screenDto.setId(booking.getShow().getScreen().getId());
        screenDto.setName(booking.getShow().getScreen().getName());
        screenDto.setTotalSeats(booking.getShow().getScreen().getTotalSeats());

        TheaterDto theaterDto = new TheaterDto();
        theaterDto.setId(bookingDto.getShow().getScreen().getTheater().getId());
        theaterDto.setName(bookingDto.getShow().getScreen().getTheater().getName());
        theaterDto.setAddress(bookingDto.getShow().getScreen().getTheater().getAddress());
        theaterDto.setCity(bookingDto.getShow().getScreen().getTheater().getCity());
        theaterDto.setTotalSeats(bookingDto.getShow().getScreen().getTheater().getTotalSeats());

        screenDto.setTheater(theaterDto);
        showDto.setScreen(screenDto);
        bookingDto.setShow(showDto);

        List<ShowSeatDto> seatDtos=seats.stream()
                .map(seat->{
                    ShowSeatDto showDto1 = new ShowSeatDto();
                    showDto1.setId(seat.getId());
                    showDto1.setStatus(seat.getStatus());
                    showDto1.setPrice(seat.getPrice());

                    SeatDto baseseatDto = new SeatDto();
                    baseseatDto.setId(seat.getSeat().getId());
                    baseseatDto.setSeatNumber(seat.getSeat().getSeatNumber());
                    baseseatDto.setSeatType(seat.getSeat().getSeatType());
                    baseseatDto.setBasePrice(seat.getSeat().getBasePrice());
                    showDto1.setSeat(baseseatDto);
                    return showDto1;
                })
                .collect(Collectors.toList());
        bookingDto.setSeats(seatDtos);

        if(booking.getPayment()!=null){
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setId(booking.getPayment().getId());
            paymentDto.setAmount(booking.getPayment().getAmount());
            paymentDto.setPaymentMethod(booking.getPayment().getPaymentMethod());
            paymentDto.setPaymenttime(booking.getPayment().getPaymentTime());
            paymentDto.setStatus(booking.getPayment().getStatus());
            paymentDto.setTransactionId(booking.getPayment().getTransactionId());
            bookingDto.setPayment(paymentDto);


        }
        return bookingDto;





    }
}
