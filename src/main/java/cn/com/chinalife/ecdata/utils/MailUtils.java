package cn.com.chinalife.ecdata.utils;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by xiexiangyu on 2018/4/12.
 */
public class MailUtils {
    private static JavaMailSenderImpl mailSender;

    static {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:conf/spring-mail.xml");
        mailSender = (JavaMailSenderImpl) applicationContext.getBean("mailSender");
    }

    public static void sendHtmlMail(String from, String[] to, String cc, String bcc, String subject, String htmlContent) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        if (cc != null) {
            mimeMessageHelper.setCc(cc);
        }
        if (bcc != null) {
            mimeMessageHelper.setBcc(bcc);
        }
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
    }

    public static void sendMailTest() throws MessagingException {
        StringBuilder htmlContent = new StringBuilder()
                .append("<html>")
                .append("<head>")
                .append("<title>")
                .append("Spring自带JavaMailSender发送的HTML邮件")
                .append("</title>")
                .append("</head>")
                .append("<body>")
                .append("<p>您好!陌生人</p>")
                .append("</body>")
                .append("</html>");
        sendHtmlMail("348452440@qq.com", new String[]{"348452440@qq.com", "348452440@qq.com"}, "348452440@qq.com", "348452440@qq.com", "test", htmlContent.toString());
    }

    public static void main(String[] args) throws MessagingException {
//        System.setProperty("java.net.preferIPv4Stack", "true");
        sendMailTest();
    }
}
