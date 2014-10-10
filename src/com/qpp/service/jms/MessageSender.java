package com.qpp.service.jms;

import com.qpp.service.jms.base.BaseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by SZ_it123 on 2014/9/11.
 */
@Service
public class MessageSender {
    @Autowired
    public JmsTemplate jmsTemplate;

    public void sendMessage(BaseMessage baseMessage){
        jmsTemplate.convertAndSend(baseMessage);
    }
}