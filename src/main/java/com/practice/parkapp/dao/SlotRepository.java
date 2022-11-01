package com.practice.parkapp.dao;

import java.util.List;

import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.practice.parkapp.model.Slot;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long>{
	
	Slot findBySlotNumber(SLOTNUMBERS slotNumber);
	Slot findBySlotNumber(String slotNumber);
	Slot findByBookTime(String bookTime);
	List<Slot> findBySlotStatus(SLOTSTATUS slotStatus);
	List<Slot> findBySlotType(SLOTTYPE slotType);
	Slot findByAssignedTo(String assignedTo);

	
	
	
	 
	 
	
	
	
	
	
	
	
	

}
