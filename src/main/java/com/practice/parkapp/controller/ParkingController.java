package com.practice.parkapp.controller;


import java.awt.Window;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.InvalidAttributesException;
import javax.swing.JWindow;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.parkapp.dao.SLOTNUMBERS;
import com.practice.parkapp.dao.SLOTSTATUS;
import com.practice.parkapp.dao.SLOTTYPE;
import com.practice.parkapp.dao.USERTYPE;
import com.practice.parkapp.model.Slot;
import com.practice.parkapp.model.User;
import com.practice.parkapp.service.MapValidationErrorService;
import com.practice.parkapp.service.ParkingService;

import ch.qos.logback.core.util.Duration;

@RestController
@CrossOrigin
@RequestMapping("/parkapp")
public class ParkingController {
	
	
	/**
	 * swagger url
	 * http://localhost:8085/swagger-ui.html#/parking-controller
	 */
	
	String FORMAT = "yyyy-MM-dd hh:mm:ss a";
	
	@Autowired
	ParkingService parkingService;
	
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	
	
//	private static final org.slf4j.Logger log = LoggerFactory.getLogger(ParkingController.class);
	
	
	
/*
 * -------------------------------------------------------------------------------------------------
 * */
	/*
	 * User Services
	 */
	/**
	 * Create user 
	 * @param userName
	 * @param passPhrase
	 * @param phone
	 * @param userType
	 * @return Sucess message
	 */
	
	@PostMapping("/users/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result)
	{
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationError(result);
//		log.info("errorMap"+errorMap);
		if (errorMap != null)
			return errorMap;
		
		parkingService.saveUser(user);
		
		return new ResponseEntity<User>(user,HttpStatus.CREATED);
	}
	
	@GetMapping("/users/all")
	public ResponseEntity<?> getAllUsers()
	{
		try
		{
			
			List<User> users = parkingService.findAllUsers();
//			log.info("Users list:"+users);
			System.out.println(users.get(0).getUserType());
			
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
//			return new ResponseEntity<USERTYPE>(users.get(0).getUserType(),HttpStatus.OK);
		}
		
		catch(Exception e)
		{
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}

//	@GetMapping("/users/all/{userType}")
	public ResponseEntity<?> getUsersByType(@PathVariable USERTYPE userType)
	{
		try{
			
			List<User> typeUsers=parkingService.findByUserType(userType);
			return new ResponseEntity<List<User>>(typeUsers,HttpStatus.FOUND);
			
		} catch(Exception e)
		{	return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);	}
		
	}
	
	
	/*
	 * Slot Services
	 */
/*-------------------------------------------------------------------------------------------*/
/*
	@PostMapping("/slots/create/{slotNumber}/{slotType}/{slotStatus}")
	public ResponseEntity<?> createSlot(
			@Valid @RequestBody Slot slot,
			@PathVariable("slotNumber") SLOTNUMBERS slotNumber,
			@PathVariable("slotType") SLOTTYPE slotType,
			@PathVariable("slotStatus") SLOTSTATUS slotStatus , 
			BindingResult result1)
	{
		
		
		try {
			ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationError(result1);
//			log.info("errorMap"+errorMap);
			if (errorMap != null)
				return errorMap;			
			/*
			 * We need to assign current time to the object and checkin time 
			 * +30 min from datetime.now
			 *-/
			ZonedDateTime dateTime = ZonedDateTime.now();
			ZonedDateTime dateTime30 = ZonedDateTime.now().plusMinutes(30);
			
			slot.setBookTime(dateTime);
			slot.setCheckinTimeInterval(dateTime30);
			
//			return new ResponseEntity<String>(SLOTSTATUS.valueOf(slotStatus.toString().toUpperCase()).toString(),HttpStatus.OK);
//			System.out.println(dateTime.format(DateTimeFormatter.ofPattern(FORMAT)));
//			System.out.println(dateTime30.format(DateTimeFormatter.ofPattern(FORMAT)));
			
			Slot savedSlot=parkingService.saveSlot(slot);
			return new ResponseEntity<Slot>(savedSlot,HttpStatus.CREATED);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
		
	}
*/
	
/*-------------------------------------------------------------------------------------------*/	
	/**
	 * /slots/status/{slotStatus}
	 * @param slotStatus
	 * @return
	 */
	@GetMapping("/slots/status/{slotStatus}")
	public ResponseEntity<?> findSlotbyStatus(@PathVariable("slotStatus") SLOTSTATUS slotStatus)
	{
		try {
			
			List<Slot> foundSlots=parkingService.findBySlotStatus(slotStatus);
			return new ResponseEntity<List<Slot>>(foundSlots,HttpStatus.FOUND);
			
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);

		}
	}


