package com.project.flik.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.project.flik.model.User;
import com.project.flik.repository.UserRepository;
import com.project.flik.service.UserService;
import com.project.flik.service.storage.StorageService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StorageService storageService;
	
	@Override
	public Resource getImage(Long id) {
//		Optional<User> opUser= userRepository.findById(id);
//		User user2= new User();
//		opUser.ifPresent(user -> {
//			user2.setImageName(user.getImageName());
//			user2.setProfilePic(user.getProfilePic());
//		});
//	return storageService.loadFile(user2.getImageName(),user2.getProfilePic());
		return null;
	}

}
