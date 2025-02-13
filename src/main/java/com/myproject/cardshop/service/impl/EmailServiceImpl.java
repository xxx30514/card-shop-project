package com.myproject.cardshop.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import com.myproject.cardshop.email.EmailTemplateName;
import com.myproject.cardshop.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender mailSender;

	private final SpringTemplateEngine templateEngine;
	
	@Async
	@Override
	public void sendEmail(String to, String username, EmailTemplateName emailTemplate, String confirmationUrl,
			String activationCode, String subject) throws MessagingException {
		//選擇模板名稱
		String templateName;
		if (emailTemplate == null) {
			templateName = "confirm-email";
		} else {
			templateName = emailTemplate.getName();
		}
		//寄送郵件的工具類
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		//MimeMessageHelper.MULTIPART_MODE_MIXED表示可攜帶附件
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED,
				StandardCharsets.UTF_8.name());
		//設置email的變量給Thymeleaf模板引擎渲染
		Map<String, Object> properties = new HashMap<>();
		properties.put("username", username);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activationCode", activationCode);
        //存儲和傳遞模板渲染所需的變量
        Context context = new Context();
        context.setVariables(properties);
        //寄件人信箱
        mimeMessageHelper.setFrom("xxx30514@gmail.com");
        //收件人信箱
        mimeMessageHelper.setTo(to);
        //email主題
        mimeMessageHelper.setSubject(subject);
        //渲染模板
        String template = templateEngine.process(templateName, context);
        //設置發送內容  true表示為HTML格式而不是純文本
        mimeMessageHelper.setText(template, true);
        //寄送郵件
        mailSender.send(mimeMessage);
	}

}
