package com.driver.ui.controller;

import java.util.List;

import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.model.response.UserResponse;
import com.driver.service.UserService;
import com.driver.service.impl.UserServiceImpl;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserServiceImpl userService;

	@GetMapping(path = "/{id}")
	public UserResponse getUser(@PathVariable String id) throws Exception{
		UserDto userDto=userService.getUserByUserId(id);
		return UserResponse.builder().userId(userDto.getUserId()).email(userDto.getEmail()).firstName(userDto.getFirstName()).lastName(userDto.getLastName()).build();
	}

	@PostMapping()
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto=userService.createUser(UserDto.builder().email(userDetails.getEmail()).firstName(userDetails.getFirstName()).lastName(userDetails.getLastName()).build());
		return UserResponse.builder().userId(userDto.getUserId()).email(userDto.getEmail()).firstName(userDto.getFirstName()).lastName(userDto.getLastName()).build();
	}

	@PutMapping(path = "/{id}")
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto=userService.updateUser(id,UserDto.builder().email(userDetails.getEmail()).firstName(userDetails.getFirstName()).lastName(userDetails.getLastName()).build());
		return UserResponse.builder().userId(userDto.getUserId()).email(userDto.getEmail()).firstName(userDto.getFirstName()).lastName(userDto.getLastName()).build();
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) throws Exception{
		userService.deleteUser(id);
		OperationStatusModel operationStatusModel=OperationStatusModel.builder().operationResult(String.valueOf(RequestOperationStatus.SUCCESS)).operationName(String.valueOf(RequestOperationName.DELETE)).build();
		return operationStatusModel;
	}

	@GetMapping()
	public List<UserResponse> getUsers(){
		List<UserResponse> userResponses = null;
		for(UserDto userDto: userService.getUsers()){
			userResponses.add(UserResponse.builder().userId(userDto.getUserId()).lastName(userDto.getLastName()).firstName(userDto.getFirstName()).email(userDto.getEmail()).build());
		}
		return userResponses;
	}

}
