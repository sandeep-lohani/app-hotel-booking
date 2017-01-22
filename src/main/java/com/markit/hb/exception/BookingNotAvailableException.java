package com.markit.hb.exception;

public class BookingNotAvailableException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public BookingNotAvailableException(String message){
		super(message);
	}

}
