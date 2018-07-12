package org.camunda.bpm.roles;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

/**
 * Set role of Validate Information user task to Customer Relation Department
 */
public class ValidateInformationRole implements TaskListener{
	public void notify(DelegateTask arg0) {
		arg0.addCandidateGroup("CustomerRelationDep");
	}
}
