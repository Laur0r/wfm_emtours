package org.camunda.bpm.roles;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

/**
 * Set role of BookHoliday user task to Holiday Recommendation Department
 */
public class BookHolidayRole implements TaskListener{
	public void notify(DelegateTask arg0) {
		arg0.addCandidateGroup("HolidayRecommendationDep");
	}
}

