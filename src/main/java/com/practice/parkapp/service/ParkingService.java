/**
 * 
 */
package com.practice.parkapp.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.practice.parkapp.dao.SLOTNUMBERS;
import com.practice.parkapp.dao.SLOTSTATUS;
import com.practice.parkapp.dao.SLOTTYPE;
import com.practice.parkapp.dao.USERTYPE;
import com.practice.parkapp.model.Slot;
import com.practice.parkapp.model.User;

/**
 * @author kmahendr
 *
 */
@Service
public interface ParkingService{

	/**
	 * 
	 * @return list of resgistered users
	 */
	public  List<User> findAllUsers();
	
	/**
	 * 
	 * @param user object
	 * @return user oject saved in db
	 */
	public User  saveUser(User user);
	
	/**
	 * 
	 * @param userType FROM(GENERAL,RESERVED)
	 * @return LIST OF USERS FROM one of the above types
	 */
	public List<User> findByUserType(USERTYPE userType);
	
	
	public User findUserByName(String name);
	
	public Slot findSlotByName(String name);
	
	/**
	 * 
	 * @param slot Object
	 * @return Object
	 */
	public Slot saveSlot(Slot slot);
	
	/**
	 * 
	 * @param slotStatus (BOOKED,AVAILABLE)
	 * @return List of Slots requested (One of the Above)
	 */
	public List<Slot> findBySlotStatus(SLOTSTATUS slotStatus);

	/**
	 * 
	 * @param slotType(GENERAL,RESERVED)
	 * @return list of slots by type
	 */
	public List<Slot> findBySlotType(SLOTTYPE slotType);
	
	/**
	 * 
	 * @param totalSlots number of slots to create
	 * @return List of created Defined Slots 
	 */
	public List<Slot> createDefinedSlots(Integer totalSlots);

	
	/**
	 * 
	 * @param slot object
	 * @return boolean value
	 */
	public boolean isReservedSlot(Slot slot);
	
	/**
	 * 
	 * @param slot object
	 * @return boolean value
	 */
	public boolean isGeneralSlot(Slot slot);
	
	
	/**
	 * 
	 * @param user object
	 * @return boolean value
	 */
	public boolean isReservedUser(User user);
	
	/**
	 * 
	 * @param user object
	 * @return boolean value
	 */
	public boolean isGeneralUser(User user);
	
	/**
	 * 
	 * @param slot object
	 * @return boolean value
	 */
	public boolean isAvailable(Slot slot);
	
	/**
	 * 
	 * @param slot object
	 * @return boolean value
	 */
	public boolean isBooked(Slot slot);

	public Slot updateSlot(String slotNumber,ZonedDateTime bookTime,ZonedDateTime checkinTime,String bookedBy, SLOTTYPE slotType, SLOTSTATUS booked);

	public void unBookSlot(Slot slotToCheckOut);

	public String clearSlots();

	public int getTotalNumberOfSlots();
}
