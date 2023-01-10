package com.moviepedia.domain;
import java.util.Date;
import java.util.List;

// 유저의 정보(이름 및 사진) 및 코멘트 정보 저장 DTO
import lombok.Data;

@Data
public class CommentInfoDTO {

	private String userName;
	private String randomString;
	
	private String movieNm;
	private String posterUrl;
	private String prdtYear;
	private Date openDt;
	private String directorDetail;
	private String repNationNm;
	private String repGenreNm;
	
	private double userStarRating;
	
	private Long likeCd;
	
	private int cntUserStarRating;
	private double avgUserStarRating;
	
	private CommentVO commentVO;
}
