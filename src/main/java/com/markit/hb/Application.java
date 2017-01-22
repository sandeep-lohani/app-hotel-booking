package com.markit.hb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import com.markit.hb.manager.BookingManager;
import com.markit.hb.manager.BookingManagerImpl;

/**
 * Created by sandeep
 */
public class Application {

	private static final Logger log = Logger.getLogger("Application.class");

	/**
	 * The entry point, starts the application.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		log.info("Hotel booking app starting");
		try {
			BookingManager bm = new BookingManagerImpl();			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date today = dateFormat.parse("2012-03-28");
			System.out.println(bm.isRoomAvailable(101, today)); // outputs true
			bm.addBooking("Smith", 101, today);
			System.out.println(bm.isRoomAvailable(101, today)); // outputs false
			} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("Hotel booking finished");
	}

}
