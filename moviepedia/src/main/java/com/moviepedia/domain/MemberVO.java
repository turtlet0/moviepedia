package com.moviepedia.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MemberVO {

	private String userid;
	private String userpw;
	private String userName;
	private String userIntroduction;
	
	private boolean enabled;
		// : 계정이 어떠한 이유로 인해 관리 상 또는 자동으로 비활성화됨을 나타냄
		// : 해제를 하기위해선 몇가지 조치가 필요
	private boolean accountNonLocked; 
	// 잘못된 로그인 시도로 인해 계정이 자동으로일시 중지
	// -> 시간 지남 또는 수동 잠금 해제
	// but, 일반적으론 enabled 하나만으로 사용하는 경우 많음
	// 여기선 휴면계정과 비밀번호 오류 반복으로 인한 계정 잠금을 구분할 것
	
//	accountNonExpired
//	credentialsNonExpired
	
	private String randomString;
	
	private int failureCnt;
	
	private Date regDate;
	private Date updateDate;
	
	private String favoriteGenreList;
	private String favoriteDirectorList;
	private String favoriteActorList;
	
	private List<AuthVO> authList;

}
