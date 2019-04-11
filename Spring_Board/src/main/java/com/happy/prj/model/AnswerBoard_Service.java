package com.happy.prj.model;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.prj.dtos.Answerboard_DTO;
import com.happy.prj.dtos.RowNum_DTO;
@Service
public class AnswerBoard_Service implements AnswerBoard_IService {
	
	private Logger logger=LoggerFactory.getLogger(AnswerBoard_Service.class);

	@Autowired
	private Answerboard_InterFace answerBoard_Interface;
	
	@Override
	public boolean writeBoard(Answerboard_DTO dto) {
		logger.info("새글작성 writeBoard{}",dto);
		return answerBoard_Interface.writeBoard(dto);
	}

	@Override
	public boolean reply(Answerboard_DTO dto) {
		logger.info("답글reply{}",dto);
		boolean isc=answerBoard_Interface.replyBoardUp(dto);
		logger.info("답글replyUP{}",dto);
		isc=answerBoard_Interface.replyBoardIn(dto);
		logger.info("답글replyIN{}",dto);
		return isc;
	}

	@Override
	public Answerboard_DTO getOneBoard(String seq) {
		logger.info("하나 조회getOneBoard{}",seq);
		return answerBoard_Interface.getOneBoard(seq);
	}

	@Override
	public boolean readcountBoard(String seq) {
		logger.info("조회수 증가readcountBoard{}",seq);
		return answerBoard_Interface.readcountBoard(seq);
	}

	@Override
	public boolean modifyBoard(Answerboard_DTO dto) {
		logger.info(" 글수정modifyBoard{}",dto);
		return answerBoard_Interface.modifyBoard(dto);
	}

	@Override
	public boolean delflagBoard(Map<String, String[]> map) {
		logger.info("delfalg변경 delflagBoard{}",map);
		return answerBoard_Interface.delflagBoard(map);
	}

	@Override
	public List<Answerboard_DTO> deleteBoardSel(String seq) {
		logger.info("하위삭제글 확인 deleteBoardSel{}",seq);
		return answerBoard_Interface.deleteBoardSel(seq);
	}

	@Override
	public boolean deleteBoard(Map<String, String[]> map) {
		logger.info("진짜 삭제 deleteBoard{}",map);
		return answerBoard_Interface.deleteBoard(map);
	}

	@Override
	public List<Answerboard_DTO> userBoardList() {
		logger.info("전체글조회-사용자 userBoardList");
		return answerBoard_Interface.userBoardList();
	}

	@Override
	public List<Answerboard_DTO> adminBoardList() {
		logger.info("전체글조회-관리자 adminBoardList");
		return answerBoard_Interface.adminBoardList();
	}

	@Override
	public List<Answerboard_DTO> userBoardListRow(RowNum_DTO dto) {
		logger.info("페이징사용자 userBoardListRow{}",dto);
		return answerBoard_Interface.userBoardListRow(dto);
	}

	@Override
	public List<Answerboard_DTO> adminBoardListRow(RowNum_DTO dto) {
		logger.info("페이징관리자adminBoardListRow{}",dto);
		return answerBoard_Interface.adminBoardListRow(dto);
	}

	@Override
	public int userBoardListTotal() {
		logger.info("전체글숫자 사용자 userBoardListTotal");
		return answerBoard_Interface.userBoardListTotal();
	}

	@Override
	public int adminBoardListTotal() {
		logger.info("전체글숫자 사용자 adminBoardListTotal");
		return answerBoard_Interface.adminBoardListTotal();
	}

}
