package com.leantech.service;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.apimq.service.EmailService;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
	@Mock
	private JavaMailSender mailSender;
	
	@InjectMocks
	private EmailService emailService;
	
	@Test
	void testMailSenderCall() {
		emailService.sendMessage();
		verify(mailSender,atLeastOnce()).send(ArgumentMatchers.any(SimpleMailMessage.class));
	}
}
