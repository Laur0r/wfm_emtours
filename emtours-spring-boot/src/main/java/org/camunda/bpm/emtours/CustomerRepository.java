package org.camunda.bpm.emtours;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

  //  public customer findCustomerByEmail(String email);

}