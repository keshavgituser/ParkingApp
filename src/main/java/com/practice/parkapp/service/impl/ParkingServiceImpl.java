/**
 * 
 */
package com.practice.parkapp.service.impl;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.type.ZonedDateTimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.parkapp.dao.SLOTNUMBERS;
import com.practice.parkapp.dao.SLOTSTATUS;
import com.practice.parkapp.dao.SLOTTYPE;
import com.practice.parkapp.dao.SlotRepository;
import com.practice.parkapp.dao.USERTYPE;
import com.practice.parkapp.dao.UserRepository;
import com.practice.parkapp.model.Slot;
import com.practice.parkapp.model.User;
import com.practice.parkapp.service.ParkingService;

/**
 * @author kmahendr
 *
 */
@Service
public class ParkingServiceImpl implements ParkingService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	SlotRepository slotRepo;
	
	
	String FORMAT = "yyyy-MM-dd hh:mm:ss a";

	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub

		List<User> foundUsers=userRepo.findAll();
		return foundUsers;
	}

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub

		User newUser=userRepo.save(user);
		return newUser;
	}

	@Override
	public List<User> findByUserType(USERTYPE userType) {
		// TODO Find all users by their type saved in db


		return userRepo.findByUserType(userType);
	}

	@Override
	public Slot saveSlot(Slot slot) {
		// TODO Auto-generated method stub
		return slotRepo.save(slot);
	}


	@Override
	public List<Slot> findBySlotStatus(SLOTSTATUS slotStatus) {
		// TODO Auto-generated method stub
		return slotRepo.findBySlotStatus(slotStatus);
	}

	@Override
	public List<Slot> findBySlotType(SLOTTYPE slotType) {
		// TODO Auto-generated method stub
		return slotRepo.findBySlotType(slotType);
	}

	@Override
	public  List<Slot> createDefinedSlots(Integer totalSlots) {
		// TODO Auto-generated method stub

		/*
		 * We create a method with total slots as slot objects without name but we will provide type and then we will make the  method static to create the slots during runtime 
		 * so that we can modify them when booking slots with username within the defined type of slots
		 * 		reserved slots=  ---- percentage of value here it is 20% of 10 
		 * int k = (int)(value*(percentage/100.0f));
		 */

		Integer totalSlots1=null,generalSlots=null,reservedSlots=null;
		float percentage=20.0f;

		totalSlots1=totalSlots;
		//		System.out.println(totalSlots1);

		reservedSlots=(int) (totalSlots1*(percentage/100.0f));
		generalSlots= Math.subtractExact(totalSlots1, reservedSlots);	
		/*System.out.println(reservedSlots);
		 * now generate 20% reserved slots and 80 % general slots out of total slots
		 * (reserved,general,total),(24,96,120)
		 * @param slotNumber * @param bookTime * @param checkinTimeInterval
		 * @param slotType * @param slotStatus * @param assignedTo
		 */

		/**
		 * Create Reserved Slots
		 */
		List<Slot> rSlotList=new ArrayList<Slot>();
		ZonedDateTime currentTime=ZonedDateTime.now();
		/*
		 * Creating  Reserved Slots
		 */
		for(int x=1;x<=reservedSlots;x++)
		{

			SLOTNUMBERS slotNumber=null;
			int slotindex=Math.abs(x-1);
			Slot newrSlot=new Slot(slotNumber.values()[slotindex].toString(),
					currentTime,
					currentTime,
					SLOTTYPE.RESERVED,
					SLOTSTATUS.AVAILABLE,
					"");
			slotRepo.save(newrSlot);
			rSlotList.add(newrSlot);
			newrSlot=null;
			
			if(x==reservedSlots)
			{
				for(int y=x+1;y<=totalSlots1;y++)
				{
					slotNumber=null;
					int slotindex1=Math.abs(y-1);
					Slot newgSlot=new Slot(slotNumber.values()[slotindex1].toString(),
							currentTime,
							currentTime,
							SLOTTYPE.GENERAL,
							SLOTSTATUS.AVAILABLE,
							"");
					slotRepo.save(newgSlot);
					rSlotList.add(newgSlot);
					newgSlot=null;
				}
			}
			
		}
		
		/*
		 * Creating GENERAL Slots
		 */
		


		for(Slot s:rSlotList)
		{
			System.out.println("Object:"+s);
		}
		return rSlotList;
	}


	@Override
	public boolean isReservedSlot(Slot slot) {
		// TODO Auto-generated method stub
		
		if(slot.getSlotType().equals(SLOTTYPE.RESERVED))
		{
			return true;
		}
		else
			return false;
	}

	
	@Override
	public boolean isGeneralSlot(Slot slot) {
		// TODO Auto-generated method stub
		if(slot.getSlotType().equals(SLOTTYPE.GENERAL))
			return true;
		else
			return false;
	}

	

	@Override
	public boolean isReservedUser(User user) {
		// TODO Auto-generated method stub
		if(user.getUserType().equals(USERTYPE.RESERVED))
			return true;
		else
			return false;
	}

	
	@Override
	public boolean isGeneralUser(User user) {
		// TODO Auto-generated method stub
		if(user.getUserType().equals(USERTYPE.GENERAL))
			return true;
		else
			return false;
	}
	@Override
	public boolean isAvailable(Slot slot) {
		// TODO Auto-generated method stub
		if(slot.getSlotStatus().equals(SLOTSTATUS.AVAILABLE))
			return true;
		else
			return false;
	}

	@Override
	public boolean isBooked(Slot slot) {
		// TODO Auto-generated method stub
		if(slot.getSlotStatus().equals(SLOTSTATUS.BOOKED))
			return true;
		else
			return false;
	}

	@Override
	public Slot updateSlot(String slotNumber,ZonedDateTime bookTime,ZonedDateTime checkinTime,String bookedBy, SLOTTYPE slotType, SLOTSTATUS booked) {
		// TODO Auto-generated method stub
		Slot oldSlot,newSlot;
		
		
		
		oldSlot=slotRepo.findBySlotNumber(slotNumber);
		oldSlot.setSlotType(slotType);
		oldSlot.setSlotStatus(booked);
		oldSlot.setAssignedTo(bookedBy);
		
		oldSlot.setBookTime(bookTime);
		oldSlot.setCheckinTimeInterval(checkinTime);
		newSlot=slotRepo.save(oldSlot);
		
		
		return newSlot;
	}

	@Override
	public User findUserByName(String name) {
		// TODO Auto-generated method stub
		return userRepo.findByUserName(name);
	}
	
	@Override
	public Slot findSlotByName(String name) {
		// TODO Auto-generated method stub
		return slotRepo.findByAssignedTo(name);
	}

	
	@Override
	public void unBookSlot(Slot slotToCheckOut) {
		// TODO Auto-generated method stub
		slotToCheckOut.setAssignedTo("");
		slotToCheckOut.setSlotStatus(SLOTSTATUS.AVAILABLE);
		slotToCheckOut.setBookTime(ZonedDateTime.now());
		slotToCheckOut.setCheckinTimeInterval(ZonedDateTime.now());
		slotRepo.save(slotToCheckOut);
	}
	
	
	
	@Override
	public String clearSlots() {
		// TODO Auto-generated method stub
		slotRepo.deleteAll();
		return "ALL the SLOTS ARE CLEARED !!! CREATE NEW SLOTS";
	}

	
	@Override
	public int getTotalNumberOfSlots() {
		// TODO Auto-generated method stub
		
		int totalSlotCount=(int) slotRepo.count();
		System.out.println(totalSlotCount);
		return totalSlotCount;
		
	}
	
	
	
}

