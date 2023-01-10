package com.moviepedia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moviepedia.domain.RecaptchaDTO;
import com.moviepedia.service.RecaptchaService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/robot")
@Log4j
@AllArgsConstructor
public class RecaptchaController {

	private RecaptchaService recaptchaService;
	
	@GetMapping("/test")
	public String test() {
		
		return "recaptcha2";
	}
	
	@PostMapping("/token")
	public @ResponseBody RecaptchaDTO token(@RequestParam("token") String token) {
		log.info(token);
		
		return recaptchaService.token(token);
	}
}
