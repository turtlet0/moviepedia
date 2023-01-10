package com.moviepedia.domain;

import lombok.Data;

@Data
public class RecaptchaDTO {
	
	private String success;
	private String challenge_ts;
	private String hostname;
	private String score;
	private String action;
}
