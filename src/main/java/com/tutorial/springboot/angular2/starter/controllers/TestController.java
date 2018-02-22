package com.tutorial.springboot.angular2.starter.controllers;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zlatan on 2/22/18.
 */
@RestController
public class TestController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping(value="/testStart", method= RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public void startMyProcess(){
        runtimeService.startProcessInstanceByKey("myProcess");
        System.out.println("Broj " + runtimeService.createProcessInstanceQuery().count() + " je broj instanci procesa zadatak2.");

        //DataInit.initGroupsYml();
        //DataInit.initUsersYml();

        long usersNum = identityService.createUserQuery().count();
        long groupsNum = identityService.createGroupQuery().count();

        System.out.println("Broj korisnika je: " + usersNum + ", a broj grupa: " + groupsNum + ".");
    }

    @RequestMapping(value="/testDeployAndStart", method = RequestMethod.GET)
    public ResponseEntity deployAndStart(){
        String filename = "processes/Test.bpmn";
        Deployment deployment = repositoryService.createDeployment().addClasspathResource(filename).deploy();
        String deploymentId = deployment.getId();

        System.out.println("Deployment id: " + deploymentId);

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        String processDefinitionId = processDefinition.getId();
        runtimeService.startProcessInstanceById(processDefinitionId);

        return new ResponseEntity<>("STARTED", HttpStatus.OK);
    }

    @RequestMapping(value="/testRegisterDeploy", method = RequestMethod.GET)
    public ResponseEntity deployRegister(){
        String filename = "processes/Registracija.bpmn";
        Deployment deployment = repositoryService.createDeployment().addClasspathResource(filename).deploy();
        String deploymentId = deployment.getId();

        System.out.println("Deployment id: " + deploymentId);

//		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
//		String processDefinitionId = processDefinition.getId();
//		runtimeService.startProcessInstanceById(processDefinitionId);

        return new ResponseEntity<>("DEPLOYED REGISTER", HttpStatus.OK);
    }


}
