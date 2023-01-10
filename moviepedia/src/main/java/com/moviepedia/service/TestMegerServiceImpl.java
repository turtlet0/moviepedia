package com.moviepedia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviepedia.domain.TestMergeVO;
import com.moviepedia.mapper.TestMergeMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class TestMegerServiceImpl implements TestMegeService {
	

	@Setter(onMethod_ = @Autowired)
	private TestMergeMapper mapper;
	
	@Override
	public int merge(TestMergeVO mergeVO) {
		// TODO Auto-generated method stub
		return mapper.testMerge(mergeVO);
	}

	@Override
	public int mergeList(List<TestMergeVO> mergeList) {
		// TODO Auto-generated method stub
		return mapper.TestMergeList(mergeList);
	}

}
