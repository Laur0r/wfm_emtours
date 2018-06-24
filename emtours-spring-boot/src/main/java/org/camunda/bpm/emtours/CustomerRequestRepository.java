package org.camunda.bpm.emtours;

import org.camunda.bpm.entities.CustomerRequest;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRequestRepository extends CrudRepository<CustomerRequest, Integer> {
}
