package org.camunda.bpm.roles;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

public class InvokeLegalActionRole implements TaskListener{

		public void notify(DelegateTask arg0) {
			arg0.addCandidateGroup("FinanceDep");
		}

}
