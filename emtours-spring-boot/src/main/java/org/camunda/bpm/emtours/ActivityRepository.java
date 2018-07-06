package org.camunda.bpm.emtours;

import java.util.Date;
import java.util.List;

import org.camunda.bpm.entities.Activity;
import org.camunda.bpm.entities.Customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository
public interface ActivityRepository extends CrudRepository<Activity, Integer> {
	
	 @Query("SELECT c FROM Customer c WHERE LOWER(c.name) = LOWER(:name) AND (c.birthday) = (:birthday)")
	    public List<Customer> find(@Param("name") String custName, @Param("birthday") Date custBirthday);

}
