//package com.project_study.my.common.utils;
//
//import com.ithinkdt.web.common.constant.Constants;
//import com.ithinkdt.web.common.view.MailView;
//import com.sun.mail.util.MailSSLSocketFactory;
//import freemarker.core.ParseException;
//import freemarker.template.MalformedTemplateNameException;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import freemarker.template.TemplateNotFoundException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.mail.MailException;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.mail.javamail.MimeMessagePreparator;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.stereotype.Component;
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeUtility;
//import java.io.File;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URISyntaxException;
//import java.security.GeneralSecurityException;
//import java.util.HashMap;
//import java.util.IdentityHashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Map.Entry;
//
//@Component
//@Slf4j
//public class MailUtil implements Constants {
//	/**
//	 * 模版配置器
//	 */
//	@Autowired
//    private FreeMarkerConfigurer freeMarkerConfigurer;
//	public static MailUtil instance;
//
//	public MailUtil(){
//		instance = this;
//	}
//
//	private JavaMailSender getMailSender(){
//		JavaMailSenderImpl sender = new JavaMailSenderImpl();
//		// TODO APPLICATION
//		sender.setHost(ApplicationConfigUtil.getProperty(CONFIG_MAIL_SENDER_HOST));
//		sender.setPort(ApplicationConfigUtil.getInteger(CONFIG_MAIL_SENDER_PORT));
//		sender.setUsername(ApplicationConfigUtil.getProperty(CONFIG_MAIL_SENDER_USER_NAME));
//		sender.setPassword(ApplicationConfigUtil.getProperty(CONFIG_MAIL_SENDER_PASSWORD));
//
//		sender.getJavaMailProperties().put("mail.smtp.auth", "true");
//		sender.getJavaMailProperties().put("mailAddress", ApplicationConfigUtil.getProperty(CONFIG_MAIL_SENDER_USER_NAME));
//		if("TLS".equals(ApplicationConfigUtil.getProperty(CONFIG_MAIL_ENCRYPTION_TYPE))) {
//			sender.getJavaMailProperties().put("mail.smtp.starttls.enable", "true");
//			sender.getJavaMailProperties().put("mail.smtp.socketFactory.port",  ApplicationConfigUtil.getInteger(CONFIG_MAIL_SENDER_PORT));
//		} else if("SSL".equals(ApplicationConfigUtil.getProperty(CONFIG_MAIL_ENCRYPTION_TYPE))){
//			sender.getJavaMailProperties().put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//			sender.getJavaMailProperties().put("mail.smtp.socketFactory.port",  ApplicationConfigUtil.getInteger(CONFIG_MAIL_SENDER_PORT));
//			MailSSLSocketFactory sf = null;
//			try {
//			    sf = new MailSSLSocketFactory();
//			    sf.setTrustAllHosts(true);
//			} catch (GeneralSecurityException e) {
//			    log.error("SSL Mode send error", e);
//			}
//			sender.getJavaMailProperties().put("mail.smtp.ssl.enable", "true");
//			sender.getJavaMailProperties().put("mail.smtp.ssl.socketFactory", sf);
//			sender.getJavaMailProperties().put("mail.smtp.socketFactory.fallback", "false");
//		}
//		return sender;
//	}
//
//	private void sendAsyncMail(final MimeMessagePreparator mimeMessage) {
//		((ThreadPoolTaskExecutor) SpringUtils.getBean(BEAN_NAME_THREAD_POOL_TASK_EXECUTOR)).execute(new Runnable() {
//			public void run() {
//				try {
//					getMailSender().send(mimeMessage);
//				} catch (Exception ex) {
//					log.error("邮件发送失败！" + mimeMessage.toString(), ex);
//				}
//			}
//		});
//    }
//
//	private boolean isSendMailOn(){
//		return ApplicationConfigUtil.getBoolean(CONFIG_MAIL_ONOFF);
//	}
//
//	@SuppressWarnings("unused")
//	private void sendMail(final MailView mail) {
//		if (isSendMailOn()) {
//			MimeMessagePreparator preparator = new MimeMessagePreparator() {
//				public void prepare(MimeMessage mimeMessage) throws Exception {
//					MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
//					// TODO APPLICATION
//					message.setFrom(StringUtil.getOrElse(mail.getFrom(), ApplicationConfigUtil.getProperty(CONFIG_MAIL_FROM)));
//					message.setTo(mail.getToArray());
//					message.setSubject(StringUtil.getOrElse(mail.getSubject()));
//					message.setText(mail.getContent(), mail.isHtml());
//				}
//			};
//			if (mail.isSyncMode()) {
//				getMailSender().send(preparator);
//			} else {
//				sendAsyncMail(preparator);
//			}
//		}
//	}
//
//	public static String send(final MailView mail){
//		if(instance != null){
//			return instance.send(mail.getToArray(), mail.getSubject(), mail.getContent(), mail.isHtml(), mail.getViewName(), mail.getParams(), mail.getAttcFile(), mail.isSyncMode());
//		}
//		return null;
//	}
//
//	private final String send(String[] toArray, String subject, String html, boolean isHtml, String viewName, Map<String, Object> params, IdentityHashMap<String, byte[]> attcFileMap, boolean async) {
//		return this.send(null, toArray, null, null, subject, html, isHtml, viewName, params, attcFileMap, async);
//	}
//	private final String send(String fromUserName, String[] toArray, String[] ccArray,
//			String[] bccArray, String subject, String html, boolean isHtml, String viewName,
//			Map<String, Object> params, IdentityHashMap<String, byte[]> attcFileMap, boolean async) {
//		if (!isSendMailOn()) {
//			return null;
//		}
//		final JavaMailSenderImpl senderImpl = (JavaMailSenderImpl) getMailSender();
//		final MimeMessage mime = senderImpl.createMimeMessage();
//		String message = null;
//		try {
//			MimeMessageHelper helper = new MimeMessageHelper(mime, true, "utf-8");
//			int toCnt = 0;
//			for (String to : toArray) {
//				if (StringUtil.validateEmail(to)) {
//					log.info("Reciver : " + to);
//					helper.addTo(to);
//					toCnt ++;
//				}
//			}
//			if (toCnt == 0) {
//				// throw exception
//				throw new RuntimeException("the mail reciver can not be empty");
//			}
//			if (ccArray != null) {
//				for (String cc : ccArray) {
//					if (StringUtil.validateEmail(cc)) {
//						helper.addCc(cc);
//					}
//				}
//			}
//			if (bccArray != null) {
//				for (String bcc : bccArray) {
//					if (StringUtil.validateEmail(bcc)) {
//						helper.addBcc(bcc);
//					}
//				}
//			}
//			String MAIL_FROM = ApplicationConfigUtil.getProperty(CONFIG_MAIL_FROM);
//			String MAIL_DEFAULT_SUBJECT = ApplicationConfigUtil.getProperty(CONFIG_DB_MAIL_DEFAULT_SUBJECT);
//			helper.setFrom(senderImpl.getUsername(), StringUtil.isEmpty(fromUserName) ? MAIL_FROM : fromUserName);
//			helper.setSubject(StringUtil.isEmpty(subject) ? MAIL_DEFAULT_SUBJECT : subject);
//			String MAIL_SIGN = ApplicationConfigUtil.getProperty(CONFIG_DB_MAIL_SIGN);
//			if (isHtml && StringUtil.isNotEmpty(viewName)) {
//				if (params == null) {
//					params = new HashMap<String, Object>();
//				}
//				params.put("mailSign", MAIL_SIGN);
//				Template mailTmpl = freeMarkerConfigurer.getConfiguration().getTemplate(viewName);
//				String mailCotent = FreeMarkerTemplateUtils.processTemplateIntoString(mailTmpl, params);
//				helper.setText(mailCotent, true);
//			} else {
//				helper.setText(html);
//			}
//			String MAIL_SIGN_LOGO_PATH = ApplicationConfigUtil.getProperty(CONFIG_DB_MAIL_SIGN_LOGO_PATH);
//			if (StringUtil.isNotEmpty(MAIL_SIGN_LOGO_PATH)) {
//				String path = null;
//				try {
//					path = this.getClass().getClassLoader().getResource("").toURI().getPath();
//					helper.addInline("logo", new File(path + MAIL_SIGN_LOGO_PATH));
//				} catch (URISyntaxException e) {
//					log.error("logo图片路径错误!", e);
//				}
//			}
//			if (attcFileMap != null && attcFileMap.size() > 0) {
//				Iterator<Entry<String, byte[]>> iterator = attcFileMap.entrySet().iterator();
//				while(iterator.hasNext()){
//					Entry<String, byte[]> entry = iterator.next();
//					if (StringUtil.isNotEmpty(entry.getKey()) && entry.getValue() != null) {
//						helper.addAttachment(MimeUtility.encodeWord(entry.getKey()), new ByteArrayResource(entry.getValue()));
//					}
//				}
//			}
//			if (async) {
//				((ThreadPoolTaskExecutor) SpringUtils.getBean(BEAN_NAME_THREAD_POOL_TASK_EXECUTOR)).execute(new Runnable() {
//					public void run() {
//						senderImpl.send(mime);
//					}
//				});
//			} else {
//				senderImpl.send(mime);
//			}
//		} catch (UnsupportedEncodingException e) {
//			message = e.getMessage();
//			log.error(message, e);
//		} catch (MessagingException e) {
//			message = e.getMessage();
//			log.error(message, e);
//		} catch (MailException e) {
//			message = "mail send failed:Could not connect to SMTP host: " + senderImpl.getHost() + ", port: " + senderImpl.getPort() + "!";
//			log.error(message, e);
//		} catch (TemplateNotFoundException e) {
//			message = "mail template not found!";
//			log.error(message, e);
//		} catch (MalformedTemplateNameException e) {
//			message = e.getMessage();
//			log.error(message, e);
//		} catch (ParseException e) {
//			message = "mail template parse exception!";
//			message = e.getMessage();
//			log.error(message, e);
//		} catch (IOException e) {
//			message = e.getMessage();
//			log.error(message, e);
//		} catch (TemplateException e) {
//			message = e.getMessage();
//			log.error(message, e);
//		}
//		return message;
//	}
//}
