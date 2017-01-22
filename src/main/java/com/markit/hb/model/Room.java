package com.markit.hb.model;

import com.markit.hb.exception.BookingNotAvailableException;


public class Room {

	private Integer roomNumber = null;

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public Room(Integer roomNumber) {
		super();
		if (HotelDB.getAllRooms().stream()
				.anyMatch(i -> i.equals(roomNumber)))
			this.roomNumber = roomNumber;
		else
			throw new BookingNotAvailableException(roomNumber + " is invalid, please enter valid room number. available room in the hotels are :"
					+ HotelDB.getAllRooms().toString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((roomNumber == null) ? 0 : roomNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		if (roomNumber == null) {
			if (other.roomNumber != null)
				return false;
		} else if (!roomNumber.equals(other.roomNumber))
			return false;
		return true;
	}

}