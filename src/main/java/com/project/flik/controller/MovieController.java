package com.project.flik.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.flik.model.LastScrapped;
import com.project.flik.payload.MovieResponse;
import com.project.flik.payload.PagedResponse;
import com.project.flik.payload.ScrapMovieVariable;
import com.project.flik.repository.LastScrappedRepository;
import com.project.flik.security.CurrentUser;
import com.project.flik.security.UserPrincipal;
import com.project.flik.service.MovieService;
import com.project.flik.util.AppConstants;

@Controller
@RequestMapping("/api")
public class MovieController {
	@Autowired
	private MovieService movieService;

	@Autowired
	private LastScrappedRepository lastScrappedRepository;

	@GetMapping("/")
	public void imdbScrap() throws IOException, ParseException {
		String searchDate = "2019-08-15";
		LastScrapped lastScrapped = lastScrappedRepository.findFirstByOrderByIdDesc();
		String listNumber = "1";
		if (lastScrapped != null) {
			searchDate = lastScrapped.getSearchDate();
			listNumber = lastScrapped.getListNumber();
		}
		String url = "https://www.imdb.com/search/title/?release_date=" + searchDate + "&start=";

		ScrapMovieVariable scrapMovieVariable = fatchUrl(url + listNumber);
		if (scrapMovieVariable.getTotalMovieRecords() > Integer.parseInt(listNumber)) {
			for (int searchPage = scrapMovieVariable.getPageStart(); searchPage < scrapMovieVariable
					.getTotalMovieRecords(); searchPage = searchPage + 50) {
				System.out.println(searchPage);
				movieService.scrapMovie(url + searchPage, searchDate);
			}
		}
		
		
		LastScrapped lastScrapped2 = lastScrappedRepository.findFirstByOrderByIdDesc();
		if (lastScrapped2 != null) {
			searchDate = lastScrapped2.getSearchDate();
			listNumber = lastScrapped2.getListNumber();
		}
		if (scrapMovieVariable.getTotalMovieRecords() <= Integer.parseInt(listNumber)) {
			System.out.println("abcc");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(searchDate));

			calendar.add(Calendar.DATE, 1); // number of days to add
			String destDate = sdf.format(calendar.getTime());
			System.out.println(destDate);

			url = "https://www.imdb.com/search/title/?release_date=" + destDate + "&start=";
			ScrapMovieVariable scrapMovieVariable2 = fatchUrl(url + "1");
			for (int searchPage = scrapMovieVariable2.getPageStart(); searchPage < scrapMovieVariable2
					.getTotalMovieRecords(); searchPage = searchPage + 50) {
				System.out.println(searchPage);
				movieService.scrapMovie(url + searchPage, destDate);
			}
			//fatchUrl(url);
			// scrapMovieFromNewDate();
		}
	}

	private ScrapMovieVariable fatchUrl(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements pageNumbersText = doc.select("div.desc");
		System.out.println("Text = " + pageNumbersText.text());
		String pageArray[] = pageNumbersText.text().split(" ");

		int totalMovieRecords = Integer.parseInt(pageArray[2].replace(",", ""));

		String pageMovie[] = pageArray[0].split("-");
		int pageStart = Integer.parseInt(pageMovie[0].replace(",", ""));

		ScrapMovieVariable scrapMovieVariable = new ScrapMovieVariable();
		scrapMovieVariable.setTotalMovieRecords(totalMovieRecords);
		scrapMovieVariable.setPageStart(pageStart);
		return scrapMovieVariable;
	}

	@GetMapping("/movies")
	public @ResponseBody PagedResponse<MovieResponse> getMovies(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
		return movieService.getAllMovies(page-1, size);
	}

	@PostMapping("/{movieId}/likes")
	@PreAuthorize("hasRole('USER')")
	public MovieResponse castLike(@CurrentUser UserPrincipal currentUser, @PathVariable Long movieId) {
		return movieService.castLikesAndGetUpdatedMovie(movieId, currentUser);
	}
}