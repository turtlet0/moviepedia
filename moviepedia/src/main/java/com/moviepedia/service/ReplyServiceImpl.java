package com.moviepedia.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moviepedia.domain.ReplyInfoDTO;
import com.moviepedia.domain.ReplyListDTO;
import com.moviepedia.domain.ReplyVO;
import com.moviepedia.mapper.CommentMapper;
import com.moviepedia.mapper.ReplyMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReplyServiceImpl implements ReplyService {
	
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private CommentMapper commentMapper;
								
	@Transactional // 메서드나 클래스 내 모든 작업들이 모두 수행되지 않으면 모두 원상태로 되돌림
	@Override
	public Long register(ReplyVO vo) {
		
		log.info("register...: " + vo); // > register...: ReplyVO(replyCd=null, commentCd=489, movieCd=20000006, userid=mem8, reply=ㅇㄴ, replyDate=null, updateDate=null)
		
		// 댓글 개수 업데이트
		commentMapper.updateReplyCnt(vo.getCommentCd(), 1);
		
		mapper.insert(vo);
		
		log.info(vo); // > - ReplyVO(replyCd=291, commentCd=489, movieCd=20000006, userid=mem8, reply=ㅇㄴ, replyDate=null, updateDate=null)
		log.info(vo.getReplyCd()); // > 291
		
		return vo.getReplyCd();
	}

	@Override
	public ReplyListDTO get(Long replyCd) {
		
		log.info("get.... : " + replyCd);
		
		List<ReplyInfoDTO> replyList = new ArrayList<ReplyInfoDTO>();
		ReplyInfoDTO replyInfoDTO = mapper.read(replyCd);
		replyList.add(replyInfoDTO);
		
		return new ReplyListDTO( 
			replyList, // List<ReplyInfoDTO> replyList
			commentMapper.getReplyCnt(replyInfoDTO.getCommentCd())); // int replyCnt
	}
		
	
//	@Override
//	public ReplyVO getUser(String userid, String movieCd) {
//		
//		log.info("getUser.... : " + userid + "/" + movieCd);
//		
//		return mapper.readUser(userid, movieCd);
//	}
	
	
	@Override
	public int modify(ReplyVO vo) {
		
		log.info("modify.... : " + vo);
		
		return mapper.update(vo);
	}

	@Transactional
	@Override
	public int remove(Long replyCd) {
		
		log.info("remove.... " + replyCd);
		
		// 댓글 개수 업데이트
		ReplyInfoDTO vo = mapper.read(replyCd);
		
		commentMapper.updateReplyCnt(vo.getCommentCd(), -1);
		
		
		return mapper.delete(replyCd);
	}
	
	@Override
	public ReplyListDTO getList(Long commentCd) {
		
		log.info("get Reply List of a Comment : " + commentCd);
		
		return new ReplyListDTO( 
				mapper.getList(commentCd), // List<ReplyVO> replyList
				commentMapper.getReplyCnt(commentCd)); // int replyCnt
				
	}
	
	@Override
	public ReplyListDTO getList(Long commentCd, int crudIdx, Long replyCd) {
		
		log.info("get Reply List of a Comment : " + commentCd);
		
		return new ReplyListDTO( 
				mapper.getList(commentCd, crudIdx, replyCd), // List<ReplyVO> replyList
				commentMapper.getReplyCnt(commentCd)); // int replyCnt
		
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

	// 회원탈퇴 - 유저의 모든 댓글 리스트 삭제
	@Override
	public boolean removeUserReplyList(String userid) {
		log.info("removeUserReplyList... " + userid);
		// 좋아요 삭제
		Long[] userReplyCdArr = null;
		userReplyCdArr = mapper.getUserReplyCdList(userid);
		if(userReplyCdArr != null) {
			int userReplyCdArr_length = userReplyCdArr.length;
			for(int i=0; i<userReplyCdArr_length; i++) {
				remove(userReplyCdArr[i]);
				

			}
			
		}
		return true;
	}


	

}
