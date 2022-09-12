package com.rewards.rewardcalculater.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.rewardcalculater.model.Customer;
import com.rewards.rewardcalculater.model.Rewards;
import com.rewards.rewardcalculater.service.RewardServiceImpl;

@RestController
@RequestMapping("/customers")
public class RewardCalculaterController {
	

	@Autowired
	private RewardServiceImpl rewardService;

	 @GetMapping(value = "/{id}/rewards",produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Object> getPointsByCustomerId(@PathVariable("id") Long id){
	        Customer customer = rewardService.getCustomerByCustomerId(id.intValue());
	        if(customer == null)
	        {
	        	Map<String,String> errorMap=new HashMap<>();
	        	errorMap.put("errorMessage", "No Member Found");
		        return new ResponseEntity<>(errorMap,HttpStatus.OK);
	        }
	        Rewards rewards = rewardService.getPointsByCustomerId(id);
	        return new ResponseEntity<>(rewards,HttpStatus.OK);
	    }
  
}
