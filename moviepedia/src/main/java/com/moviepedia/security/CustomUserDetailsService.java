package com.moviepedia.security;
// 스프링 시큐리티의 UserDetailsService를 구현하는 클래스
// MemberMapper 타입의 인스턴스 주입받아 실제 기능 구현

//33.2 CustomUserDetailsService 구성
//: MyBatis 이용해 MeberVO와 같이 회원 처리하는 부분 구성되었다면 이를 이용해
//스프링 시큐리티의 UserDetailsService를 구현하는 커스텀 클래스를 작성 가능
//-> 이 클래스에 MemberMapper 타입의 인스턴스 주입받아서 실제 기능(비즈니스 로직) 구현 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.moviepedia.domain.MemberVO;
import com.moviepedia.mapper.MemberMapper;
import com.moviepedia.security.domain.CustomUser;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailsService  implements UserDetailsService {
	// loadUserByUsername() 메서드는 내부적으로 memberMapper를 이용해 MemberVO를 조회하고,
	// 만일 MemberVO의 인스턴스를 얻을 수 있다면 CustomUser 타입의 객체로 변환해 반환
	
	
	@Setter(onMethod_ = @Autowired)
	private MemberMapper memberMapper;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		
		log.warn("Load User By UserName: " + userName);
		
		// userName means userid
		MemberVO memberVO = memberMapper.read(userName);
		
		log.warn("queried by member mapper: " + memberVO);

//		 
		/*
		 *  tip) DB에 사용자계정없는경우 UserDetailsService에서 InternalAuthenticationServiceException 에러를 내고
		 *  AuthenticationProvider을 타지 않고 바로 failureHandler로 넘어감
		 *  이를 방지하기 위해선 UserDetailsService에서 null일 경우 UsernameNotFoundException 예외로 throw해줘야 
		 *  에러 발생하지 않고 안정적으로 failureHandler로 넘어감 
		 *  tip) InternalAuthenticationServiceException 예외로 throw하는 경우에도 동일하게 InternalAuthenticationServiceException 발생함
		 *  -> 원인은 아직 모름.
		 */
		if(memberVO == null) {
			log.warn("memberVO null -> throw UsernameNotFoundException");
			throw new UsernameNotFoundException(userName);
//			throw new InternalAuthenticationServiceException(userName);
		}
		
		return memberVO == null ? null : new CustomUser(memberVO);
		
	}
	
	
}
