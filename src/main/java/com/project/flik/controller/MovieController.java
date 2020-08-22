package com.project.flik.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.project.flik.dto.MovieDto;
import com.project.flik.dto.MoviesDto;
import com.project.flik.model.LastScrapped;
import com.project.flik.model.Movie;
import com.project.flik.payload.ApiResponse;
import com.project.flik.payload.MovieResponse;
import com.project.flik.payload.PagedResponse;
import com.project.flik.payload.ScrapMovieVariable;
import com.project.flik.repository.LastScrappedRepository;
import com.project.flik.repository.MovieRepository;
import com.project.flik.security.CurrentUser;
import com.project.flik.security.UserPrincipal;
import com.project.flik.service.MovieService;
import com.project.flik.util.AppConstants;
import com.project.flik.util.ModelMapper;

@Controller
@RequestMapping("/api")
public class MovieController {
	@Autowired
	private MovieService movieService;

//	@GetMapping("/scrapMovies")
//	public ResponseEntity<?> imdbScrap() throws IOException, ParseException {
//		movieService.imdbScrap();
//		return ResponseEntity.ok(new ApiResponse(true, "Movie scraped succesfully"));
//	}

	@GetMapping("/movies")
	public @ResponseBody PagedResponse<MovieResponse> getMovies(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
		return movieService.getAllMovies(page - 1, size);
	}

	@PostMapping("/{movieId}/likes")
	@PreAuthorize("hasRole('USER')")
	public MovieResponse castLike(@CurrentUser UserPrincipal currentUser, @PathVariable Long movieId) {
		return movieService.castLikesAndGetUpdatedMovie(movieId, currentUser);
	}

	@GetMapping("/scrapMovieDB")
	public ResponseEntity<?> movieDbScrap(){
		movieService.scrapGenre();
		movieService.movieDbScrap();
		return ResponseEntity.ok(new ApiResponse(true, "Movie scraped succesfully"));
	}
	
	public void movieDbScraps(){
		movieService.scrapGenre();
		movieService.movieDbScrap();
	}
} 