package com.project.flik.service;

import java.io.IOException;

import com.project.flik.payload.MovieResponse;
import com.project.flik.payload.PagedResponse;
import com.project.flik.payload.PollResponse;
import com.project.flik.security.UserPrincipal;

public interface MovieService {
	void scrapMovie(String url, String searchDate) throws IOException;

	PagedResponse<MovieResponse> getAllMovies(int page, int size);

	MovieResponse castLikesAndGetUpdatedMovie(Long movieId, UserPrincipal currentUser);
}
