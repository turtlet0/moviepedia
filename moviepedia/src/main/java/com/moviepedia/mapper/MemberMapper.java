package com.moviepedia.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.moviepedia.domain.MemberVO;

public interface MemberMapper {
	
	/* 회원정보 호출 */
	//	com.moviepedia.security.domain.CustomUser.java 에서 호출
	public MemberVO read(String userid);
	
	public MemberVO readByRandomString(String randomString);
	
/* 회원가입 */
	public int insert(MemberVO vo);
	
	public int checkUseridDuplicate(String userid);
	
/* 로그인 */
	public int updateFailureCnt(String userid);
	
	public int resetFailureCnt(String userid);
	
	public MemberVO getFailureCntAccountNonLocked(String userid);
	
	public int changeAccountNonLocked(String userid);
	
	
/* 비밀번호 찾기 */
	public int updatePassword(MemberVO memberVO);
	
	
	public int testUpdate(@Param("userid") String userid, @Param("randomString") String randomString);
	
	public List<MemberVO> readList();
	
/* 회원 탈퇴 */
	public int remove(String userid);
	
	public int removeAuthList(String userid);
	
/* 프로필 수정 */
//	public int updateUserNameNIntroduction(@Param("userid") String userid, @Param("userName") String userName, @Param("userIntroduction") String userIntroduction);
	public int updateUserNameNIntroduction(MemberVO memberVO);
	
	
	
}
