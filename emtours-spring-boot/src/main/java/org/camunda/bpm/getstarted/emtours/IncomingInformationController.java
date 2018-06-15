package org.camunda.bpm.getstarted.emtours;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/information")
public class IncomingInformationController {
  
  @Autowired
  private ProcessEngine camunda;

  @RequestMapping(method=RequestMethod.POST)
  public void incomingInformationPOST(String name, String email) {
	  placeOrder(name, email);
  }

  /**
   * we need a method returning the {@link ProcessInstance} to allow for easier tests,
   * that's why I separated the REST method (without return) from the actual implementaion (with return value)
   */
  public ProcessInstance placeOrder(String name, String email) {
    return camunda.getRuntimeService().startProcessInstanceByKey(//
        "SampleProcess", //
        Variables //
          .putValue("name", name) //
          .putValue("email", email));
  }
}
