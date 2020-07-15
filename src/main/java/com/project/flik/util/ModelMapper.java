package com.project.flik.util;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.project.flik.model.Movie;
import com.project.flik.model.Poll;
import com.project.flik.model.User;
import com.project.flik.payload.ChoiceResponse;
import com.project.flik.payload.MovieResponse;
import com.project.flik.payload.PollResponse;
import com.project.flik.payload.UserSummary;

public class ModelMapper {

    public static PollResponse mapPollToPollResponse(Poll poll, Map<Long, Long> choiceVotesMap, User creator, Long userVote) {
        PollResponse pollResponse = new PollResponse();
        pollResponse.setId(poll.getId());
        pollResponse.setQuestion(poll.getQuestion());
        pollResponse.setCreationDateTime(poll.getCreatedAt());
        pollResponse.setExpirationDateTime(poll.getExpirationDateTime());
        Instant now = Instant.now();
        pollResponse.setExpired(poll.getExpirationDateTime().isBefore(now));

        List<ChoiceResponse> choiceResponses = poll.getChoices().stream().map(choice -> {
            ChoiceResponse choiceResponse = new ChoiceResponse();
            choiceResponse.setId(choice.getId());
            choiceResponse.setText(choice.getText());

            if(choiceVotesMap.containsKey(choice.getId())) {
                choiceResponse.setVoteCount(choiceVotesMap.get(choice.getId()));
            } else {
                choiceResponse.setVoteCount(0);
            }
            return choiceResponse;
        }).collect(Collectors.toList());

        pollResponse.setChoices(choiceResponses);
        UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getEmail(), creator.getFullName());
        pollResponse.setCreatedBy(creatorSummary);

        if(userVote != null) {
            pollResponse.setSelectedChoice(userVote);
        }

        long totalVotes = pollResponse.getChoices().stream().mapToLong(ChoiceResponse::getVoteCount).sum();
        pollResponse.setTotalVotes(totalVotes);

        return pollResponse;
    }

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

}
