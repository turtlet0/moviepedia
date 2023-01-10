package com.moviepedia.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.moviepedia.domain.ApiInfoDTO;
import com.moviepedia.domain.MovieVO;
import com.moviepedia.service.ManageService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController // REST 방식을 동작
@Log4j
@AllArgsConstructor
public class ManageController {

	private ManageService service;
	
	
	/* ------------- Call API --------------- */
	
	// 영화&인물 정보 API 호출
	@GetMapping(value = "/rest/manage/callApi/moviePeople/{curPageStart}/{curPageFinish}/{itemPerPage}/{openStartDt}/{openEndDt}",
			produces = {
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE
	})
	public ResponseEntity<List<ApiInfoDTO>> callApiMoviePeople(
			@PathVariable("curPageStart") int curPageStart,
			@PathVariable("curPageFinish") int curPageFinish,
			@PathVariable("itemPerPage") int itemPerPage,
			@PathVariable("openStartDt") int openStartDt,
			@PathVariable("openEndDt") int openEndDt){
		
	
//		String key = "c3697662d8e20b3ce90f649f1c91ad78";
//		String key = "bafd21e96032a078061356970bbfd7b7";
		
//		String key = "341f8f3bda063d9881afa76dc16180ca";
//		String key = "2a33334b3e7bce3518472e30c1b1cb49";	
	
		
//		String key = "6e15b9cbc0b343c662807fec2e56d4ef";
		String key = "0c3d249406e4ae7e4b9cb5059633cb54";

		
		log.info("callApiMoviePeople......");
		
		return new ResponseEntity<List<ApiInfoDTO>>(service.registerMainMoviePeopleList(key, curPageStart, curPageFinish, itemPerPage, openStartDt, openEndDt), HttpStatus.OK);
	}
	
	
	/* ------------- Call API --------------- */
	

}
