package com.moviepedia.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moviepedia.domain.CommentVO;
import com.moviepedia.domain.CommentInfoDTO;
import com.moviepedia.mapper.CommentMapper;
import com.moviepedia.mapper.LikeMapper;
import com.moviepedia.mapper.MemberMapper;
import com.moviepedia.mapper.MovieMapper;
import com.moviepedia.mapper.ReplyMapper;
import com.moviepedia.mapper.StarRatingMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class CommentServiceImpl implements CommentService {
	
	
	@Setter(onMethod_ = @Autowired)
	private CommentMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private MemberMapper memberMapper;
	
	@Setter(onMethod_ = @Autowired)
	private MovieMapper movieMapper;
	
	@Setter(onMethod_ = @Autowired)
	private LikeMapper likeMapper;
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper replyMapper;
	
	@Setter(onMethod_ = @Autowired)
	private StarRatingMapper starRatingMapper;
	
	
	
	@Transactional
	@Override
	public int register(CommentVO commentVO) {
		
		log.info("register...: " + commentVO);
		
		// 코멘트 개수 업데이트
		movieMapper.updateCommentCnt(commentVO.getMovieCd(), 1);
		
		return mapper.insert(commentVO);
	}
	
	@Transactional
	@Override
	public CommentInfoDTO get(Long commentCd) {
		
		log.info("get.... : " + commentCd);
		
		CommentInfoDTO commentInfoDTO = mapper.read(commentCd);
		
//		if(commentInfoDTO != null) {
//			log.info("null X");
//			
//			Map<String, Double> starRatingAvgCnt = starRatingMapper.getAvgCntByUserid(commentInfoDTO.getCommentVO().getUserid());
//			
//			if(Integer.parseInt(String.valueOf(starRatingAvgCnt.get("cnt"))) != 0) {
//				
//				log.info(starRatingAvgCnt.get("cnt"));
//				log.info(starRatingAvgCnt.get("avg"));
//				
//				// 유저의 별점 평가 수 및 평균평점
//				commentInfoDTO.setCntStarRating(Integer.parseInt(String.valueOf(starRatingAvgCnt.get("cnt"))));
//				commentInfoDTO.setAvgStarRating(Double.parseDouble(String.valueOf(starRatingAvgCnt.get("avg"))));
//			}
//		}
		
	
		log.info(commentInfoDTO);
		
		return commentInfoDTO;
		
	
	}

	@Transactional
	@Override
	public CommentInfoDTO getUser(String userid, String movieCd) {
		
		log.info("getUser.... : " + userid + "/" + movieCd);
		
		CommentInfoDTO commentInfoDTO = mapper.readUser(userid, movieCd);
		if(commentInfoDTO != null) {
			log.info("null X");
			
			Map<String, Double> starRatingAvgCnt = starRatingMapper.getAvgCntByUserid(userid);
			
			if(Integer.parseInt(String.valueOf(starRatingAvgCnt.get("cnt"))) != 0) {
				
				log.info(starRatingAvgCnt.get("cnt"));
				log.info(starRatingAvgCnt.get("avg"));
				
				// 유저의 별점 평가 수 및 평균평점
				commentInfoDTO.setCntUserStarRating(Integer.parseInt(String.valueOf(starRatingAvgCnt.get("cnt"))));
				commentInfoDTO.setAvgUserStarRating(Double.parseDouble(String.valueOf(starRatingAvgCnt.get("avg"))));
			}
			
		}
		log.info(commentInfoDTO);
		
		return commentInfoDTO;
	}
	
	
//	@Override
//	public int modify(CommentVO commentVO) {
//		
//		log.info("modify.... : " + commentVO);
//		
//		return mapper.update(commentVO);
//	}
	
	@Override
	public String modify(CommentVO commentVO) {
		
		log.info("modify.... : " + commentVO);
		
		mapper.update(commentVO);
		String modifiedContents = commentVO.getContents();
		log.info("modifiedContents: " + modifiedContents);
		log.info(commentVO.getCommentDate());
		
		return modifiedContents;
	}

	@Transactional
	@Override
	public int remove(Long commentCd) {
		
		log.info("remove.... " + commentCd);
		
		// 코멘트 개수 업데이트
		CommentInfoDTO commentInfoDTO = mapper.read(commentCd);	
		movieMapper.updateCommentCnt(commentInfoDTO.getCommentVO().getMovieCd(), -1);
		
		// 해당 코멘트의 좋아요 내역 삭제
		if(commentInfoDTO.getCommentVO().getLikeCnt() != 0) {
			likeMapper.deleteByCommentCd(commentCd);
		}
		
		// 해당 코멘트의 댓글 내역 삭제
		if(commentInfoDTO.getCommentVO().getReplyCnt() != 0) {
			replyMapper.deleteByCommentCd(commentCd);
		}
		return mapper.delete(commentCd);
	}


	
	@Override
	public List<CommentVO> getList(String movieCd, int orderBy, int totalCnt) {
		
		log.info("get Comment List of a Movie : " + movieCd + "/" + orderBy + "/" + totalCnt);
		
		return mapper.getList(movieCd, orderBy, totalCnt);
	}

	@Override
	public List<CommentInfoDTO> getAdditionalList(String movieCd, int orderBy, int currentCnt, int additionalCnt, String loginUserid) {
		
		log.info("get Additional Comment List of a Movie : " + movieCd+  "/" + orderBy + "/" + currentCnt + "/" + additionalCnt +"/" + loginUserid);
		
		
		List<CommentInfoDTO> CommentUserDTOList = mapper.getAdditionalList(movieCd, orderBy, currentCnt, additionalCnt, loginUserid);

		log.info("CommentUserDTOList: " + CommentUserDTOList);
		
		CommentUserDTOList.forEach(CommentUserDTO -> log.info("CommentUserDTO: " + CommentUserDTO));
		
//		for (CommentInfoDTO commentInfoDTO : CommentUserDTOList) {
//			
//			String userid = commentInfoDTO.getCommentVO().getUserid();
//			
//			Map<String, Double> starRatingAvgCnt = starRatingMapper.getAvgCntByUserid(userid);
//			if(Integer.parseInt(String.valueOf(starRatingAvgCnt.get("cnt"))) != 0) {
//				
//				log.info(starRatingAvgCnt.get("cnt"));
//				log.info(starRatingAvgCnt.get("avg"));
//				
//				// 유저의 별점 평가 수 및 평균평점
//				commentInfoDTO.setCntUserStarRating(Integer.parseInt(String.valueOf(starRatingAvgCnt.get("cnt"))));
//				commentInfoDTO.setAvgUserStarRating(Double.parseDouble(String.valueOf(starRatingAvgCnt.get("avg"))));
//				
//			}
//			
//		}
		
		return CommentUserDTOList;
	}

	
	// 사용x. 각 서비스에서 수행) 코멘트 좋아요 개수 갱신
	@Override
	public int getLikeCnt(Long commentCd) {
		
		log.info("getLikeCnt......" + commentCd);
		
		return mapper.getLikeCnt(commentCd);
	}
	
	// 사용x. 각 서비스에서 수행) 코멘트 댓글 개수 갱신
	@Override
	public int getReplyCnt(Long commentCd) {
		
		log.info("getReplyCnt......" + commentCd);
		
		return mapper.getReplyCnt(commentCd);
	}

	// 회원탈퇴 - 유저의 모든 코멘트(이하 댓글, 좋아요) 리스트 삭제
	@Transactional
	@Override
	public boolean removeUserCommentList(String userid) {
		
		// 코멘트 삭제
		Long[] userCommentCdArr = null;
		userCommentCdArr = mapper.getUserCommentCdList(userid);
		if(userCommentCdArr != null) {
			int userCommentCdArr_length = userCommentCdArr.length;
			for(int i=0; i<userCommentCdArr_length; i++) {
				remove(userCommentCdArr[i]);
				
//						commentMapper.delete(userCommentCdArr[i]);
			}
			
		}
				
		return true;
	}
	

}
