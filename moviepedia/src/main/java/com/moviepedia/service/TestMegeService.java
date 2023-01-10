package com.moviepedia.service;

import java.util.List;

import com.moviepedia.domain.TestMergeVO;

public interface TestMegeService {
	public int merge(TestMergeVO mergeVO);
	
	public int mergeList(List<TestMergeVO> mergeList);
}
