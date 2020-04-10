package com.controllers;

import com.domain.Message;
import com.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {

    private MessageRepository messageRepository;

    @Autowired
    public GreetingController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String home() {
        return "greeting";
    }

    @GetMapping("/user-page")
    public String userPage(Map<String, Object> model) {
        Iterable<Message> messages = messageRepository.findAll();

        model.put("messages", messages);
        return "main";
    }

    @PostMapping("/save")
    public String saveToDb(@RequestParam String text, @RequestParam String tag, Map<String, Object> map) {
        Message message = Message.builder()
                .tag(tag)
                .text(text)
                .build();
        messageRepository.save(message);

        Iterable<Message> messages = messageRepository.findAll();

        map.put("messages", messages);
        return "main";
    }

    @RequestMapping("/messages/get")
    public String getMessagesList(Map<String, Object> map) {
        Iterable<Message> messages = messageRepository.findAll();
        map.put("messages", messages);
        return "main";
    }

    @RequestMapping("/reset")
    public String reset(){
        return "main";
    }

    @RequestMapping("/filter")
    public String filter(@RequestParam String filter, Map<String, Object> map){
        List<Message> filtered = messageRepository.findByTag(filter);
        map.put("messages", filtered);

        return "main";
    }
}