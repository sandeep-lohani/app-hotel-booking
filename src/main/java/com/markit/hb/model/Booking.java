package com.markit.hb.model;

import java.util.Date;


public final class Booking {
		
	private final Date date;
	
	private final Guest guest;
	
	private final Room room;

	public Booking(Date date, Guest guest, Room room) {
		super();
		this.date = new Date(date.getTime());
		this.guest = guest;
		this.room = room;
	}
	
	public Date getDate() {
		return date;
	}	

	public Guest getGuest() {
		return guest;
	}

	public Room getRoom() {
		return room;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((guest == null) ? 0 : guest.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
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
		Booking other = (Booking) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (guest == null) {
			if (other.guest != null)
				return false;
		} else if (!guest.equals(other.guest))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		return true;
	}
		
}
