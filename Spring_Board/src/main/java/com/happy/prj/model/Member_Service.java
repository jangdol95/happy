package com.happy.prj.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.prj.dtos.Member_DTO;

@Service
public class Member_Service implements Member_IService {

	private Logger logger=LoggerFactory.getLogger(Member_Service.class);
	
	@Autowired
	private Member_Interface member_interface;
	
	@Override
	public List<Member_DTO> memList() {
		logger.info("회원조회 memList");
		return member_interface.memList();
	}

	@Override
	public boolean signupmember(Member_DTO dto) {
		logger.info(" signupmember{}",dto);
		return member_interface.signupmember(dto);
	}

	@Override
	public boolean idduplicatecheck(String id) {
		logger.info("아이디 중복검사 idduplicatecheck{}",id);
		return member_interface.idduplicatecheck(id);
	}

	@Override
	public Member_DTO loginMember(Member_DTO dto) {
		logger.info("로그인 loginMember{}",dto);
		return member_interface.loginMember(dto);
	}

}
