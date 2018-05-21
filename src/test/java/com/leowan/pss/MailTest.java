package com.leowan.pss;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.leowan.pss.service.BaseServiceTest;

public class MailTest extends BaseServiceTest {
	@Autowired
	MailSender mailSender;

	@Test
	public void testName() throws Exception {

//JavaMailSenderImpl xxx = (JavaMailSenderImpl)mailSender
		for (;;) {
			// 简单邮件对象
			SimpleMailMessage msg = new SimpleMailMessage();
			// 发送人:和配置一致
			msg.setFrom("20972647@qq.com");
			// 收件人
			msg.setTo("994917004@qq.com");
			// 主题
			msg.setSubject("牛皮大学录取通知书");
			// 内容
			msg.setText("你已经被录取了");
			// 设置固定回邮地址
			msg.setReplyTo("20972647@qq.com");
			// 发送
			mailSender.send(msg);
			
		}
	}
}
