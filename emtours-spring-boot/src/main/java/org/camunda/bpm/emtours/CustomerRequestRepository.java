package org.camunda.bpm.emtours;

import org.camunda.bpm.entities.CustomerRequest;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for the Entity class CustomerRequest which controls the database queries
 */
public interface CustomerRequestRepository extends CrudRepository<CustomerRequest, Integer> {
}
