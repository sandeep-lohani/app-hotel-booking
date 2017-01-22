package com.markit.hb.model;

import com.markit.hb.exception.BookingNotAvailableException;

public final class Guest {
	
	private final String lastName;
	private String firstName;
	
	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public Guest(String lastName, String firstName) {
		super();
		if(lastName==null || "".equals(lastName)) throw new BookingNotAvailableException("lastName cannot be null or empty");
		this.lastName = lastName;
		this.firstName = firstName;
	}
	
	public Guest(String lastName) {
		super();
		if(lastName==null || "".equals(lastName)) throw new BookingNotAvailableException("lastName cannot be null or empty");
		this.lastName = lastName;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
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
		Guest other = (Guest) obj;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

}
