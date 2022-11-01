/**
 * 
 */
package com.practice.parkapp.model;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.UniqueElements;

import com.practice.parkapp.dao.SLOTNUMBERS;
import com.practice.parkapp.dao.SLOTSTATUS;
import com.practice.parkapp.dao.SLOTTYPE;

/**
 * @author kmahendr
 *
 */
@Entity
@Table(name = "parking_slots")
public class Slot {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	Long slotID;

	@Column(unique = true)
	@NotNull(message="Please Provide BETWEEN B001(0) to B120(119)")
	private String slotNumber;

	
	@NotNull
	@Column(name="booktimes")
	private ZonedDateTime bookTime;
	
	@NotNull
	@Column(name="checkins")
	private ZonedDateTime checkinTimeInterval;
	
	@NotNull(message="Please Provide Specify general(0) or reserved(1)")
	@Column(name="slottypes")
	private SLOTTYPE slotType;
	
	@NotNull(message="Please Provide status Booked(0) or available(1)")
	@Column(name="slot_status")
	private SLOTSTATUS slotStatus;

	@Column(name="assigned_to")
	private String assignedTo; 
	
	
	/**
	 * We need to create a entry without the name to which the slot is alloted
	 *  for the default lets take 10 slots later we will add 120
	 * 
	 * booked slots will be have a join in table to 
	 * search all the slots whose status is booked or available
	 * return a list of slots object with 
	 * slotnumber , booktime , checkintimeinterval(30minutes from booktime) , slottype , slottypestatus
	 * 
	 * e.g (b001,"")
	 * 		(b002,"rvndr")
	 */
	
	

	
	/**
	 * @param slotNumber
	 * @param bookTime
	 * @param checkinTimeInterval
	 * @param slotType
	 * @param slotStatus
	 * @param assignedTo
	 */
	public Slot(@NotNull(message = "Please Provide BETWEEN B001(0) to B120(119)") String slotNumber,
			@NotNull ZonedDateTime bookTime, @NotNull ZonedDateTime checkinTimeInterval,
			@NotNull(message = "Please Provide Specify general(0) or reserved(1)") SLOTTYPE slotType,
			@NotNull(message = "Please Provide status Booked(0) or available(1)") SLOTSTATUS slotStatus,
			String assignedTo) {
		super();
		this.slotNumber = slotNumber;
		this.bookTime = bookTime;
		this.checkinTimeInterval = checkinTimeInterval;
		this.slotType = slotType;
		this.slotStatus = slotStatus;
		this.assignedTo = assignedTo;
	}


	
	

	/**
	 * Default Constructor
	 */
	private Slot() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * @return the slotID
	 */
	public Long getSlotID() {
		return slotID;
	}
	/**
	 * @param slotID the slotID to set
	 */
	public void setSlotID(Long slotID) {
		this.slotID = slotID;
	}

	/**
	 * @return the slotNumber
	 */
	public String getSlotNumber() {
		return slotNumber;
	}

	/**
	 * @param slotNumber the slotNumber to set
	 */
	public void setSlotNumber(String slotNumber) {
		this.slotNumber = slotNumber;
	}

	/**
	 * @return the bookTime
	 */
	public ZonedDateTime getBookTime() {
		return bookTime;
	}

	/**
	 * @param bookTime the bookTime to set
	 */
	public void setBookTime(ZonedDateTime bookTime) {
		this.bookTime = bookTime;
	}

	/**
	 * @return the checkinTimeInterval
	 */
	public ZonedDateTime getCheckinTimeInterval() {
		return checkinTimeInterval;
	}

	/**
	 * @param checkinTimeInterval the checkinTimeInterval to set
	 */
	public void setCheckinTimeInterval(ZonedDateTime checkinTimeInterval) {
		this.checkinTimeInterval = checkinTimeInterval;
	}

	/**
	 * @return the slotType
	 */
	public SLOTTYPE getSlotType() {
		return slotType;
	}

	/**
	 * @param slotType the slotType to set
	 */
	public void setSlotType(SLOTTYPE slotType) {
		this.slotType = slotType;
	}

	/**
	 * @return the slotStatus
	 */
	public SLOTSTATUS getSlotStatus() {
		return slotStatus;
	}

	/**
	 * @param slotStatus the slotStatus to set
	 */
	public void setSlotStatus(SLOTSTATUS slotStatus) {
		this.slotStatus = slotStatus;
	}





	/**
	 * @return the assignedTo
	 */
	public String getAssignedTo() {
		return assignedTo;
	}





	/**
	 * @param assignedTo the assignedTo to set
	 */
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	
	

}
