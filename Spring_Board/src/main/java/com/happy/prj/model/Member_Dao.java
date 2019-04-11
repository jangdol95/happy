package com.happy.prj.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.happy.prj.dtos.Member_DTO;

@Repository
public class Member_Dao implements Member_Interface {

	private final String NS="com.happy.prj.Statement_Member.";
	@Autowired
	private SqlSessionTemplate sqlSession;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public List<Member_DTO> memList() {
		return sqlSession.selectList(NS+"memList");
	}

	@Override
	public boolean signupmember(Member_DTO dto) {
		//전달 받은비밀번호를 암호화 처리
		String encodePw=passwordEncoder.encode(dto.getPw());
		dto.setPw(encodePw);
		
		int n=sqlSession.insert(NS+"signupmember",dto);
		return n>0?true:false;
	}

	@Override
	public boolean idduplicatecheck(String id) {
		int n=sqlSession.selectOne(NS+"idduplicatecheck",id);
		return n>0?true:false;
	}

	@Override
	public Member_DTO loginMember(Member_DTO dto) {
//		String securityPW=sqlSession.selectOne(NS+"selStringPW",dto);
//		String encodePw=passwordEncoder.encode(dto.getPw());
//		
//		System.out.println("DB의 PW:"+securityPW);
//		System.out.println("입력받은 PW:"+encodePw);
//		
//		if(securityPW.equals(encodePw)) {
//			System.out.println("비밀번호 일치");
//			return sqlSession.selectOne(NS+"loginMember",dto);
//		}
		
		String securityPW=sqlSession.selectOne(NS+"selStringPw",dto);
		if(passwordEncoder.matches(dto.getPw(), securityPW)) {
			dto.setPw(securityPW);
			System.out.println("비밀번호 일치");
			return sqlSession.selectOne(NS+"loginMember",dto);
		}
		
		return null;
	}

}
