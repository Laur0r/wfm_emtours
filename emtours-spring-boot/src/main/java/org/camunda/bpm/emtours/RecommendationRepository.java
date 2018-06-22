package org.camunda.bpm.emtours;

import org.camunda.bpm.entities.Recommendation;
import org.springframework.data.repository.CrudRepository;

public interface RecommendationRepository extends CrudRepository<Recommendation, Long> {
}
