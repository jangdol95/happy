package com.happy.prj.model;

import java.util.List;
import java.util.Map;

import com.happy.prj.dtos.Answerboard_DTO;
import com.happy.prj.dtos.RowNum_DTO;

public interface AnswerBoard_IService {

	// 글입력
	public boolean writeBoard(Answerboard_DTO dto);

	// 답글입력
	public boolean reply(Answerboard_DTO dto);

	// 상세글 조회
	public Answerboard_DTO getOneBoard(String seq);

	// 조회수 증가
	public boolean readcountBoard(String seq);

	// 글 수정
	public boolean modifyBoard(Answerboard_DTO dto);

	// 글삭제(삭제로 변경)
	public boolean delflagBoard(Map<String, String[]> map);

	// 삭제글 찾기
	public List<Answerboard_DTO> deleteBoardSel(String seq);

	// 글삭제(진짜 삭제DB삭제)
	public boolean deleteBoard(Map<String, String[]> map);

	// 글조회(전체 -사용자)
	public List<Answerboard_DTO> userBoardList();

	// 글조회
	public List<Answerboard_DTO> adminBoardList();

	// 글조회(전체 페이징 사용자)
	public List<Answerboard_DTO> userBoardListRow(RowNum_DTO dto);

	// 글조회(전체 페이징 관리자)
	public List<Answerboard_DTO> adminBoardListRow(RowNum_DTO dto);

	// 글갯수(전체 사용자)
	public int userBoardListTotal();

	// 글갯수(전체 관리자)
	public int adminBoardListTotal();
}
