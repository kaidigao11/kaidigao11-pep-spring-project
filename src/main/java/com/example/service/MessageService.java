package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Transactional
@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    } 

    public Message createNewMessage(Message message){
        Optional<Account> accountOptional = accountRepository.findById(message.getPostedBy());
        if (message.getMessageText() != "" && message.getMessageText().length() <=255 && accountOptional.isPresent()) {
            return messageRepository.save(message);
        }
        return null;
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId){
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isPresent()) {
            return messageRepository.findById(messageId).get();
        }
        return null;
    }
                
    public Integer deleteMessageById(Integer messageId){
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isPresent()) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    public Integer updateMessageById(Integer messageId, Message newMessage){
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isPresent() && newMessage.getMessageText() != "" && newMessage.getMessageText().length() <= 255) {
            Message persistedMessage = messageOptional.get();
            persistedMessage.setMessageText(newMessage.getMessageText());
            messageRepository.save(persistedMessage);
            return 1;
        }
        return 0;
    }

    public List<Message> getAllMessagesByAccountId (Integer accountId){
        return messageRepository.findMessagesByPostedBy(accountId);
    }


    
}
