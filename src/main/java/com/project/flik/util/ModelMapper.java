package com.project.flik.util;

import com.project.flik.dto.MovieDto;
import com.project.flik.model.Movie;
import com.project.flik.payload.MovieResponse;

public class ModelMapper {
	
	public static MovieResponse mapMovieToMovieResponse(Movie movie, Long likes) {
		MovieResponse movieResponse = new MovieResponse();
		movieResponse.setId(movie.getId());
		movieResponse.setDescription(movie.getDescription());
		movieResponse.setEpisode(movie.getEpisode());
		movieResponse.setGenre(movie.getGenre());
		movieResponse.setImageUrl(movie.getImageUrl());
		movieResponse.setName(movie.getName());
		movieResponse.setRefranceMovie(movie.getRefranceMovie());
		movieResponse.setReleaseDate(movie.getReleaseDate());
		movieResponse.setRunTime(movie.getRunTime());
		movieResponse.setSeason(movie.getSeason());
		movieResponse.setUrl(movie.getUrl());
		movieResponse.setLikes(likes);
		return movieResponse;
	}
	
	public static Movie mapMovieDtoToMovie(MovieDto movieDto) {
		Movie movie = new Movie();
		movie.setDescription(movieDto.getOverview());
		movie.setGenre(movieDto.getGenre_ids());
		movie.setImageUrl(movieDto.getPoster_path());
		movie.setName(movieDto.getOriginal_title());
		movie.setReleaseDate(movieDto.getRelease_date());
		//movie.setRunTime(runTime);
		movie.setUrl("a");
		movie.setMovieId(Long.parseLong(movieDto.getId().toString()));
		return movie;
	}

}
