package com.moviepedia.service;


import com.moviepedia.domain.RecaptchaDTO;

public interface RecaptchaService {

	public RecaptchaDTO token(String token);
}