	/**
	 * /slots/type/{slotType}
	 * @param slotType
	 * @return
	 */
//	@GetMapping("/slots/type/{slotType}")
	public ResponseEntity<?> findSlotbyType(@PathVariable("slotType") SLOTTYPE slotType)
	{
		try {

			List<Slot> foundSlots=parkingService.findBySlotType(slotType);
			return new ResponseEntity<List<Slot>>(foundSlots,HttpStatus.FOUND);

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);

		}
	}


	/**
	 * /user/{name}/{usertype}
	 * @param name
	 * @param userType
	 * @return
	 */
	@PostMapping("/user/{name}/{usertype}")
	public ResponseEntity<?> bookSlot(@PathVariable("name") String name,@PathVariable("usertype") USERTYPE userType)
	{
		try {
			String slotNumber=null;
			Slot bookedSlot=null;
			/*
			 * Take usertype at time of login 
			 * develop two different approach for booking slot(changing the status to booked) from the available options
			 *
			 *Step 1: print out the available slots -DONE
			 *Step 2: check the user type and assign appropriate slot type  -DONE
			 *Step 3: if a reserved slot is not available then book a general slot for the  reserved user -DONE
			 *Step 4: return the slot object with user name parkingnumber(Slot number) e.g b001 -DONE
			 *Step 5: return the message with time to report before interval with formated date
			 *Step 6: one user can book multiple slots now if required change 
			 *Step 7:Done haha
			 *
			 *
			 */
			
			
			/*.Check for Previously booked.*/
			List<Slot> bookedSlots=parkingService.findBySlotStatus(SLOTSTATUS.BOOKED);
			
			List<String> bookedNames=new ArrayList<String>();
			for (Slot slot : bookedSlots) 
			{
				bookedNames.add(slot.getAssignedTo());
	
			}
			
			if(bookedNames.contains(name))
			{
				Slot s=parkingService.findSlotByName(name);
				String BookedSlotNumber=s.getSlotNumber();
				String reCheckinTime=s.getCheckinTimeInterval().format(DateTimeFormatter.ofPattern(FORMAT));
				return new ResponseEntity<String>("Hello "+
				name+"You Already Booked Slot Number:"
				+BookedSlotNumber+" Please Check-in Before:  "
				+reCheckinTime,HttpStatus.CONFLICT);
			}
			/*.Check for Previously booked.*/
			
			
			
			/* Declarations At time of booking*/
			ZonedDateTime bookTime,checkinTime;
			//15 minutes Extra Time before 50%capacaty full;
			long extraMinutes=15;
			
			bookTime=ZonedDateTime.now();
			
			int totalCountOfSlots=parkingService.getTotalNumberOfSlots();
			
			
			List<Slot> availableSlots=parkingService.findBySlotStatus(SLOTSTATUS.AVAILABLE);
			
			/**
			 * Check whether the number of available counts is half of the total slots
			 * in this case total is 10 and if
			 *  only 5 slots (50% capacity is full) are available then remove extra wait time  then
			 * the checkinTime has to be =ZonedDateTime.now().plusMinutes(30+extraMinutes);
			 * else if more slots are available the we will =ZonedDateTime.now().plusMinutes(30);
			 * we dont need to partially book something because it will be the same thing as booked
			 * but with checkin option after someone checks in we will make the checkout option visible and 
			 * make the slot available again
			 * there is no point in partially book something not when the user can unbook by his own and he also has extra time
			 * 
			 */
			//This means that total available slots are more than 50% so we can give extra checkin time
			if(availableSlots.size()>(totalCountOfSlots/2))
			{
				checkinTime=ZonedDateTime.now().plusMinutes(30+extraMinutes);
			}
			//this means than less than 50% of capacity is available we cannot give extra time
			else
			{
				checkinTime=ZonedDateTime.now().plusMinutes(30);
			}
			/* Declarations At time of booking*/
			
			String formatCheckinTime=checkinTime.format(DateTimeFormatter.ofPattern(FORMAT));
			/*Booking Based on user Type RESERVED user is Prioritized for booking */
			for (Slot slot : availableSlots) {
				//try to book slot based on user TYPE(RESERVED)
				if(slot.getSlotType().toString()==userType.toString())
				{
					bookedSlot=parkingService.updateSlot(slot.getSlotNumber(),bookTime,checkinTime,name,SLOTTYPE.RESERVED,SLOTSTATUS.BOOKED);
					return new ResponseEntity<String>(
							"RESERVED SLOT NUMBER:  "+
					bookedSlot.getSlotNumber()+
					" is Booked!:: Please Check in Before:  "+formatCheckinTime,HttpStatus.OK);
					
				}
				//Book general slot if reserved are not available again priority to Reserved
				else if(slot.getSlotStatus().equals(SLOTSTATUS.AVAILABLE))
				{
					
					bookedSlot=parkingService.updateSlot(slot.getSlotNumber(),bookTime,checkinTime, name, SLOTTYPE.GENERAL, SLOTSTATUS.BOOKED);
					return new ResponseEntity<String>(
							"RESERVED SLOTS ARE FULL BOOKING GENERAL SLOT-----"
							+ " "+"GENERAL SLOT NUMBER:  "+
								bookedSlot.getSlotNumber()+
								"   is Booked!:: Please Check in Before:  "+formatCheckinTime,HttpStatus.CREATED);
				}
				
				//Book general slot for general user
				else if(slot.getSlotStatus().equals(SLOTSTATUS.AVAILABLE) & slot.getSlotType().toString()==userType.GENERAL.toString())
				{
					
					bookedSlot=parkingService.updateSlot(slot.getSlotNumber(),bookTime,checkinTime, name, SLOTTYPE.GENERAL, SLOTSTATUS.BOOKED);
					return new ResponseEntity<String>(
							" BOOKING GENERAL SLOT-----"
							+ " "+"GENERAL SLOT NUMBER:  "+
								bookedSlot.getSlotNumber()+
								"  is Booked!:: Please Check in Before:  "+formatCheckinTime,HttpStatus.CREATED);
				}
				//Inform about slots unavailablity
				else
				{
					return new ResponseEntity<String>("Sorry"+name+"No Slots Available UNABLE TO BOOK",HttpStatus.BAD_REQUEST);
				}
			}
			
		
			return new ResponseEntity<Slot>(bookedSlot,HttpStatus.CONTINUE);
		
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
		
	}


	/**
	 * /slots/createall/{totalSlots}
	 * @param totalSlots
	 * @return
	 */
//	@PostMapping("/slots/createall/{totalSlots}")
	public ResponseEntity<?> intitalizeSlots(@PathVariable("totalSlots") Integer totalSlots)
	{
		try {
			List<Slot> predefinedSlots=parkingService.createDefinedSlots(totalSlots);
			
			if
			(predefinedSlots!=null)
			{
				return new ResponseEntity<List<Slot>>(predefinedSlots,HttpStatus.CREATED);
			}
			else
				return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
	}

	/**
	 * /users/{name}/checkin
	 * @param name
	 * @return
	 */
	@PostMapping("/users/{name}/checkin")
	public ResponseEntity<?> checkinSlot(@PathVariable("name") String name)
	{

		try {
			ZonedDateTime currentTime,expectedCheckinTime;
			List<Slot> slotsAvailableToCheckin=parkingService.findBySlotStatus(SLOTSTATUS.AVAILABLE);
			List<String> slotNamesWeCanCheckin=new ArrayList<String>();
			for (Slot slot : slotsAvailableToCheckin) {
				slotNamesWeCanCheckin.add(slot.getSlotNumber());

			}
			Slot slotToCheckin=parkingService.findSlotByName(name);
			if(slotToCheckin!=null)
			{
				currentTime=ZonedDateTime.now();
				expectedCheckinTime=slotToCheckin.getCheckinTimeInterval();
				//allow checkin if booked else dont allow
				//comparator value date , negative if less, positive if greater if current time dont exceed expected it will allow checkin 
				if((currentTime).compareTo(expectedCheckinTime)<0)
				{
					return new ResponseEntity<String>("Checked In Please Checkout after Leaving Slot: "
							+slotToCheckin.getSlotNumber(),HttpStatus.ACCEPTED);
				}
				else
				{
					/*
					 * This will prevent from unbooking even if user has time
					 * so we will check the time and decide whether someone can checkin or not
					 * if not then they have to rebook their slot 
					 * because the have given extra time included in checkin time interval
					 */
					parkingService.unBookSlot(slotToCheckin);
					return new ResponseEntity<String>("Please Book Another Slot Checkin Time Passed Away"
							+slotNamesWeCanCheckin+" are Available Slots",HttpStatus.BAD_REQUEST);

				}
			}
			else
			{
				return new ResponseEntity<String>("No Slots To Checkin",HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>("Exception Description: "+e.getMessage(),HttpStatus.BAD_REQUEST);
		}

		
	}

	/**
	 * 
	 * @param name
	 * @return nothing
	 */
	@PostMapping("/users/{name}/checkout")
	public ResponseEntity<?> checkoutSlot(@PathVariable("name") String name)
	{
		List<Slot> slotToCheckOutList=(List<Slot>) parkingService.findSlotByName("mister");
		parkingService.unBookSlot(slotToCheckOutList.get(0));
		
		return new ResponseEntity<String>("Unbooked:",HttpStatus.OK);
		
	}

	/**
	 * 
	 * @return Sucess message after Deleting all the slots
	 */
//	@PostMapping("/users/slots/clearall")
	public ResponseEntity<?> clearSlots()
	{
	
		try {
			return new ResponseEntity<String>("Success! "+parkingService.clearSlots(),HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>("Exception Occured:!"+e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
	}
}
