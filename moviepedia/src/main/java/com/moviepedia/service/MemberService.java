package com.moviepedia.service;

import com.moviepedia.domain.MemberVO;

import io.jsonwebtoken.Claims;

public interface MemberService {

	
public String[] testRandString(String userid, String randomString);
	
	public int testRandString();
	
	
	
	// 회원가입 메서드
	public int register(MemberVO vo);
	
	// 회원 정보 반환 메서드 by userid
	public MemberVO get(String userid);
	
	// 사용x? - UserService 통해 회원 페이지 정보 조회) 회원 정보 반환 메서드 by randomString
	public MemberVO getByRandomString(String randomString);
	
	
	
	
	
/* 공통 */
	// 텍스트 파일 읽기 메서드
		public String readTextFile(String fileName);
		
	
/* 회원가입 */
	// 회원가입 이메일 중복 검사
	public int checkEmailDuplicate(String email);
	
	// 회원가입 이메일 본인인증 메일 전송
	public String sendVerificationEmail(String email);
	
/* 비밀번호 찾기 */

	// 비밀번호 재설정 이메일 전송 메서드
	public String sendPasswordResetEmail(String email);
		
		// JWT 토큰 생성 메서드
		public String createJwtToken(String userid);
		
	// ** 토큰 만료 여부 확인 및 userid 획득 메인 메서드
	public String getUseridFromJwtToken(String jwtToken);
	
		// JWT 토큰 claims 반환 메서드
		public Claims getAllClaims(String jwtToken);
	
		// 토큰 만료 여부 확인 메서드
		public boolean isTokenExpired(String jwtToken);
		
	// 비밀번호 초기화/변경 메서드
	public int resetPassword(String userid, String userpw);
	
	// 계정 잠금 여부 체크 메서드
	public boolean isAccountNonLocked(String userid);
	
	// 계정 잠금 해제 메서드
	public int unlockAccountnonlocked(String userid);
	
	// 변경 비밀번호/기존 비밀번호 일치 여부 체크 메서드
	public boolean checkUserpwSameness(String userid, String userpw);
	
	
	
	
/* 회원 탈퇴 */
	
	// 계정 삭제 메인 메서드
	public boolean withdraw(String userid);
		
	// 계정 및 계정 권한 삭제 메서드
	public boolean removeUserNAuth(String userid);
	
	
/* 프로필 수정 */
	public MemberVO modifyUserNameNIntroduction(MemberVO memberVO);
	
}
