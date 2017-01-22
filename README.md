# app-hotel-booking
app-hotel-booking

#Introduction 
This is a java project to get the room availability on a perticular date and do the booking.

#Implementation details
	+ the application uses core Java SE8
	+ the project compiles on a machine with Maven 3.3 and Java SE8 
	+ In memory collection is used to persist the rooms and bookings data	
	
#Set-up
	+ Maven build project using command mvn:install which compiles the application, creates a jar and runs the Junit tests.
	+ Run java command to run this jar java –jar jarname from /target folder

##Dependencies
Included dependency for junit 

##How to Run	
	+ Run Junit tests under BookingManagerImplTest for functional scenarios and edge cases 
	+ Run Junit tests under BookingManagerImplBulkTest for multithreading scenarios
 
#To do
	+ More Junit tests can be included
	+ More exception handling can be put in place
