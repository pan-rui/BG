package com.qpp.service.jms.base;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by SZ_it123 on 2014/9/11.
 */
public class BaseMessage implements Serializable {
    private static final long serialVersionUID = 268573299606213300L;
    private IMessageController controller;
    private Map<String,Object> map;
    public BaseMessage(IMessageController lcontroller, Map<String,Object> lmap){
        controller=lcontroller;
        map=lmap;
    }

    public IMessageController getController() {
        return controller;
    }

    public void setController(IMessageController controller) {
        this.controller = controller;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
