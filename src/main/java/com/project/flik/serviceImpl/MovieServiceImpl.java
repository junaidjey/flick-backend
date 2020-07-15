package com.project.flik.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.flik.exception.BadRequestException;
import com.project.flik.exception.ResourceNotFoundException;
import com.project.flik.model.LastScrapped;
import com.project.flik.model.Like;
import com.project.flik.model.Movie;
import com.project.flik.model.User;
import com.project.flik.payload.MovieResponse;
import com.project.flik.payload.PagedResponse;
import com.project.flik.repository.LastScrappedRepository;
import com.project.flik.repository.LikeRepository;
import com.project.flik.repository.MovieRepository;
import com.project.flik.repository.UserRepository;
import com.project.flik.security.UserPrincipal;
import com.project.flik.service.MovieService;
import com.project.flik.util.AppConstants;
import com.project.flik.util.ModelMapper;

@Service
public class MovieServiceImpl implements MovieService {
	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LikeRepository likeRepository;
	
	@Autowired
	private LastScrappedRepository lastScrappedRepository;

	private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

	public void scrapMovie(String url, String searchDate) throws IOException {

		Document doc = Jsoup.connect(url).get();
		Elements movies = doc.getElementsByClass("lister-item mode-advanced");
		for (Element movie : movies) {
			Elements movieTags = movie.getElementsByClass("lister-item-content");
			for (Element movieTag : movieTags) {
				Element movieTitles = movieTag.getElementsByClass("lister-item-header").first();
				Element getNumber = movieTitles.getElementsByClass("lister-item-index unbold text-primary").first();
				String movieNumber = getNumber.text().replace(".", "");
				if (movieTitles.text().contains("Episode:")) {
					Element serialName = movieTag.getElementsByTag("a").get(0);
					String serialUrl = serialName.attr("href");
					Element episode = movieTag.getElementsByTag("a").get(1);
					String episodeUrl = episode.attr("href");
					try {
						Movie movied = movieRepository.findByUrl(serialUrl);
						if (movied == null) {
							Movie moviesss = saveMovie(serialUrl, movieNumber, searchDate);
							if (moviesss != null) {
								saveEpisode(episodeUrl, moviesss.getId(), movieNumber, searchDate);
							}

						} else {
							Movie episodeName = movieRepository.findByUrl(episodeUrl);
							if (episodeName == null) {
								saveEpisode(episodeUrl, movied.getId(), movieNumber, searchDate);
							}
						}

					} catch (NullPointerException e) {
					}

				} else {
					Element serialName = movieTag.getElementsByTag("a").get(0);
					String serialUrl = serialName.attr("href");
					Movie movied = movieRepository.findByUrl(serialUrl);
					if (movied == null) {
						saveMovie(serialUrl, movieNumber, searchDate);
					}
				}
			}

		}
	}

	private void saveEpisode(String episode, Long movied, String movieNumber, String searchDate) throws IOException {
		Document doc = Jsoup.connect("https://www.imdb.com" + episode).get();

		Element titleOverview = doc.getElementsByClass("title-overview").first();
		Element movieDoc = titleOverview.getElementsByClass("heroic-overview").first();
		Element movieShortDesc = movieDoc.getElementsByClass("title_wrapper").first();
		Element movieName = movieShortDesc.getElementsByTag("h1").first();
		Movie movie = new Movie();
		movie.setName(movieName.text());

		Element subText = movieShortDesc.getElementsByClass("subtext").first();
		try {
			Element time = subText.getElementsByTag("time").first();
			movie.setRunTime(time.text());
		} catch (NullPointerException e) {
		}

		movie.setUrl(episode);
		movie.setRefranceMovie(movied);
		Element movieOverview = movieDoc.getElementsByClass("plot_summary").first();
		Element movieDesc = movieOverview.getElementsByClass("summary_text").first();

		if (movieDesc.text().equals("Add a Plot »")) {
			movie.setDescription("");
		} else {
			movie.setDescription(movieDesc.text());
		}

		Element movieCoverDiv = movieDoc.getElementsByClass("poster").first();
		try {
			Element poster = movieCoverDiv.select("img").first();
			String movieCoverUrl = poster.attr("src");
			movie.setImageUrl(movieCoverUrl);
		} catch (NullPointerException e) {
			// TODO: handle exception
		}

		try {
			Element bpHeading = movieDoc.getElementsByClass("bp_heading").first();
			String pageMovie[] = bpHeading.text().split(" | ");
			movie.setSeason(pageMovie[1]);
			movie.setEpisode(pageMovie[4]);
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}

		Movie movie1 = movieRepository.save(movie);

		LastScrapped lastScrapped = new LastScrapped();
		lastScrapped.setMovie(movie1);
		lastScrapped.setListNumber(movieNumber);
		lastScrapped.setSearchDate(searchDate);
		lastScrappedRepository.save(lastScrapped);
	}

