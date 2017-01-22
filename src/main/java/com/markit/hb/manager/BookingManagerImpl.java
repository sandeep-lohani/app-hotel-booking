package com.markit.hb.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.markit.hb.exception.BookingNotAvailableException;
import com.markit.hb.model.Booking;
import com.markit.hb.model.Day;
import com.markit.hb.model.Guest;
import com.markit.hb.model.HotelDB;
import com.markit.hb.model.Room;

public class BookingManagerImpl implements BookingManager{

	@Override
	public boolean isRoomAvailable(Integer room, Date date) {
		List<Booking> bookings = HotelDB.getBookingDetails().get(new Day(date));
		if(bookings==null) return true;
		synchronized (bookings) {
			for (Booking b : bookings) {
				if (b.getRoom().getRoomNumber().equals(room)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void addBooking(String guest, Integer room, Date date) {
		Day day = new Day(date);
		Booking booking = new Booking(date, new Guest(guest), new Room(room));
		checkRoomAvailability(day, booking);		
		Map<Day, List<Booking>> hotelBookingsMap = HotelDB.getBookingDetails();		
		List<Booking> bookings = hotelBookingsMap.get(day);
		if (bookings==null || bookings.isEmpty()) {
			List<Booking> bookingList = new ArrayList<>();			
			bookingList.add(booking);
			bookingList = hotelBookingsMap.putIfAbsent(day, bookingList);
			if(bookingList!=null){
				addBookingToList(bookingList, day, booking);
			}
		} else {			
			addBookingToList(bookings, day, booking);
		}
	}
	
	@Override
	public Iterable<Integer> getAvailableRooms(Date date) {
        List<Booking> bookings = HotelDB.getBookingDetails().get(new Day(date));
		List<Integer> availableList = new ArrayList<>();
		List<Integer> bookedList = new ArrayList<>();
		for (Integer i : HotelDB.getAllRooms()) {
			availableList.add(i);
		}
		synchronized (bookings) {
			bookings.forEach(b -> bookedList.add(b.getRoom().getRoomNumber()));
		}
		availableList.removeAll(bookedList);
		return availableList;
	}

	private void checkRoomAvailability(Day day, Booking booking) {
		boolean available = isRoomAvailable(booking.getRoom().getRoomNumber(), booking.getDate());
		if(!available) {
			List<Booking> bookings = HotelDB.getBookingDetails().get(day);
			synchronized (bookings) {
			bookings.forEach(b -> {
				if(b.getGuest().equals(booking.getGuest()))  
					throw new BookingNotAvailableException("Room " + booking.getRoom().getRoomNumber() +  " is already booked for you " + booking.getGuest().getLastName() + " for " + day.getDate());
				});
			}
			throw new BookingNotAvailableException("Room " + booking.getRoom().getRoomNumber() + " is unavailable for booking or already booked for " + day.getDate());
		}
	}

	private void addBookingToList(List<Booking> bookings, Day day, Booking booking) {
		synchronized (bookings) {
			checkRoomAvailability(day, booking);
			bookings.add(booking);
			HotelDB.getBookingDetails().put(day, bookings);
		}
	}
}
