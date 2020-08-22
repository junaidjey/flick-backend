package com.project.flik.payload;

import java.util.ArrayList;
import java.util.List;

import com.project.flik.dto.GenreDto;

public class MovieResponse {
	private Long id;
	private String imageUrl;
	private String url;
	private String name;
	private String runTime;
	// private ArrayList<String> genre;
	private List<String> genre = new ArrayList<>();

	private List<GenreDto> genres = new ArrayList<>();
	private String description;
	private String season;
	private String episode;
	private Long refranceMovie;
	private String releaseDate;
	private Long likes;
	private String baseUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}
//	public ArrayList<String> getGenre() {
//		return genre;
//	}
//	public void setGenre(ArrayList<String> genre) {
//		this.genre = genre;
//	}

	public String getDescription() {
		return description;
	}

	public List<String> getGenre() {
		return genre;
	}

	public void setGenre(List<String> genre) {
		this.genre = genre;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getEpisode() {
		return episode;
	}

	public void setEpisode(String episode) {
		this.episode = episode;
	}

	public Long getRefranceMovie() {
		return refranceMovie;
	}

	public void setRefranceMovie(Long refranceMovie) {
		this.refranceMovie = refranceMovie;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}

	public List<GenreDto> getGenres() {
		return genres;
	}

	public void setGenres(List<GenreDto> genres) {
		this.genres = genres;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
}