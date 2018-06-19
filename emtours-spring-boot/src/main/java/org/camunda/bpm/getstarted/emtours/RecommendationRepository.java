package org.camunda.bpm.getstarted.emtours;

import org.springframework.data.repository.CrudRepository;

public interface RecommendationRepository extends CrudRepository<Recommendation, Long> {
}
