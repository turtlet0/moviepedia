package com.moviepedia.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.moviepedia.domain.BoxOfficeVO;
import com.moviepedia.domain.BoxOfficeWithStarDTO;

public interface BoxOfficeMapper {
	public List<BoxOfficeWithStarDTO> getBoxOfficeWithStarList(@Param("showDate") String showDate, @Param("userid") String userid);
	
	public int insertBoxOfficeList(List<BoxOfficeVO> boxOfficeList);
}
