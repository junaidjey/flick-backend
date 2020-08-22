package com.project.flik.service;

import org.springframework.core.io.Resource;

import com.project.flik.payload.FollowResponce;
import com.project.flik.security.UserPrincipal;

public interface UserService {

	Resource getImage(Long id);

	FollowResponce follow(Long userId, UserPrincipal currentUser);

}