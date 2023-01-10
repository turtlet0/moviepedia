package com.moviepedia.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class FavoriteGenreAnalysisDTO {
	
	private List<FavoriteGenreNationDTO> favoriteGenreDTOList;
	
	private String resultMessage;
	
}
