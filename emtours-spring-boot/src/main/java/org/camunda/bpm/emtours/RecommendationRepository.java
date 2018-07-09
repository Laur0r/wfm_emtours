package org.camunda.bpm.emtours;

import org.camunda.bpm.entities.Recommendation;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for the Entity class Recommendation which controls the database queries
 */
public interface RecommendationRepository extends CrudRepository<Recommendation, Integer> {
}
