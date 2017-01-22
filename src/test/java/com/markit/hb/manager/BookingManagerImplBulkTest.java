package com.markit.hb.manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.markit.hb.exception.BookingNotAvailableException;
import com.markit.hb.model.Booking;
import com.markit.hb.model.Day;
import com.markit.hb.model.Guest;
import com.markit.hb.model.HotelDB;
import com.markit.hb.model.Room;

public class BookingManagerImplBulkTest {

	// Mock
	private BookingManager bm = new BookingManagerImpl();

	// Mock
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@BeforeClass
	public static void setUpClass() throws Exception {
		for (int i = 100; i < 200; i++) {
			HotelDB.addRoom(i);
		}
	}

	@Before
	public void setUpTest() throws Exception {
		Date today = dateFormat.parse("2017-01-21");
		List<Booking> bookingList = new ArrayList<>();
		Booking booking = new Booking(today, new Guest("Lohani"), new Room(104));
		bookingList.add(booking);
		HotelDB.getBookingDetails().put(new Day(today), bookingList);
	}

	@Test
	public void testAddBooking() throws Exception {
		Date day = dateFormat.parse("2017-01-21");
		System.out.println(day + " available rooms before booking are " + bm.getAvailableRooms(day));
		for (int k = 0; k < 10; k++) {
			Thread t = new Thread(
					() -> {
						for (int j = 0; j < 10; j++) {
						try {
							Random rand = new Random(); 
							Integer value = rand.nextInt(101) + 100; 
							bm.getAvailableRooms(day);
							bm.isRoomAvailable(value, getNextdate(day, j));
							bm.addBooking("Smith" + j, value, day);
						} catch (BookingNotAvailableException e) {
							System.out.println(e.getMessage());
							Assert.assertTrue(e.getMessage().contains(
									" already booked ") || e.getMessage().contains("enter valid room number"));
						}
						}
					});
			Thread t2 = new Thread(
					() -> {
						for (int j = 0; j < 10; j++) {
						try {
							Random rand = new Random(); 
							Integer value = rand.nextInt(101) + 100; 
							bm.getAvailableRooms(day);
							bm.isRoomAvailable(value, getNextdate(day, j));
							bm.addBooking("John" + j, value,
									getNextdate(day, j + 1));
						} catch (BookingNotAvailableException e) {
							System.out.println(e.getMessage());
							Assert.assertTrue(e.getMessage().contains(
									" already booked ") || e.getMessage().contains("enter valid room number"));
						}
						}
					});
			t.start();
			t2.start();
			t.join();
			t2.join();
		}
		System.out.println(day + " available rooms before booking are " + bm.getAvailableRooms(day));
	}

	private Date getNextdate(Date today, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DATE, i);
		Date nextDate = cal.getTime();
		return nextDate;
	}

}
