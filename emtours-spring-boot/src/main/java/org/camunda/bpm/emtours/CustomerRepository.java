package org.camunda.bpm.emtours;

import java.util.Date;
import java.util.List;

import org.camunda.bpm.entities.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for the Entity class Customer which controls the database queries
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

	/**
	 * Custom method which finds customers in the database based on their name and birthday
	 * @param custName customer name
	 * @param custBirthday customer birthday
	 * @return list of customers with this name and birthday
	 */
	 @Query("SELECT c FROM Customer c WHERE LOWER(c.name) = LOWER(:name) AND (c.birthday) = (:birthday)")
	    public List<Customer> find(@Param("name") String custName, @Param("birthday") Date custBirthday);


}