	@SuppressWarnings("null")
	private Movie saveMovie(String serialUrl, String movieNumber, String searchDate) throws IOException {
		Document doc = Jsoup.connect("https://www.imdb.com" + serialUrl).get();
		Element movieDoc = doc.getElementsByClass("heroic-overview").first();
		Element movieShortDesc = movieDoc.getElementsByClass("title_wrapper").first();
		Element movieName = movieShortDesc.getElementsByTag("h1").first();
		Movie movie = new Movie();
		movie.setName(movieName.text());
		movie.setUrl(serialUrl);
		Element movieOverview = movieDoc.getElementsByClass("plot_summary").first();
		Element movieDesc = movieOverview.getElementsByClass("summary_text").first();

		if (movieDesc.text().equals("Add a Plot »")) {
			movie.setDescription("");
		} else {
			movie.setDescription(movieDesc.text());
		}

		Element movieCoverDiv = movieDoc.getElementsByClass("poster").first();
		try {
			Element poster = movieCoverDiv.select("img").first();
			String movieCoverUrl = poster.attr("src");
			movie.setImageUrl(movieCoverUrl);
		} catch (NullPointerException e) {
			// TODO: handle exception
		}

		Element subText = movieShortDesc.getElementsByClass("subtext").first();
		Elements subTextATags = subText.getElementsByTag("a");
		ArrayList<String> genre = new ArrayList<>();
		for (Element subTextATag : subTextATags) {

			if (subTextATag.attr("title").contains("See more release dates")) {
				movie.setReleaseDate(subTextATag.text());
			}
			if (!subTextATag.attr("title").contains("See more release dates")) {
				try {
					genre.add(subTextATag.text());
				} catch (NullPointerException e) {
				}

			}
		}

		try {
			Element time = subText.getElementsByTag("time").first();
			movie.setRunTime(time.text());
		} catch (NullPointerException e) {
		}
		movie.setGenre(genre);
		
		Movie movie1 = movieRepository.save(movie);

		LastScrapped lastScrapped = new LastScrapped();
		lastScrapped.setMovie(movie1);
		lastScrapped.setListNumber(movieNumber);
		lastScrapped.setSearchDate(searchDate);
		lastScrappedRepository.save(lastScrapped);
		return movie1;
	}

	private void validatePageNumberAndSize(int page, int size) {
		if (page < 0) {
			throw new BadRequestException("Page number cannot be less than zero.");
		}

		if (size > AppConstants.MAX_PAGE_SIZE) {
			throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
		}
	}

	@Override
	public PagedResponse<MovieResponse> getAllMovies(int page, int size) {
		validatePageNumberAndSize(page, size);

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
		Page<Movie> movies = movieRepository.findAll(pageable);

		if (movies.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), movies.getNumber(), movies.getSize(),
					movies.getTotalElements(), movies.getTotalPages(), movies.isLast());
		}
		List<MovieResponse> movieResponse = movies.map(movie -> {
			return ModelMapper.mapMovieToMovieResponse(movie, likeRepository.countByMovieId(movie.getId()));
		}).getContent();
		return new PagedResponse<>(movieResponse, movies.getNumber(), movies.getSize(), movies.getTotalElements(),
				movies.getTotalPages(), movies.isLast());
	}

	@Override
	public MovieResponse castLikesAndGetUpdatedMovie(Long movieId, UserPrincipal currentUser) {
		Movie movie = movieRepository.findById(movieId)
				.orElseThrow(() -> new ResourceNotFoundException("Movie", "id", movieId));

		User user = userRepository.getOne(currentUser.getId());

		Like like = new Like();
		like.setMovie(movie);
		like.setUser(user);

		try {
			like = likeRepository.save(like);
		} catch (DataIntegrityViolationException ex) {
			logger.info("User {} has already liked in Movie {}", currentUser.getName(), movie.getName());
			throw new BadRequestException("Sorry! You have already liked on this movie");
		}

		Long likes = likeRepository.countByMovieId(movieId);
		return ModelMapper.mapMovieToMovieResponse(movie, likes);
	}
}
