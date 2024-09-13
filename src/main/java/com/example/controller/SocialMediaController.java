package com.example.controller;

import org.springframework.context.ApplicationContext;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }
    /**
     * 
     */
    @PostMapping(value = "/register")
    public ResponseEntity createNewAccountHandler(@RequestBody Account newAccount){
        if (newAccount.getUsername()!="" && newAccount.getPassword().length()>4 && accountService.containsAccount(newAccount.getUsername())==null ) {
            Account resultAccount = accountService.createNewAccount(newAccount);
            return ResponseEntity.status(200).body(resultAccount);
        }
        else if (accountService.containsAccount(newAccount.getUsername()) != null) {
            return ResponseEntity.status(409).body(null);
        }
        else {
            return ResponseEntity.status(400).body(null);
        }

        
    }

    /**
     * 
     */
    @PostMapping(value = "/login")
    public ResponseEntity loginAccountHandler(@RequestBody Account newAccount){
        Account resultAccount = accountService.loginAccount(newAccount);
        if (resultAccount != null) {
            return ResponseEntity.status(200).body(resultAccount);
        }
        return ResponseEntity.status(401).body(null);
    }

    /**
     * 
     */
    @PostMapping(value = "/messages")
    public ResponseEntity createNewMessageHandler(@RequestBody Message newMessage){
        Message resultMessage = messageService.createNewMessage(newMessage);
        if (resultMessage != null) {
            return ResponseEntity.status(200).body(resultMessage);
        }
        return ResponseEntity.status(400).body(null);
    } 

    /**
     * 
     */
    @GetMapping(value = "/messages")
    public ResponseEntity getAllMessagesHandler(){
        List<Message> allMessages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(allMessages);

    }

    /**
     * 
     */
    @GetMapping(value = "/messages/{messageId}")
    public ResponseEntity getMessageByIdHandler(@PathVariable Integer messageId){
        Message resultMessage = messageService.getMessageById(messageId);
        return ResponseEntity.status(200).body(resultMessage);



    }

    /**
     * 
     */
    @DeleteMapping(value = "/messages/{messageId}")
    public ResponseEntity deleteMessageByIdHandler(@PathVariable Integer messageId){
        Integer rowsDeleted = messageService.deleteMessageById(messageId);
        if (rowsDeleted > 0) {
            return ResponseEntity.status(200).body(rowsDeleted);
        }
        return ResponseEntity.status(200).body(null);

    }

    /**
     * 
     */
    @PatchMapping(value = "/messages/{messageId}")
    public ResponseEntity updateMessageByIdHandler(@PathVariable Integer messageId, @RequestBody Message newMessage){
        Integer rowsUpdated = messageService.updateMessageById(messageId, newMessage);
        if (rowsUpdated > 0) {
            return ResponseEntity.status(200).body(rowsUpdated);
        }
        return ResponseEntity.status(400).body(null);
    }

    /**
     * 
     */
    @GetMapping(value = "/accounts/{accountId}/messages")
    public ResponseEntity getAllMessagesByAccountIdHandler(@PathVariable Integer accountId){
        List<Message> allMessagesByUser = messageService.getAllMessagesByAccountId(accountId);
        return ResponseEntity.status(200).body(allMessagesByUser);

    }

}