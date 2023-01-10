package com.moviepedia.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moviepedia.domain.MemberVO;
import com.moviepedia.domain.ReplyVO;
import com.moviepedia.mapper.CommentMapper;
import com.moviepedia.mapper.LikeMapper;
import com.moviepedia.mapper.MemberMapper;
import com.moviepedia.mapper.ReplyMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MemberServiceImpl implements MemberService {

	private final Long EXPIRED_TIME = 1000 * 60L * 60L; // 유효기간 10분
	private final String SECRET_KEY = "fdjghkdgjhvucbyi3mewnjhksd382yh";
	/*
	 *  tip) TTL(Time To Live) : 컴퓨터나 네트워크에서 데이터의 유효기간을 나타내기 윈한 방법
	 *  TTL은 타임스탬프 형태로 데이터에 포함되면 정해진 유효기잔 지나면 데이터는 폐기됨
	 * 	캐시 성능이나 프라이버시 수준 향상시키는데 용이
	 */
	
	
	@Setter(onMethod_ = @Autowired)
	private MemberMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private PasswordEncoder passwordEncoder;
	//	import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//	import org.springframework.security.crypto.password.PasswordEncoder;
	
	@Setter(onMethod_ = @Autowired)
	private JavaMailSenderImpl mailSender;
	
	// 다른 Service 주입 정상 실행됨.
	@Setter(onMethod_ = @Autowired)
	private CommentService commentService;
	
	@Setter(onMethod_ = @Autowired)
	private LikeService likeService;
	
	@Setter(onMethod_ = @Autowired)
	private ReplyService replyService;
	
	@Setter(onMethod_ = @Autowired)
	private StarRatingService starRatingService;
	

	// 회원가입 메서드
	@Override
	public int register(MemberVO memberVO) {
		
		// 비밀번호 암호화
		memberVO.setUserpw(passwordEncoder.encode(memberVO.getUserpw()));
		
		// 랜덤 문자열 저장
		String randomString = RandomStringUtils.random(13, true, true);	
		memberVO.setRandomString(randomString);
		
		log.info("========================");
		log.info("register memberVO: " + memberVO);	
		log.info("========================");
		
		return mapper.insert(memberVO);
	}

	// 회원 정보 반환 메서드 by userid
	@Override
	public MemberVO get(String userid) {
		
		log.info("get...............");
		
		return mapper.read(userid);
	}
	
	// 회원 정보 반환 메서드 by randomString
	@Override
	public MemberVO getByRandomString(String randomString) {
		
		log.info("getByRandomString...............");
		
		return mapper.readByRandomString(randomString);
	}

	@Override
	public String[] testRandString(String userid, String randomString) {
		
		String[] failUserid = new String[10];
//		try {
//			mapper.testUpdate(userid, randomString);
//			return failUserid;
//			
//		} catch (DataAccessException e) {
//            SQLException sqle = (SQLException) e.getCause();
//            System.out.println("Error code: " + sqle.getErrorCode());
//            System.out.println("SQL state: " + sqle.getSQLState());
//            
//            
//        }
		
		
		return failUserid;
	}

	@Override
	public int testRandString() {
		
		List<MemberVO> memberList = mapper.readList();
		for(int i=0; i<memberList.size(); i++) {
			
//			mapper.testUpdate(memberList.get(i).getUserid(), RandomStringUtils.random(13, true, true));
			log.info(memberList.get(i).getUserid() + "/" + memberList.get(i).getRandomString());
		}
		return 0;
	}

	@Override
	public int checkEmailDuplicate(String email) {
		
		log.info("checkEmailDuplicate........");
		
		log.info(email + "/" + mapper.checkUseridDuplicate(email));
		return mapper.checkUseridDuplicate(email);
	}

	
	
	
	
	
/* 공통 */
	// 텍스트 파일 읽기 메서드	
	@Override
	public String readTextFile(String fileName) {
		
		List<String> htmlStrList = null;
		
		try {
			
			htmlStrList = Files.readAllLines(Paths.get("D:/workspace_sts3_moviepedia/moviepedia/src/main/webapp/resources/codeFile/" + fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		htmlStrList.forEach(string -> log.info(string));
		
		StringBuilder sbHtmlStrList = new StringBuilder();
		
		for(int i=0;i<htmlStrList.size();i++) { 
			sbHtmlStrList.append(htmlStrList.get(i));
		}			

		String htmlStr = sbHtmlStrList.toString(); // toString() : StringBuilder -> String
		
		log.info(htmlStr);
		

		return htmlStr;
	}
	

	
	// 회원가입 이메일 본인인증 메일 전송
	@Override
	public String sendVerificationEmail(String email) {

		String validationCode = RandomStringUtils.random(6, false, true);
		
		// 이메일 전송 양식
		String fromEmail = mailSender.getUsername();
		String toMail = email;
		String title = "[MOVIEPEDIA] 회원 가입 인증 이메일입니다.";
		String htmlContent = readTextFile("signUpVerificationEmailHtml.txt");
		htmlContent = htmlContent.replace("validationCode", validationCode);
		
		// 이메일 전송
		MimeMessage message = mailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
				// true 매개값 전달 시 multipart 형식의 메세지 전달이 가능해짐. 이때 문자 인코딩 설정도 가능
			
			helper.setFrom(fromEmail);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(htmlContent, true); 
				// true 매개값 전달 시 html 형식으로 전송(작성 x-> 단순 텍스트로 전달)
			
			// 파일 첨부
			File file = new File("");
			log.info(file.getAbsolutePath() + "/src/main/webapp/resources/img/logo.png"); // 상대경로
			helper.addInline("logo", new FileDataSource("D:\\workspace_sts3_moviepedia\\moviepedia\\src\\main\\webapp\\resources\\img\\logo.png")); 
//			helper.addInline(contentId, dataSource); : 자원 첨부 메서드. FileDataSource 클래스 함께 이용
			
			
			mailSender.send(message);
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return validationCode;
	}


	
	
	@Override
	public String createJwtToken(String userid) {
		
		Date now = new Date(); // 현재 시각
		
		/* header 작성 */
		Map<String, Object> header = new HashMap<String, Object>();
		
		header.put("type", "JWT");
		header.put("signatureAlgorithm", "HS256");
		// 토큰 서명하기 위해 사용해야할 알고리즘 선택
			// HS256 : 대칭 키 사용 -> 2개이상의 영역에서 하나의 키를 사용
		header.put("regDate", System.currentTimeMillis());
		
		/* claims 생성 */
			// 클레임 : 토큰에 포함될 주요 정보
		Map<String, Object> claims = new HashMap<String, Object>();
		
		claims.put("userid", userid);
		

		
		return Jwts.builder()
				.setSubject(userid) // 보통 username(계정명) 사용
				.setHeader(header)
				.setClaims(claims)
				.setExpiration(new Date(now.getTime() + EXPIRED_TIME))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}

	@Override
	public String getUseridFromJwtToken(String jwtToken) {
		
		String userid = null;
		
		// 토큰 유효기간 만료 체크
		if(! isTokenExpired(jwtToken)) {
			userid = String.valueOf(getAllClaims(jwtToken).get("userid"));
			
			log.info("getUseridFromJwtToken... " + userid);
		} else {
			log.info("token is expired");
			// userid = null
		}
				
		return userid;
	}

	@Override
	public Claims getAllClaims(String jwtToken) {

		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken).getBody();
	}

	@Override
	public boolean isTokenExpired(String jwtToken) {
		Claims claims = getAllClaims(jwtToken);
		
		return claims.getExpiration().before(new Date());
	}

	
	// 비밀번호 찾기 - 비밀번호 변경 이메일 전송
	@Override
	public String sendPasswordResetEmail(String email) {
		String jwtToken = createJwtToken(email);
		log.info(jwtToken);
		
		// 이메일 전송 양식
		String fromEmail = mailSender.getUsername();
		String toMail = email;
		String title = "[MOVIEPEDIA] 새로운 비밀번호를 설정해주세요.";
		String htmlContent = readTextFile("passwordResetEmailHtml.txt");
		htmlContent = htmlContent.replace("resetPassword/", "resetPassword/" + jwtToken);

		
		// 이메일 전송
		MimeMessage message = mailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
				// true 매개값 전달 시 multipart 형식의 메세지 전달이 가능해짐. 이때 문자 인코딩 설정도 가능
			
			helper.setFrom(fromEmail);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(htmlContent, true); 
				// true 매개값 전달 시 html 형식으로 전송(작성 x-> 단순 텍스트로 전달)
			
			
			// 파일 첨부
			File file = new File("");
			log.info(file.getAbsolutePath() + "/src/main/webapp/resources/img/logo.png"); // 상대경로
			helper.addInline("logo", new FileDataSource("D:\\workspace_sts3_moviepedia\\moviepedia\\src\\main\\webapp\\resources\\img\\logo.png")); 
//			helper.addInline(contentId, dataSource); : 자원 첨부 메서드. FileDataSource 클래스 함께 이용
			
			
			mailSender.send(message);
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jwtToken;
	}

	
	// 비밀번호 변경 메서드
	@Override
	public int resetPassword(String userid, String userpw) {
		
		MemberVO memberVO = get(userid);
		log.info(userid + "/" + userpw);
		log.info(memberVO);
		
		// 비밀번호 암호화
		memberVO.setUserpw(passwordEncoder.encode(userpw));

		return mapper.updatePassword(memberVO);
	}
	
	// 계정 잠금 여부 체크 메서드
	@Override
	public boolean isAccountNonLocked(String userid) {
		
		log.info("isAccountNonLocked.... " + userid);
		
		return mapper.getFailureCntAccountNonLocked(userid).isAccountNonLocked();
	}

	// 계정 잠금 해제 메서드
	@Override
	public int unlockAccountnonlocked(String userid) {
		
		log.info("unlockAccountnonlocked... " + userid);
		
		// FAILURECNT 0 초기화 / ACCOUNTNONLOCKED 1 초기화
		return mapper.changeAccountNonLocked(userid);
	}

	// 변경 비밀번호/기존 비밀번호 일치 여부 체크 메서드
	@Override
	public boolean checkUserpwSameness(String userid, String userpw) {
		
		log.info("checkUserpwSameness..... " +userid);
		
		return passwordEncoder.matches(userpw, mapper.read(userid).getUserpw()); // matches(rawPassword, encodedPassword);
	}

	
	
	// 계정 삭제 메서드
	@Transactional
	@Override
	public boolean withdraw(String userid) {
		log.info("withdraw...." + userid);
		
		
		// 코멘트 삭제
		boolean removeCommentResult = commentService.removeUserCommentList(userid);
		
		// 좋아요 삭제
		boolean removeLikeResult = likeService.removeUserLikeList(userid);
		
		// 댓글 삭제
		boolean removeReplyResult = replyService.removeUserReplyList(userid);
		
		// 별점 평가 삭제
		boolean removeStarRatingResult = starRatingService.removeUserStarRatingList(userid);
		
		// 계정 및 권한 삭제
		boolean removeUserResult = removeUserNAuth(userid);
		
		if(! removeCommentResult||! removeLikeResult||! removeReplyResult||! removeStarRatingResult||! removeUserResult ) {
			log.info("계정 관련 모든 정보 삭제 실패");
			return false;
		}
		
		// 성공
		log.info("계정 관련 모든 정보 삭제 성공");
		return true;
	}

	
	// 계정 및 계정 권한 삭제 메서드
	@Transactional
	@Override
	public boolean removeUserNAuth(String userid) {

		log.info("removeUserNAuth..." + userid);
		
		// 계정 권한 삭제
		int removeAuthListResult = mapper.removeAuthList(userid);
		
		// 계정 정보 삭제
		int removeResult = mapper.remove(userid);
		
		if(removeAuthListResult == 0 || removeResult == 0) {
			
			log.info("계정 정보 및 권한 삭제 실패");
			return false;
		}
		
		log.info("계정 정보 및 권한 삭제 성공");
		// 성공
		return true;
	}

/* 프로필 수정 */
	@Override
	public MemberVO modifyUserNameNIntroduction(MemberVO memberVO) {
		
		log.info("modifyUserNameNIntroduction... " + memberVO);
		
		mapper.updateUserNameNIntroduction(memberVO);
		
		return memberVO;
	}
	
	
	

}
