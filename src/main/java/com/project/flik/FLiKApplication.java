package com.project.flik;

import java.io.IOException;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.project.flik.IMDBScrap.IMDB;
import com.project.flik.controller.MovieController;

@SpringBootApplication
@EntityScan(basePackageClasses = {
		FLiKApplication.class,
		Jsr310JpaConverters.class
})
public class FLiKApplication {
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	public static void main(String[] args) throws IOException {
		SpringApplication.run(FLiKApplication.class, args);
	}

}