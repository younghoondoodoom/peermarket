package peermarket.peershop.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;
import peermarket.peershop.entity.ChatMessage;
import peermarket.peershop.websocket.Greeting;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greeting")
    public Greeting greeting(ChatMessage message) throws Exception {
        Thread.sleep(1000);
        return new Greeting(
            "Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @GetMapping("/chat")
    public String chat() {
        return "chat/index";
    }
}
