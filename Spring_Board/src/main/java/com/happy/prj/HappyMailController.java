package com.happy.prj;

import java.io.UnsupportedEncodingException;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class HappyMailController {
	
	private Logger logger=LoggerFactory.getLogger(HappyMailController.class);
	
	@Autowired
	private JavaMailSender mailSender;
	
	@RequestMapping(value="/mail.do", method=RequestMethod.GET)
	public String mail() {
		logger.info("MailController mail");
		return "mailForm";
	}
	@RequestMapping(value="/mailSending.do", method=RequestMethod.POST)
	public String mailSending(String toMail, String title, String content) {
		String setFrom="info.happy0612@gmail.com";//보내는사람
		//toMail:받는사람메일
		//title:메일의 제목
		//content:메일의 내용
		
		
		try {
			MimeMessage message=mailSender.createMimeMessage();//메일 보내는 객체
			MimeMessageHelper  messageHelper=new MimeMessageHelper(message,true,"UTF-8");
			
			messageHelper.setFrom(setFrom);//보내는 사람   주소를 생략하거나 입력하지 않으면 작동되지 않는다
			messageHelper.setTo(toMail);//받는 사람의 주소 입력
			messageHelper.setSubject(title);//제목 생략해도된다
//			messageHelper.setText(content);//메일 전송내용 //setText(content,boolean);// boolean HTML사용여부
			messageHelper.setText(content,true);
			
			DataSource dataSource=new FileDataSource("C:\\Users\\Hee\\Downloads\\장석영.jpg");
			try {
				messageHelper.addAttachment(MimeUtility.encodeText("장석.jpg", "UTF-8","B"),dataSource);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			//setCC(String), setCC(String[]):참조메일 주소
			//setBCC(String), setBCC(String[]):숨음참조메일 주소
			//messageHelper.setSentDate(Date); : 예약메일
			
			mailSender.send(message);
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/memberList.do";
	}
	
}
