package com.moviepedia.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moviepedia.domain.CommentVO;
import com.moviepedia.domain.LikeCntDTO;
import com.moviepedia.domain.LikeVO;
import com.moviepedia.domain.ReplyVO;
import com.moviepedia.mapper.CommentMapper;
import com.moviepedia.mapper.LikeMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class LikeServiceImpl implements LikeService {
	
	
	@Setter(onMethod_ = @Autowired)
	private LikeMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private CommentMapper commentMapper;
	
								
	@Transactional // 메서드나 클래스 내 모든 작업들이 모두 수행되지 않으면 모두 원상태로 되돌림
	@Override
	public int register(LikeVO likeVO) {
		
		log.info("register...: " + likeVO);
		
		int queryResult = mapper.insert(likeVO);
		
		int likeCnt = 0;
		if(queryResult == 1) {
			
			log.info("insert success... " + queryResult);
			
			// 좋아요 개수 업데이트
			commentMapper.updateLikeCnt(likeVO.getCommentCd(), 1);
			
			// 좋아요 개수 반환
			likeCnt = commentMapper.getLikeCnt(likeVO.getCommentCd());
			log.info("likeCnt: " + likeCnt);
		}
		
		
//		commentMapper.getLikeCnt(likeVO.getCommentCd()); // 좋아요 개수 반환
		return likeCnt;
//		return commentMapper.updateLikeCnt(likeVO.getCommentCd(), 1);
	}

	@Override
	public LikeVO get(Long likeCd) {
		
		log.info("get.... : " + likeCd);
		
		return mapper.read(likeCd);
	}

	
	@Override
	public LikeVO getUser(String userid, Long commentCd) {
		
		log.info("getUser.... : " + userid + "/" + commentCd);
		
		return mapper.readUser(userid, commentCd);
	}
	
	/**
	 * 회원탈퇴 시 like 삭제 메서드
	 */
	@Transactional
	@Override
	public int remove(Long likeCd) {
		
		log.info("remove...." + likeCd);
		
		// 좋아요 개수 업데이트
		LikeVO likeVO = mapper.read(likeCd);
		
		commentMapper.updateLikeCnt(likeVO.getCommentCd(), -1);
		
		return mapper.delete(likeCd);
	}
	
	
	@Transactional
	@Override
	public int remove(LikeVO likeVO) {
		
		log.info("remove.... " + likeVO);
		
//		// 좋아요 개수 업데이트
		int queryResult = mapper.delete(likeVO.getLikeCd());
		int likeCnt = 0;
		if(queryResult == 1) {
			log.info("delete success... " + queryResult);
			
			commentMapper.updateLikeCnt(likeVO.getCommentCd(), -1);
			likeCnt = commentMapper.getLikeCnt(likeVO.getCommentCd());
			log.info("likeCnt: " + likeCnt);
		}
				
		return likeCnt;
	}
	
	@Override
	public int removeByCommentCd(Long commentCd) {
		
		log.info("removeByCommentCd.... " + commentCd);
		
		return mapper.deleteByCommentCd(commentCd);
	}

	@Override
	public int getCntByCommentCd(Long commentCd) {
		
		log.info("getCntByCommentCd.... : " + commentCd);
		
		return mapper.getCountByCommentCd(commentCd);
	}
	
	
	// 회원탈퇴 - 유저의 모든 좋아요 리스트 삭제
	@Override
	public boolean removeUserLikeList(String userid) {
		log.info("removeUserLikeList... " + userid);
		// 좋아요 삭제
		Long[] userLikeCdArr = null;
		userLikeCdArr = mapper.getUserLikeCdList(userid);
		if(userLikeCdArr != null) {
			int userLikeCdArr_length = userLikeCdArr.length;
			for(int i=0; i<userLikeCdArr_length; i++) {
				remove(userLikeCdArr[i]);
				

			}
			
		}
		return true;
	}

	

	




	

}
