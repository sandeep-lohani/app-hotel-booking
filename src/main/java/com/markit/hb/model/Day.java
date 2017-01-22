package com.markit.hb.model;

import java.util.Date;

import com.markit.hb.exception.BookingNotAvailableException;

public final class Day {

	private final Date date;

	public Day(Date date) {
		super();
		if(date==null) throw new BookingNotAvailableException("date cannot be null, please enter valid date.");
		this.date = new Date(date.getTime());
	}

	public Date getDate() {
		return date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
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
		Day other = (Day) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}	
}
