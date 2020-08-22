package com.project.flik.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.flik.model.User;

/**
 * Created by junaid on 02/08/17.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

//	Optional<User> findByEmail(String username, String email);

	List<User> findByIdIn(List<Long> userIds);

//	Optional<User> findByEmail(String email);

	//Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	@Transactional
	@Modifying
	@Query("UPDATE User usr SET usr.profilePic = :profilePic, usr.imageName=:imageName WHERE usr.id = :id")
	void updateProfilePic(@Param("profilePic") String profilePic, @Param("imageName") String imageName,
			@Param("id") Long id);
}
