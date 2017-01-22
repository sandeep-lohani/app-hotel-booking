package com.markit.hb.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HotelDB 
{
    private static Map<Day, List<Booking>> hotelBookingsMap = new ConcurrentHashMap<>();
    
    private static List<Integer> allRooms = new ArrayList<Integer>();
    
    static {
    	Integer[] array = {}; 
    	allRooms.addAll(Arrays.asList(array));
    }

    public static List<Integer> getAllRooms() {
		return allRooms;
	}

	public static Map<Day, List<Booking>> getBookingDetails(){
        return hotelBookingsMap;
    }  
	
	public static void addRoom(Integer room) {
		allRooms.add(room);
	}
	
	public static void addRooms(List<Integer> rooms) {
		allRooms.addAll(rooms);
	}
}
