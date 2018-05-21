package com.leowan.pss.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service("quartzJob")
public class QuartzJobServiceImpl {
	@Autowired
	MailSender mailSender;

	public void work() {
		System.out.println("现在的时间是:" + new Date().toLocaleString());
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
