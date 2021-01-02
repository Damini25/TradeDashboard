package com.trdsimul.gamemgmt.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trdsimul.gamemgmt.dto.DashBResponse;
import com.trdsimul.gamemgmt.dto.ErrorMessage;
import com.trdsimul.gamemgmt.dto.UserCredDTO;
import com.trdsimul.gamemgmt.model.entity.UserDetails;
import com.trdsimul.gamemgmt.model.entity.UsersGameDetails;
import com.trdsimul.gamemgmt.service.UserService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user/")
public class UserController {

	@Autowired
	UserService userService;
	
	private static Logger LOG = LoggerFactory.getLogger(UserController.class);

	@PostMapping( path = "newuser", produces = "application/json", consumes = "application/json")
	public DashBResponse createNewUser(@RequestBody UserDetails inputDTO) {
		return null;
	}
	
	@PostMapping( path = "userlogin", produces = "application/json", consumes = "application/json")
	public DashBResponse loginUser(@RequestBody UserCredDTO credDTO) {
		LOG.info("Logging user .... Verifying credentials now.. ");
		DashBResponse dashBResponse = new DashBResponse();
		ErrorMessage message = new ErrorMessage();
		try {
			List<UserDetails> usersList = new ArrayList<UserDetails>();
			UserDetails user = userService.loginUser(credDTO);
			if (user != null) {
				LOG.info("User found. Check logged in status !!");
				dashBResponse.setSuccess(true);
				dashBResponse.setError(null);
				usersList.add(user);
				dashBResponse.setData(usersList);
			} else {
				LOG.error("Credentials are incorrect.. ");
				message.setKey("credentialsIncorrect");
				message.setValue(null);
				message.setErrorMessage("Either Userid or Password is incorrect !!");
				dashBResponse.setSuccess(false);
				dashBResponse.setData(null);
				dashBResponse.setError(message);
			}
		} catch (Exception e) {
			LOG.error("Unknown Exception Occurred !!" + e);
			message.setKey("unknownExceptionOccured");
			message.setErrorMessage(e.getMessage());
			dashBResponse.setSuccess(false);
			dashBResponse.setError(message);
			dashBResponse.setData(null);
		}
		return dashBResponse;
	}
	
	@GetMapping( path = "allGameUsers")
	public List<UsersGameDetails> allActiveUsersForGame(@RequestParam("gameId") Integer gameId) {
		LOG.info("Finding all active users for game : "+ gameId);
		List<UsersGameDetails> usersList =  new ArrayList<UsersGameDetails>();
		usersList = userService.allActiveUsersForGame(gameId);
		return usersList;	
	}
	
	
}
