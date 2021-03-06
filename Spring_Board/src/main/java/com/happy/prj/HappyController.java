package com.happy.prj;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happy.prj.dtos.Answerboard_DTO;
import com.happy.prj.dtos.Member_DTO;
import com.happy.prj.dtos.RowNum_DTO;
import com.happy.prj.model.AnswerBoard_IService;
import com.happy.prj.model.Member_IService;

@Controller
public class HappyController {

	Logger logger = LoggerFactory.getLogger(HappyController.class);

	@Autowired
	private Member_IService iMember;
	@Autowired
	private AnswerBoard_IService iAnswerBoard;

	@RequestMapping(value = "/intro.do", method = RequestMethod.GET)
	public String intro() {
		logger.info("처음 시작하는 Spring Intro Page");

		return "intro";
	}

	@RequestMapping(value = "/loginForm.do", method = RequestMethod.GET)
	public String loginForm() {
		logger.info("Controller loginForm");
		return "loginForm";
	}

	@RequestMapping(value = "/loginCheck.do", method = RequestMethod.POST, produces = "application/text;charset=UTF-8")
	@ResponseBody
	public String loginCheck(Member_DTO dto) {
		logger.info("요청받은값:{}{}", dto.getId(), dto.getPw());
		Member_DTO ldto = iMember.loginMember(dto);
		logger.info("Welcome loginCheck");
		return (ldto == null) ? "실패" : "성공/" + ldto.getAuth();
	}

	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String login(HttpSession session, Member_DTO dto) {
		Member_DTO ldto = iMember.loginMember(dto);
		logger.info("Controller login{}", ldto);
		session.setAttribute("mem", ldto);
		return "redirect:/boardList.do";
	}

	@RequestMapping(value = "/signUpForm.do", method = RequestMethod.GET)
	public String signUpForm() {
		logger.info("Controller signUpForm");
		return "signUpForm";
	}

	@RequestMapping(value = "/signUp.do", method = RequestMethod.POST)
	public String signUp(Member_DTO dto) {
		boolean isc = iMember.signupmember(dto);
		logger.info("Controller signUp {} {}", isc, new Date());
		return isc ? "redirect:/loginForm.do" : "redirect:/signUpForm.do";
	}

	@RequestMapping(value = "/idCheck.do", method = RequestMethod.POST, produces = "application/text;charset=UTF-8")
	@ResponseBody
	public String idCheck(String id) {
		String regex = "^[a-zA-Z0-9]*$";
		boolean isc = id.matches(regex);

		// 중복이면 True나옴
		boolean isc2 = iMember.idduplicatecheck(id);
		System.out.println(isc2);

		logger.info("Controller signUp{}//{}", isc, new Date());
		return (isc2 == false) ? "사용가능한 아이디 입니다." : "중복된 아이디 입니다.";
	}

	@RequestMapping(value = "/memberList.do", method = RequestMethod.GET)
	public String memberList(Model model) {
		List<Member_DTO> lists = iMember.memList();
		logger.info("Controller memberList{}", lists);
		model.addAttribute("mlists",lists);
		return "membarList";
	}

	@RequestMapping(value = "/logOut.do", method = RequestMethod.GET)
	public String logOut(HttpSession session) {
		Member_DTO dto = (Member_DTO) session.getAttribute("mem");
		if (dto != null) {
			session.removeAttribute("mem");
			session.removeAttribute("row");
			// session.invalidate();
		}
		return "redirect:/loginForm.do";
	}

