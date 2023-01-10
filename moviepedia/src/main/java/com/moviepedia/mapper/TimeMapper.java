package com.moviepedia.mapper;

//4.2.1 Mapper 인터페이스 - 테스트용
import org.apache.ibatis.annotations.Select;

//Mapper 를 인터페이스와 MyBatis의 어노테이션으로 작성하는 것
//=> 작성 후엔 MyBatis 동작 시 mapper 인식할 수 있도록 root-context.xml에 설정 추가 (mybaits:scan 태그 이용)

//MyBatis-Spring은 Mapper 인터페이스를 이용해 실제 SQL 처리가되는 클래스를 '자동'으로 생성
//-> 개발자는 인터페이스와 SQL만 작성하는 방식으로 모든 JDBC 처리를 할 수 있음
public interface TimeMapper {
	
	// 인터페이스+어노테이션 이용하는 방식
	@Select("SELECT sysdate FROM dual")
	public String getTime();
	
	// 4.2.3 XML 매퍼와 같이 쓰는 방식
	public String getTime2();
		// @Select와 같은 MyBatis의 어노테이션 존재X, SQL도 X
		// -> 실제 SQL은 XML을 이용해 처리 (TimeMapper.xml)
		/*
		 * <select id="getTime2" resultType="string"> SELECT sysdate FROM dual </select>
		 */
	
}

