package com.project.flik.IMDBScrap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.flik.model.Movie;
import com.project.flik.repository.MovieRepository;

public class IMDB {
	@Autowired
	private MovieRepository movieRepository;

	public void imdbScrap() throws IOException {
		String url = "https://www.imdb.com/search/title/?release_date=2019-08-15,2019-08-15&start=";
		Document doc = Jsoup.connect(url + "1").get();
		Elements pageNumbersText = doc.select("div.desc");
		System.out.println("Text = " + pageNumbersText.text());
		String pageArray[] = pageNumbersText.text().split(" ");

		int totalMovieRecords = Integer.parseInt(pageArray[2].replace(",", ""));

		String pageMovie[] = pageArray[0].split("-");
		int pageStart = Integer.parseInt(pageMovie[0].replace(",", ""));
		List<Movie> movieList = new ArrayList<>();

		for (int searchPage = pageStart; searchPage < totalMovieRecords; searchPage = searchPage + 50) {
			System.out.println(searchPage);
			movieList.addAll(scrapMovie(url + searchPage));
		}

		generateExel(movieList);
	}

	private List<Movie> scrapMovie(String url) throws IOException {

		Document doc = Jsoup.connect(url).get();
		Elements movies = doc.getElementsByClass("lister-item mode-advanced");
		List<Movie> movieList = new ArrayList<>();
		for (Element movie : movies) {
			Movie moviess = new Movie();
			// Elements movieCover = movie.getElementsByTag("img");
			Element imagesDiv = movie.getElementsByClass("lister-item-image float-left").first();
			Element movieCover = imagesDiv.select("img").first();
			String movieCoverUrl = movieCover.attr("loadlate");
			moviess.setUrl(movieCoverUrl);
			Elements movieTags = movie.getElementsByClass("lister-item-content");
			for (Element movieTag : movieTags) {
				Elements movieTitles = movieTag.getElementsByClass("lister-item-header");
				
				for (Element movieTitle : movieTitles) {
					Elements movieName = movieTitle.getElementsByTag("a");
					moviess.setName(movieName.text());
					// System.out.println(movieName.text());
					Elements movieYear = movieTag.getElementsByClass("lister-item-year text-muted unbold");
					//moviess.setYear(movieYear.text());
				}
				Elements movieTypes = movieTag.getElementsByClass("text-muted ");
				String abc = "";
				for (Element movieType : movieTypes) {
					try {
						Element certificate = movieType.select("span.certificate").first();
						// System.out.println("certificate = "+certificate);
					} catch (Exception e) {
						// TODO: handle exception
					}

					Elements runtime = movieType.getElementsByClass("runtime");
					moviess.setRunTime(runtime.text());

					Elements genre = movieType.getElementsByClass("genre");
					//moviess.setGenre(genre.text());
					Elements span = movieType.getElementsByTag("span");
					abc = span.text();
				}
				if (movieTitles.text().contains("Episode:")) {
					Element serialName = movieTag.getElementsByTag("a").get(0);
					String serialUrl = serialName.attr("href");
					//System.out.println("serialName = " + serialName.text());
					//System.out.println("HREF=" + serialUrl);
					try {
						movieRepository.findByName(serialName.text());
						
					} catch (NullPointerException e) {
						saveMovie(serialUrl, moviess);
					}
					
					Element episode = movieTag.getElementsByTag("a").get(1);
					//System.out.println("episode = " + episode.text());
				}else {
					Element serialName = movieTag.getElementsByTag("a").get(0);
					String serialUrl = serialName.attr("href");
					saveMovie(serialUrl, moviess);
				}
				Elements movieDescs = movieTag.select("p.text-muted");
				moviess.setDescription(movieDescs.text().replace(abc, ""));
			}
			movieList.add(moviess);

		}
		return movieList;
	}

	private void saveMovie(String serialUrl, Movie movie) throws IOException {
		Document doc = Jsoup.connect("https://www.imdb.com"+serialUrl).get();
		Element movieDoc = doc.getElementsByClass("heroic-overview").first();
		Element movieShortDesc = movieDoc.getElementsByClass("title_wrapper").first();
		Element movieName = movieShortDesc.getElementsByTag("h1").first();
		System.out.println("Movie Name="+movieName.text());
		
		//movie.setName(movieName.text());
		movie.setUrl(serialUrl);
		Element movieOverview = movieDoc.getElementsByClass("plot_summary").first();
		Element movieDesc = movieOverview.getElementsByClass("summary_text").first();
		
		movie.setDescription(movieDesc.text());
		
		Element movieCoverDiv = movieDoc.getElementsByClass("poster").first();
		Element poster = movieCoverDiv.select("img").first();
		String movieCoverUrl = poster.attr("src");
		//movie.setEpisode("");
		//movie.setSeason("");
		//movie.setRefranceMovie("");
//		movie.setId(1L);
		movie.setImageUrl(movieCoverUrl);
		movieRepository.save(movie);
	}

	private void generateExel(List<Movie> moviess) {
		try {
			String filename = "D:/IMDb.xls";
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("IMDb");

			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("Url");
			rowhead.createCell(1).setCellValue("Name");
			rowhead.createCell(2).setCellValue("Year");
			rowhead.createCell(3).setCellValue("runtime");
			rowhead.createCell(4).setCellValue("genre");
			rowhead.createCell(5).setCellValue("Desc");
			int i = 1;
			for (Movie movie : moviess) {
				HSSFRow row = sheet.createRow(i);
				row.createCell(0).setCellValue(movie.getUrl());
				row.createCell(1).setCellValue(movie.getName());
				//row.createCell(2).setCellValue(movie.getYear());
				row.createCell(3).setCellValue(movie.getRunTime());
			//	row.createCell(4).setCellValue(movie.getGenre());
				row.createCell(5).setCellValue(movie.getDescription());
				i++;
			}

			FileOutputStream fileOut = new FileOutputStream(filename);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			System.out.println("Your excel file has been generated!");

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}
