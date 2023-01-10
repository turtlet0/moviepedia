package com.moviepedia.security.domain;
// 33.2.1 MemberVO를 UsersDetails 타입으로 변환하기
// UserDetailsService의 loadUserByUsername() 추상메서드의 리턴타입은 UserDetails라는 타입
// -> MemverVO의 인스턴스를 UserDetails 타입으로 변환해야함
// UserDetails는 User클래스 상속해 구현
// 


import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.moviepedia.domain.MemberVO;

import lombok.Getter;

// tip) 물론 MembverVO 클래스 직접 수행해 userDetails 인터페이스 구현하는 방법도 좋지만,
// 기존 클래스 수정하지않고 확장. 즉 CustomUser라는 클래스를 생성해 MembverVO와 그 내부 AuthVO를 품도록 하는것이 더 낫다고 생각됨

@Getter
public class CustomUser extends User {

	private static final long serialVersionUID =1L; // 상수
	
	private MemberVO memberVO;
	
//	// 부모 클래스 User의 생성자 호출 필수
	// memberVO를 가져와 부모클래스의 각 변수에 값 할당함
	public CustomUser(MemberVO memberVO) {
		
		// 부모 클래스 user의 생성자의 파라미터로 입력하는 것 
		//(String username, String password, Collection<? extends GrantedAuthority> authorities)
			// tip) 스프링 시큐리티 String username는 일반적인 userid와 대응되는 명칭
		
//		super(memberVO.getUserid(), memberVO.getUserpw(),
//				memberVO.getAuthList().stream().map(auth -> new SimpleGrantedAuthority(auth.getAuth())).
//				collect(Collectors.toList()));
				// AuthVO 인스턴스는 GrantedAuthority 객체로 변환해야하므로 stream()과 map() 이용해 처리
		
		
//		public User(String username, String password, boolean enabled,
//				boolean accountNonExpired, boolean credentialsNonExpired,
//				boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities)		
		super(memberVO.getUserid(), memberVO.getUserpw(), memberVO.isEnabled(),
				 true, true, memberVO.isAccountNonLocked(), memberVO.getAuthList().stream().map(auth -> new SimpleGrantedAuthority(auth.getAuth())).
				collect(Collectors.toList()));
		// -> 추가로 enabled 등의 변수 사용하고싶으면 User의 생성자와 같이 모든 파라미터에 값을 할당해야함
		
		this.memberVO = memberVO; // 자식 클래스인 CustomUser의 필드 memberVO에 값 입력하는 것
	}

}
