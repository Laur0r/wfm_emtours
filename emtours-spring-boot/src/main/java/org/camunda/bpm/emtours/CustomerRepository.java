package org.camunda.bpm.emtours;

import org.camunda.bpm.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

  //  public customer findCustomerByEmail(String email);

}