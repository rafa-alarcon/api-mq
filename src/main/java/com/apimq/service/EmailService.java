package com.apimq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	private JavaMailSender mailSender;
	
	@Value("${email.from}")
	private String from;
	@Value("${email.success.subject}")
	private String successSubject;
	@Value("${email.success.message}")
	private String successMessage;
	@Value("${email.failure.subject}")
	private String failureSubject;
	@Value("${email.failure.message}")
	private String failureMessage;
	
	public EmailService(@Autowired JavaMailSender mailSender) {
		super();
		this.mailSender = mailSender;
	}

	public void sendSuccessMessage(String emailTo) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(emailTo);
		message.setSubject(successMessage);
		message.setText(successMessage);
		mailSender.send(message);
	}
	
	public void sendFailureMessage(String emailTo) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(emailTo);
		message.setSubject(failureSubject);
		message.setText(failureMessage);
		mailSender.send(message);
	}

	public void sendMessage() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo("rafaalata@gmail.com");
		message.setSubject(successSubject);
		message.setText(successMessage);
		mailSender.send(message);
	}

}
