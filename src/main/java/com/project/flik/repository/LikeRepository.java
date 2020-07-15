package com.project.flik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.flik.model.Like;

public interface LikeRepository  extends JpaRepository<Like, Long>{
	@Query("SELECT count(l.id) FROM Like l WHERE l.movie.id = :movieId")
    Long countByMovieId(@Param("movieId") Long movieId);
	
	@Query("SELECT COUNT(l.id) from Like l where l.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);
}
