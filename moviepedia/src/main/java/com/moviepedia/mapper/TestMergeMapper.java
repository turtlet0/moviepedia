package com.moviepedia.mapper;

import java.util.List;

import com.moviepedia.domain.TestMergeVO;

public interface TestMergeMapper {

	public int testMerge(TestMergeVO mergeVO);
	
	public int TestMergeList(List<TestMergeVO> mergeList);
	
	public TestMergeVO get();
}
