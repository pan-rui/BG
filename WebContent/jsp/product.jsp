<%@ page import="java.util.Map" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%
	/* *
	 *功能：支付宝即时到账交易接口调试入口页面
	 *版本：3.3
	 *日期：2012-08-17
	 *说明：
	 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
	 */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<title>Paypal即时到账交易接口</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
*{
	margin:0;
	padding:0;
}
ul,ol{
	list-style:none;
}
.title{
    color: #ADADAD;
    font-size: 14px;
    font-weight: bold;
    padding: 8px 16px 5px 10px;
}
.hidden{
	display:none;
}

.new-btn-login-sp{
	border:1px solid #D74C00;
	padding:1px;
	display:inline-block;
}

.new-btn-login{
    background-color: transparent;
    background-image: url("../images/new-btn-fixed.png");
    border: medium none;
}
.new-btn-login{
    background-position: 0 -198px;
    width: 82px;
	color: #FFFFFF;
    font-weight: bold;
    height: 28px;
    line-height: 28px;
    padding: 0 10px 3px;
}
.new-btn-login:hover{
	background-position: 0 -167px;
	width: 82px;
	color: #FFFFFF;
    font-weight: bold;
    height: 28px;
    line-height: 28px;
    padding: 0 10px 3px;
}
.bank-list{
	overflow:hidden;
	margin-top:5px;
}
.bank-list li{
	float:left;
	width:153px;
	margin-bottom:5px;
}

#main{
	width:750px;
	margin:0 auto;
	font-size:14px;
	font-family:'宋体';
}
#logo{
	background-color: transparent;
    background-image: url("../images/new-btn-fixed.png");
    border: medium none;
	background-position:0 0;
	width:166px;
	height:35px;
    float:left;
}
.red-star{
	color:#f00;
	width:10px;
	display:inline-block;
}
.null-star{
	color:#fff;
}
.content{
	margin-top:5px;
}

.content dt{
	width:160px;
	display:inline-block;
	text-align:right;
	float:left;
	
}
.content dd{
	margin-left:100px;
	margin-bottom:5px;
}
#foot{
	margin-top:10px;
}
.foot-ul li {
	text-align:center;
}
.note-help {
    color: #999999;
    font-size: 12px;
    line-height: 130%;
    padding-left: 3px;
}

.cashier-nav {
    font-size: 14px;
    margin: 15px 0 10px;
    text-align: left;
    height:30px;
    border-bottom:solid 2px #CFD2D7;
}
.cashier-nav ol li {
    float: left;
}
.cashier-nav li.current {
    color: #AB4400;
    font-weight: bold;
}
.cashier-nav li.last {
    clear:right;
}
.alipay_link {
    text-align:right;
}
.alipay_link a:link{
    text-decoration:none;
    color:#8D8D8D;
}
.alipay_link a:visited{
    text-decoration:none;
    color:#8D8D8D;
}
</style>
        <script type="text/javascript" src="../js/jquery-1.9.1.js">

        </script>
</head>
    <%
        Map<String, String[]> requestMap=request.getParameterMap();
//        String requestData=JSON.toJSONString(requestMap);
        Map<String,String> userMap=new HashMap<String, String>();
        for(Iterator<String> it=requestMap.keySet().iterator();it.hasNext();){
            String str=it.next();
            userMap.put(str,requestMap.get(str)[0]);
        }
        request.getSession().setAttribute("userInfo",userMap);
//            Map<String,String[]> testMap=new HashMap<String,String[]>();testMap.put("abc",new String[]{"a","b"});
//        request.getSession().setAttribute("user", testMap);
        String basePath="http://localhost:8080"+request.getContextPath();
    %>
<body text=#000000 bgColor="#ffffff" leftMargin=0 topMargin=4>
	<div id="main">
		<div id="head">
            <dl class="alipay_link">
                <a target="_blank" href="www.paypal.com/"><span>Paypal首页</span></a>|
                <a target="_blank" href="https://www.paypal.com/hk/webapps/mpp/merchant?locale.x=zh_HK"><span>商家服务</span></a>|
                <a target="_blank" href="https://www.paypal.com/cn/webapps/helpcenter/home/"><span>帮助中心</span></a>
            </dl>
            <span class="title">商品展示</span>
		</div>
        <div class="cashier-nav">

        </div>
        <form name=alipayment action=alipayapi.jsp method=post target="_blank">
            <div id="body" style="clear:left">
                <dl class="content">
					<dt>product_A:</dt>
					<dd>
						<span class="null-star">*</span>
                        <a href="javascript:void(0)" onclick="javascript:var na=$(this).parent().prev().text();window.location.href='productDetail.jsp?productId=001&na='+na.substr(0,na.length-1)"><img src="../images/p1.jpg" alt=""/></a>
						<span>
</span>
					</dd>
                    <dt id="bb">product_B:</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <a href="javascript:void(0)" onclick="javascript:var na=$(this).parent().prev().text();window.location.href='productDetail.jsp?productId=002&na='+na.substr(0,na.length-1)"><img src="../images/p2.jpg" alt=""/></a>
						<span>
</span>
                    </dd>
                    <dt>product_C:</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <a href="javascript:void(0)" onclick="javascript:var na=$(this).parent().prev().text();window.location.href='productDetail.jsp?productId=003&na='+na.substr(0,na.length-1)"><img src="../images/p3.jpg" alt=""/></a>
						<span>
</span>
                    </dd>
                    <%--<dt></dt>--%>
                    <%--<dd>--%>
                        <%--<span class="new-btn-login-sp">--%>
                            <%--<button class="new-btn-login" type="submit" style="text-align:center;">确 认</button>--%>
                        <%--</span>--%>
                    <%--</dd>--%>
                </dl>
            </div>
		</form>
        <div id="foot">

		</div>
	</div>
</body>
</html>