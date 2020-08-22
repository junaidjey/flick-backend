package com.project.flik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.flik.model.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long>{
	@Query("SELECT COUNT(f.id) FROM Follow f WHERE f.to.id = :to")
    Long findByToId(@Param("to") Long to);
	
	@Query("SELECT COUNT(f.id) FROM Follow f WHERE f.from.id = :from")
    Long findByFromId(@Param("from") Long from);
}
