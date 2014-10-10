package com.qpp.service.jms;

import com.qpp.service.jms.base.BaseMessage;
import com.qpp.service.jms.base.IMessageController;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by SZ_it123 on 2014/9/11.
 */
@Service
public class MessageReceiver implements MessageListener {
    private Logger logger=Logger.getLogger(this.getClass());

    @Override
    public void onMessage(Message m) {
        logger.info("Client get a Message:");
        try{
            ActiveMQObjectMessage textMsg = (ActiveMQObjectMessage)m;
            BaseMessage baseReturn=(BaseMessage)textMsg.getObject();
            IMessageController controller=baseReturn.getController();
            controller.execute(baseReturn.getMap());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}