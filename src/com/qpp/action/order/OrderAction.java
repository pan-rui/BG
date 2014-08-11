package com.qpp.action.order;

import com.qpp.action.BaseAction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 订单处理
 * Created by qpp on 8/7/2014.
 */
@Controller
public class OrderAction extends BaseAction {

    @RequestMapping("/order/testt.hyml")
//    @ResponseBody
    public String testt(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        System.out.println("in httpClient.");
        ServletInputStream sis = request.getInputStream();
        StringBuffer sb = new StringBuffer();
        byte[] bytes = new byte[4096];
        int len=-1;
        while ((len = sis.read(bytes)) != -1) {
            sb.append(new String(bytes, 0, len, "utf-8"));
        }
        System.out.println(URLDecoder.decode(sb.toString(), "UTF-8"));
        Map<String, String[]> reqmap = request.getParameterMap();
        Set<Map.Entry<String, String[]>> entry = (Set<Map.Entry<String, String[]>>) reqmap.entrySet();

        for (Iterator<Map.Entry<String, String[]>> it = entry.iterator(); it.hasNext(); ) {
            Map.Entry<String, String[]> en = it.next();
            System.out.println(en.getKey() + "\t" + en.getValue()[0]);
        }
        try {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write("response form bgm..d ..");
            pw.print("response from  bgm..................");
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "testt";
    }
}
