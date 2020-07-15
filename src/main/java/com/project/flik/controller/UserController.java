package com.project.flik.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.project.flik.exception.ResourceNotFoundException;
import com.project.flik.model.User;
import com.project.flik.payload.PagedResponse;
import com.project.flik.payload.PollResponse;
import com.project.flik.payload.UserIdentityAvailability;
import com.project.flik.payload.UserProfile;
import com.project.flik.payload.UserSummary;
import com.project.flik.repository.LikeRepository;
import com.project.flik.repository.PollRepository;
import com.project.flik.repository.UserRepository;
import com.project.flik.repository.VoteRepository;
import com.project.flik.security.CurrentUser;
import com.project.flik.security.UserPrincipal;
import com.project.flik.service.PollService;
import com.project.flik.service.UserService;
import com.project.flik.service.storage.StorageService;
import com.project.flik.util.AppConstants;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PollRepository pollRepository;

	@Autowired
	private VoteRepository voteRepository;

	@Autowired
	private PollService pollService;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LikeRepository likeRepository;

	List<String> files = new ArrayList<String>();
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@GetMapping("/user/me")
	//@PreAuthorize("hasRole('USER')")
	public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
		UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(),
				currentUser.getName());
		return userSummary;
	}

	@GetMapping("/user/checkUsernameAvailability")
	public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
		Boolean isAvailable = !userRepository.existsByEmail(username);
		return new UserIdentityAvailability(isAvailable);
	}

	@GetMapping("/user/checkEmailAvailability")
	public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
		Boolean isAvailable = !userRepository.existsByEmail(email);
		return new UserIdentityAvailability(isAvailable);
	}

	@GetMapping("/users/{email}")
	public UserProfile getUserProfile(@PathVariable(value = "email") String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

		long pollCount = pollRepository.countByCreatedBy(user.getId());
		long voteCount = voteRepository.countByUserId(user.getId());

		long likeCount = likeRepository.countByUserId(user.getId());
		
		UserProfile userProfile = new UserProfile(user.getId(), user.getEmail(), user.getFullName(),
				user.getCreatedAt(), likeCount);

		return userProfile;
	}

	@GetMapping("/users/{username}/polls")
	public PagedResponse<PollResponse> getPollsCreatedBy(@PathVariable(value = "username") String username,
			@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
		return pollService.getPollsCreatedBy(username, currentUser, page, size);
	}

	@GetMapping("/users/{username}/votes")
	public PagedResponse<PollResponse> getPollsVotedBy(@PathVariable(value = "username") String username,
			@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
		return pollService.getPollsVotedBy(username, currentUser, page, size);
	}

	@GetMapping(value = "/user/get-image", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody Resource getImage(@CurrentUser UserPrincipal currentUser){
		return userService.getImage(currentUser.getId());
	}
	
	
//	@GetMapping(value = "/user/get-image")
//	 @ResponseBody
//	public  ResponseEntity<Resource> getImage(@CurrentUser UserPrincipal currentUser){
//		//return userService.getImage(currentUser.getId());
//		 Resource file = userService.getImage(currentUser.getId());
//		return ResponseEntity.ok()
//		        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//		        .body(file);
//	}
	
//	@GetMapping(value = "/user/get-image", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//	public @ResponseBody byte [] getImage(@CurrentUser UserPrincipal currentUser) throws IOException{
//		Resource resource = userService.getImage(currentUser.getId());
//		InputStream input = resource.getInputStream();
//		File file = resource.getFile();
//		BufferedImage bufferimage = ImageIO.read(file);
//	      ByteArrayOutputStream output = new ByteArrayOutputStream();
//	      ImageIO.write(bufferimage, "jpg", output );
//	      byte [] data = output.toByteArray();
//	      return data;
//	}
	
	@GetMapping("/user/getallfiles")
	public ResponseEntity<List<String>> getListFiles(@CurrentUser UserPrincipal currentUser) {
		List<String> fileNames = files
				.stream().map(fileName -> MvcUriComponentsBuilder
						.fromMethodName(UserController.class, "getFile", fileName).build().toString())
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(fileNames);
	}
	
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = storageService.loadFile(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
}
