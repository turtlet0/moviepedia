package com.moviepedia.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor // 기본 생성자 및 모든 필드값을 파라미터로 받는 생성자 자동 생성
@Getter // get 메서드 자동생성
public class ReplyListDTO {
	
	private List<ReplyInfoDTO> replyList;
	
	private int replyCnt;
}
