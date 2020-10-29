package com.example.demo.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.JwtRequest;
import com.example.demo.model.JwtResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.JwtUserDetailsService;
import org.apache.commons.codec.binary.Base64;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	@Autowired
	UserRepository userService;

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {

		if (userService.findByEmail(user.getEmail()) == null) {

			return ResponseEntity.ok(userDetailsService.save(user));
		} else {

			return new ResponseEntity<String>("{\"message\":\"Email Already Used\"}", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/get-user-detail")
	public User getUserDetail(HttpServletRequest request) {

		String username = jwtTokenUtil.getUsernameFromToken(request.getHeader("Authorization").substring(7));

		User current = userService.findByEmail(username);
		current.setPassword(null);
		current.setRoles(null);

		return current;

	}

	@PostMapping("/update-user")
	public User updateUser(@RequestBody User user, HttpServletRequest request) {
		String username = jwtTokenUtil.getUsernameFromToken(request.getHeader("Authorization").substring(7));

		User current = userService.findByEmail(username);

		current.setName(user.getName());
		current.setAddress(user.getAddress());
		return userService.save(current);

	}

	@GetMapping("/checkifadmin")
	public ResponseEntity<?> testDecodeJWT(HttpServletRequest request) {
		

		if (request.getHeader("Authorization") != null) {
			String username = jwtTokenUtil.getUsernameFromToken(request.getHeader("Authorization").substring(7));

			User current = userService.findByEmail(username);

			if (current.getRoles().contains("ADMIN")) {

				return new ResponseEntity<>("{\"role\":\"ADMIN\"}", HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("{\"role\":\"OTHERS\"}", HttpStatus.OK);

	}

	@GetMapping("/getusername")
	public String getUserName(HttpServletRequest request) {

		if (request.getHeader("Authorization") != null) {
			String username = jwtTokenUtil.getUsernameFromToken(request.getHeader("Authorization").substring(7));
			return username;
		} else {
			return "INVALID JWT";
		}

	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
