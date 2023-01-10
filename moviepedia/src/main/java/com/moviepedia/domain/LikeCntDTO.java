package com.moviepedia.domain;

import lombok.Data;

// 좋아요 등록/삭제 후 코멘트의 좋아요 개수 갱신 시 return 객체로 사용
@Data
public class LikeCntDTO {

	private int likeCnt;
	
	private LikeVO likeVO;
}
