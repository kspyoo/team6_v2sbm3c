package dev.mvc.member;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class Email {
  
  @Autowired
  private JavaMailSender javaMailSender;
  
  public String sendEmail(String toAddr) throws MessagingException {
    String fromAddr = "vkjiu486@gmail.com";
    
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
    
    helper.setSubject("WITHPET 인증번호");
    helper.setFrom(fromAddr);
    helper.setTo(toAddr);
    
    Random random = new Random();

    String acceptnum="";
    for (int i=0;i<6;i++) {
      acceptnum += Integer.toString(random.nextInt(10));
    }
    
    String html = "<div>"
        + "  WITHPET 인증번호<br>"
        + "입력란에 '" +acceptnum+"'를 입력하세요"
        + "</div>";
    
    helper.setText(html, true);
    javaMailSender.send(mimeMessage);

    return acceptnum;
    
  }
}