	///////// -----------------------------------------------------
	@RequestMapping(value = "/boardWriteForm.do", method = RequestMethod.GET)
	public String boardWriteForm() {
		logger.info("Controller boardWriteForm {}");
		return "boardWriteForm";
	}
	@RequestMapping(value = "/write.do", method = RequestMethod.POST)
	public String boardWrite(Answerboard_DTO dto) {
		logger.info("Controller boardWrite{}",dto);
		boolean isc=iAnswerBoard.writeBoard(dto);
		return isc?"redirect:/boardList.do":"boardWriteForm";
	}
	
	
//	boardList.do
	@RequestMapping(value = "/boardList.do", method = RequestMethod.GET)
	public String boardList(HttpSession session, Model model) {
		logger.info("Controller boardList{}", (Member_DTO) session.getAttribute("mem"));
		Member_DTO dto=(Member_DTO) session.getAttribute("mem");
		
		RowNum_DTO rowDto=null;
		List<Answerboard_DTO> lists=null;
		
		//session에 페이지에 관련된 정보를 담는다."row"
		if(session.getAttribute("row")==null) {
			rowDto=new RowNum_DTO();
		}else {
			rowDto=(RowNum_DTO) session.getAttribute("row");
		}
		//---------페이지 정보
		if(dto.getAuth().trim().equals("U")) {
			rowDto.setTotal(iAnswerBoard.userBoardListTotal());
			lists=iAnswerBoard.userBoardListRow(rowDto);
		}else {
			rowDto.setTotal(iAnswerBoard.adminBoardListTotal());
			lists=iAnswerBoard.adminBoardListRow(rowDto);
		}
		
		model.addAttribute("lists",lists);
		model.addAttribute("row",rowDto);
		
		return "boardList";
	}
	@RequestMapping(value = "/paging.do", method = RequestMethod.POST,produces="application/text; charset=UTF-8")
	@ResponseBody
	public String paging(Model model,HttpSession session,RowNum_DTO rowDto) {
		Member_DTO dto=(Member_DTO) session.getAttribute("mem");
		logger.info("Controller boardList{}", (Member_DTO) session.getAttribute("mem"));
		JSONObject json=null;
		
		if(dto.getAuth().trim().equalsIgnoreCase("U")) {
			rowDto.setTotal(iAnswerBoard.userBoardListTotal());
			//게시판 글조회 객체->JSON으로 담음
			json=objectJson(iAnswerBoard.userBoardListRow(rowDto), rowDto, dto);
		}else {
			rowDto.setTotal(iAnswerBoard.adminBoardListTotal());
//			게시판 글조회 객체->JSON으로 담음
			json=objectJson(iAnswerBoard.adminBoardListRow(rowDto), rowDto, dto);
		}
		
		session.removeAttribute("row");
		session.setAttribute("row", rowDto);
		logger.info("Controller paging{}",json.toString());
		
		return json.toString();
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject objectJson(List<Answerboard_DTO> lists, RowNum_DTO row, Member_DTO mem) {
		JSONObject json=new JSONObject();
		JSONArray jLists=new JSONArray();
		JSONObject jList=new JSONObject();
		
		for(Answerboard_DTO dto:lists) {
			jList=new JSONObject();
			jList.put("seq",dto.getSeq());
			jList.put("id",dto.getId());
			jList.put("title",dto.getTitle());
			jList.put("content",dto.getContent());
			jList.put("readcount",dto.getReadcount());
			jList.put("delflag",dto.getDelflag());
			jList.put("regdate",dto.getRegdate());
			jList.put("memid",dto.getId());
			
			jLists.add(jList);
			
		}
		
		//페이징에 관련된
		jList=new JSONObject();
		jList.put("pageList",row.getPageList());
		jList.put("index",row.getIndex());
		jList.put("pageNum",row.getPageNum());
		jList.put("listNum",row.getListNum());
		jList.put("total",row.getTotal());
		jList.put("count",row.getCount());
		
		json.put("lists", jLists);
		json.put("row", jList);
		
		
		
		return json;
	}
	@RequestMapping(value = "/multidel.do", method = RequestMethod.POST)
	public String multidel(String[] chkVal,Model model) {
		logger.info("Controller multidel{}",Arrays.toString(chkVal));
		Map<String, String[]> map=new HashMap<String,String[]>();
		map.put("seq_", chkVal);
		boolean isc=iAnswerBoard.delflagBoard(map);
		if(isc) {
			return "redirect:/boardList.do";
		}else {
			model.addAttribute("errMsg","잘못된 접근입니다;");
			model.addAttribute("url","./loginForm.do");
			return "error_req";
			
		}
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modifyForm.do", method = RequestMethod.POST,produces="application/text;charset=UTF-8")
	@ResponseBody
	public String modifyForm(String seq) {
		JSONObject json= new JSONObject();
		Answerboard_DTO dto=iAnswerBoard.getOneBoard(seq);
		System.out.println(dto);
		json.put("seq", dto.getSeq());
		json.put("id", dto.getId());
		json.put("title", dto.getTitle());
		json.put("content", dto.getContent());
		json.put("readcount", dto.getReadcount());
		json.put("regdate", dto.getRegdate());
		logger.info("Controller modifyForm{}",json.toString());
		
		return json.toString();
	}
	@RequestMapping(value = "/modify.do", method = RequestMethod.POST)
	public String modify(Answerboard_DTO dto) {
		logger.info("Controller modifyForm{}",dto);
		boolean isc=iAnswerBoard.modifyBoard(dto);
		return "redirect:/boardList.do";
	}
	@RequestMapping(value = "/del.do", method = RequestMethod.GET)
	public String del(String[] seq,HttpSession session) {
		Member_DTO mdto=(Member_DTO) session.getAttribute("mem");
		Map<String, String[]> map=new HashMap<String,String[]>();
		map.put("seq_", seq);
		
		if(mdto.getAuth().trim().equalsIgnoreCase("U")) {//일반유저
			iAnswerBoard.delflagBoard(map);
		}else {//관리자
			iAnswerBoard.deleteBoard(map);
		}
		return "redirect:/boardList.do";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/replyForm.do", method = RequestMethod.POST,produces="application/text;charset=UTF-8")
	@ResponseBody
	public String replyForm(HttpSession session,String seq) {
		JSONObject json= new JSONObject();
		Answerboard_DTO dto=iAnswerBoard.getOneBoard(seq);
		Member_DTO mdto=(Member_DTO)session.getAttribute("mem");
//		System.out.println(dto);
		json.put("seq", dto.getSeq());
		json.put("id", dto.getId());
		json.put("title", dto.getTitle());
		json.put("content", dto.getContent());
		json.put("readcount", dto.getReadcount());
		json.put("regdate", dto.getRegdate());
		json.put("memid",mdto.getId());
		logger.info("Controller replyForm{}",json.toString());
		
		return json.toString();
	}
	@RequestMapping(value="/replyForm2.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Answerboard_DTO> repyForm2(String seq){
		Map<String, Answerboard_DTO> map=new HashMap<String,Answerboard_DTO>();
		Answerboard_DTO dto=iAnswerBoard.getOneBoard(seq);
		map.put("dto", dto);
		return map;
	}
	@RequestMapping(value="/reply.do", method = RequestMethod.POST)
	public String reply(HttpSession session,Answerboard_DTO dto) {
		Member_DTO mdto=(Member_DTO)session.getAttribute("mem");
		dto.setId(mdto.getId());
		logger.info("Controller reply{}",dto); 
		boolean isc=iAnswerBoard.reply(dto);
		logger.info("Controller reply{}",isc);
		return "redirect:/boardList.do";
	}
}























