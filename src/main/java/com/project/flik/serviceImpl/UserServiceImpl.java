package com.project.flik.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.flik.model.Follow;
import com.project.flik.model.User;
import com.project.flik.payload.FollowResponce;
import com.project.flik.repository.FollowRepository;
import com.project.flik.repository.UserRepository;
import com.project.flik.security.UserPrincipal;
import com.project.flik.service.UserService;
import com.project.flik.service.storage.StorageService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StorageService storageService;

	@Autowired
	private FollowRepository followRepository;

	@Override
	public Resource getImage(Long id) {
		Optional<User> opUser = userRepository.findById(id);
		User user2 = new User();
		opUser.ifPresent(user -> {
			user2.setImageName(user.getImageName());
			user2.setProfilePic(user.getProfilePic());
		});
		Resource resource = null;
		try {
			resource = storageService.loadFile(user2.getImageName(), user2.getProfilePic());
		} catch (NullPointerException e) {
			// TODO: handle exception
		}
		return resource;
	}

	@Override
	public FollowResponce follow(Long userId, UserPrincipal currentUser) {
		User formUser = userRepository.findById(currentUser.getId())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		User toUser = userRepository.findById(userId)
				.orElseThrow(() -> new UsernameNotFoundException("User which you follow not found"));
		;

		Follow follow = new Follow();

		follow.setFrom(formUser);
		follow.setTo(toUser);

		FollowResponce followResponce = new FollowResponce();

		if (formUser.getId() == toUser.getId()) {
			followResponce.setMessage("You Can not follow Yourself ");
		} else {
			try {
				followRepository.save(follow);
				followResponce.setMessage("You are following to " + toUser.getFullName());
			} catch (Exception e) {
				followResponce.setMessage("You are already following to " + toUser.getFullName());
			}
		}

		return followResponce;
	}

}
