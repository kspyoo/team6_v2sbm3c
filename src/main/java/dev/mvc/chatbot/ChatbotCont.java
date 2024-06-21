package dev.mvc.chatbot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChatbotCont {

    @GetMapping("/chatbot")
    public String chatbotForm(){
        return "chatbot";
    }
}
