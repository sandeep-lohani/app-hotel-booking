package com.markit.hb.manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

public class BookingManagerImplTest {

	//Mock
	private BookingManager bm = new BookingManagerImpl();
	
	//Mock
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		Integer rooms[] = {101,102,103,104,105};
		HotelDB.addRooms(Arrays.asList(rooms));	
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
	public void testIsRoomAvailable() throws Exception{
		Date today = dateFormat.parse("2017-01-21");
		Assert.assertTrue(bm.isRoomAvailable(101, today));
		Assert.assertTrue(bm.isRoomAvailable(105, today));
		Assert.assertFalse(bm.isRoomAvailable(104, today));		
	}

	@Test
	public void testAddBooking() throws Exception{
		Date today = dateFormat.parse("2017-01-21");
		Assert.assertTrue(bm.isRoomAvailable(101, today));
		bm.addBooking("Smith", 101, today);
		Assert.assertFalse(bm.isRoomAvailable(101, today));
		expectedException.expect(BookingNotAvailableException.class);
		expectedException.expectMessage(" is already booked for you ");
		bm.addBooking("Smith", 101, today);
	}
	
	@Test
	public void testAddBookingScenario() throws Exception{
		Date today = dateFormat.parse("2017-01-21");
		Assert.assertTrue(bm.isRoomAvailable(101, today));
		bm.addBooking("Smith", 101, today);
		Assert.assertFalse(bm.isRoomAvailable(101, today));
		expectedException.expect(BookingNotAvailableException.class);
		expectedException.expectMessage(" is unavailable for booking or already booked for ");
		bm.addBooking("John", 101, today);
	}

	@Test
	public void testGetAvailableRooms() throws Exception{
		Date today = dateFormat.parse("2017-01-21");
		Integer[] remainingToday = {101,102,103,105};
		Iterable<Integer> availablerooms = bm.getAvailableRooms(today);
		availablerooms.forEach(i-> Assert.assertTrue(Arrays.asList(remainingToday).contains(i)));
	}
	
	@Test
	public void testMixedScenario() throws Exception{
		Date today = dateFormat.parse("2017-01-21");
		Assert.assertFalse(bm.isRoomAvailable(104, today));	
		Assert.assertTrue(bm.isRoomAvailable(105, today));
		bm.addBooking("Smith", 105, today);
		Assert.assertFalse(bm.isRoomAvailable(105, today));
		Date nextdate = getNextdate(today,1);
		Assert.assertTrue(bm.isRoomAvailable(105, nextdate));
		bm.addBooking("Smith", 105, nextdate);
		Assert.assertFalse(bm.isRoomAvailable(105, nextdate));
		Integer[] remainingToday = {101,102,103};
		Integer[] remainingNext = {101,102,103,104};
		Iterable<Integer> availablerooms = bm.getAvailableRooms(today);
		availablerooms.forEach(i-> Assert.assertTrue(Arrays.asList(remainingToday).contains(i)));
		availablerooms = bm.getAvailableRooms(nextdate);
		availablerooms.forEach(i ->Assert.assertTrue(Arrays.asList(remainingNext).contains(i)));
	}
	
	@Test
	public void testEdgeCasesDate() throws Exception{
		Date today = null;
		expectedException.expect(BookingNotAvailableException.class);
		expectedException.expectMessage("date cannot be null");
		bm.getAvailableRooms(today);
	}
	
	@Test
	public void testEdgeCasesDateTwo() throws Exception{
		Date today = null;
		expectedException.expect(BookingNotAvailableException.class);
		expectedException.expectMessage("date cannot be null");
		bm.isRoomAvailable(101, today);
	}
	
	@Test
	public void testEdgeCasesName() throws Exception{
		Date today = dateFormat.parse("2017-01-21");
		Assert.assertTrue(bm.isRoomAvailable(101, today));
		expectedException.expect(BookingNotAvailableException.class);
		expectedException.expectMessage("lastName cannot be null or empty");
		bm.addBooking("", 101, today);
	}

	private Date getNextdate(Date today, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add( Calendar.DATE, i );
		Date nextDate = cal.getTime();
		return nextDate;
	}

}
