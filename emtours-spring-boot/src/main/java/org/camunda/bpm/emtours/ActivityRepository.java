package org.camunda.bpm.emtours;

import java.util.Date;
import java.util.List;

import org.camunda.bpm.entities.Activity;
import org.camunda.bpm.entities.Customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for the Entity class Activity which controls the database queries
 */
@Repository
public interface ActivityRepository extends CrudRepository<Activity, Integer> {

}
