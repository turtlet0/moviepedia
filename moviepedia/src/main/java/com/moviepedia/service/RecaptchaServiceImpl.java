package com.moviepedia.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.moviepedia.domain.RecaptchaDTO;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
//계층 구조 상 주로 비즈니스 영역을 담당하는 객체임을 표시
// 이 어노테이션은 패키지 읽어들이는 동안 처리됨
// ServiceImpl가 정상적으로 동작하기 위해선 Mapper 객체 필요함
@AllArgsConstructor
@Log4j
public class RecaptchaServiceImpl implements RecaptchaService {

	@Override
	public RecaptchaDTO token(String token) {
		String url = "https://www.google.com/recaptcha/api/siteverify";
			/* 
			 * {
				  "success": false,
				  "error-codes": [
				    "missing-input-secret"
				  ]
				}
			 */
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("secret", "6Le52NoiAAAAAFiwpdhpeohpLf_qzUZ7KpFt4vMJ");
		map.add("response", token);
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String,String>>(map, headers);
		
		RecaptchaDTO response = restTemplate.postForObject(url, request, RecaptchaDTO.class);
		
		return response;
		
	}

}
