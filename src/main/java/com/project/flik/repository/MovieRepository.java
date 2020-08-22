package com.project.flik.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.flik.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{

	Movie findByName(String text);
	
	Movie findByUrl(String url);

	@Query("SELECT m from Movie m where m.genre IN :genre")
	Page<Movie> findByGenre(@Param("genre") ArrayList<String> genre, Pageable pageable);
	
	@Query("SELECT m from Movie m where :genre in elements(m.genre)")
	List<Movie> findByGenre(@Param("genre") String genre);

	Movie findByMovieId(Long movie);
}
