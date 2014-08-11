package com.qpp.action.user;

import com.qpp.action.BaseAction;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * User: gary,
 * DateTime: 12-8-8 下午2:53
 */
@Controller
public class Chat extends BaseAction{
    @RequestMapping(value = "/room/chat")
    public ModelAndView InitChat(HttpServletRequest request) throws Exception {
        ModelAndView view = new ModelAndView("/room/chat");
        HttpSession session=request.getSession();
        return view;
    }
}
