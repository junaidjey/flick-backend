package com.project.flik.payload;

import java.time.Instant;

public class UserProfile {
    private Long id;
    private String username;
    private String name;
    private Instant joinedAt;
   // private Long pollCount;
    private Long likeCount;
    private Long following;
    private Long followers;

//    public UserProfile(Long id, String username, String name, Instant joinedAt, Long pollCount, Long likeCount) {
//        this.id = id;
//        this.username = username;
//        this.name = name;
//        this.joinedAt = joinedAt;
//        this.pollCount = pollCount;
//        this.likeCount = likeCount;
//    }
    public UserProfile(Long id, String username, String name, Instant joinedAt, Long likeCount, Long following, Long followers) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.joinedAt = joinedAt;
        this.likeCount = likeCount;
        this.followers = followers;
        this.following = following;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }

//    public Long getPollCount() {
//        return pollCount;
//    }
//
//    public void setPollCount(Long pollCount) {
//        this.pollCount = pollCount;
//    }

	public Long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

	public Long getFollowing() {
		return following;
	}

	public void setFollowing(Long following) {
		this.following = following;
	}

	public Long getFollowers() {
		return followers;
	}

	public void setFollowers(Long followers) {
		this.followers = followers;
	}

}
