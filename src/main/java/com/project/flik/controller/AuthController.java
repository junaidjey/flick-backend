package com.project.flik.controller;

import java.net.URI;
import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.flik.exception.AppException;
import com.project.flik.model.Role;
import com.project.flik.model.RoleName;
import com.project.flik.model.User;
import com.project.flik.payload.ApiResponse;
import com.project.flik.payload.JwtAuthenticationResponse;
import com.project.flik.payload.LoginRequest;
import com.project.flik.payload.SignUpRequest;
import com.project.flik.repository.RoleRepository;
import com.project.flik.repository.UserRepository;
import com.project.flik.security.JwtTokenProvider;
import com.project.flik.service.storage.StorageService;
import com.project.flik.util.Utill;

/**
 * Created by junaid on 02/08/17.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	StorageService storageService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		User user = new User(signUpRequest.getFullName(), signUpRequest.getEmail(), signUpRequest.getPassword());

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role userRole = null;
		if (signUpRequest.getRole() != null) {
			userRole = roleRepository.findByName(RoleName.valueOf(signUpRequest.getRole()))
					.orElseThrow(() -> new AppException("User Role not set."));
		} else {
			userRole = roleRepository.findByName(RoleName.ROLE_USER)
					.orElseThrow(() -> new AppException("User Role not set."));
		}

		user.setRoles(Collections.singleton(userRole));

		User result = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{email}")
				.buildAndExpand(result.getEmail()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
	}

//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@PostMapping("/signup")
//	public ResponseEntity<?> registerUser(@Valid SignUpRequest signUpRequest) {
//		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//			return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
//		}
//
//		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//			return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
//		}
//
//		// Creating user's account
//		User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getUsername(),
//				signUpRequest.getEmail(), signUpRequest.getPassword(),
//				Utill.stringToUtillDate(signUpRequest.getBirthDate()), signUpRequest.getPhoneNumber());
//
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//		Role userRole = roleRepository.findByName(RoleName.valueOf(signUpRequest.getJobType()))
//				.orElseThrow(() -> new AppException("User Role not set."));
//
//		user.setRoles(Collections.singleton(userRole));
//
//		User result = userRepository.save(user);
//
//		String ProfilePicPath = storageService.saveUserProfilePic(signUpRequest.getProfilePic(), user.getId());
//		result.setProfilePic(ProfilePicPath);
//		userRepository.updateProfilePic(ProfilePicPath, signUpRequest.getProfilePic().getOriginalFilename(), result.getId());
//		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
//				.buildAndExpand(result.getUsername()).toUri();
//
//		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
//	}
}
