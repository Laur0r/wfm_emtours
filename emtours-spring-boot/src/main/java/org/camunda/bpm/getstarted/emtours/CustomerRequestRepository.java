package org.camunda.bpm.getstarted.emtours;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRequestRepository extends CrudRepository<CustomerRequest, Long> {
}
