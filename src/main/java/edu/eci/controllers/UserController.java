package edu.eci.controllers;

import edu.eci.models.User;
import edu.eci.services.contracts.IUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private IUserServices userServices;

	@ResponseBody
	@RequestMapping(value = "/useri",method = RequestMethod.GET)
	public ResponseEntity<?> getUser() {
		try {
			return new ResponseEntity<>(userServices.list(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getStackTrace(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/user",method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@RequestParam UUID id) {
		try {
			return new ResponseEntity<>(userServices.get(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getStackTrace(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> createUser(@RequestBody User user) {
		try {
			return new ResponseEntity<>(userServices.create(user), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		try {
			userServices.update(user);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> deleteUser(@RequestBody UUID id) {
		try {
			User user = userServices.get(id);
			userServices.delete(user);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
}
