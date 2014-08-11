<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.mongodb.util.JSON" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.net.URLDecoder" %>
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
	<title>购物车商品列表</title>
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
        <script type="text/javascript" src="../js/jquery-1.9.1.js"></script>
        <script type="text/javascript" src="../js/common.js"></script>
        <script type="text/javascript">
            $(function(){
                $(document.forms[0]).submit(function(){
                    setCookie("${userInfo['username']}_cart",JSON.stringify(cart));
                    var itemAmt=0;
                    for(vv in cart){
                        itemAmt+=parseInt(cart[vv].productCount)*parseFloat(cart[vv].price);
                    }
//                    $("#amt").val(itemAmt);
                    $("#itemAmt").val(itemAmt);
//                    this.submit();
//                    alert("abcd")
                });
                var cart=cart||JSON.parse(getCookie("${userInfo['username']}_cart"));
                $("input[name='count'],input[name='comment']").on("change",function(){
                    alert("kk");
                    cart[$(this).closest("tr").attr("index")][$(this).attr("name")]=$(this).val();
                })
            });

        </script>
</head>
<body text=#000000 bgColor="#ffffff" leftMargin=0 topMargin=4>
	<div id="main">
		<div id="head">
            <dl class="alipay_link">
                <a target="_blank" href="www.paypal.com/"><span>Paypal首页</span></a>|
                <a target="_blank" href="https://www.paypal.com/hk/webapps/mpp/merchant?locale.x=zh_HK"><span>商家服务</span></a>|
                <a target="_blank" href="https://www.paypal.com/cn/webapps/helpcenter/home/"><span>帮助中心</span></a>
            </dl>
            <span class="title">商品信息</span>
		</div>
            <form id="form" action="order.jsp">
            <center>
            <table>
                <tr>
                    <th>商品名称</th><th>价格</th><th>编号</th><th>规格</th><th>数量</th><th>描述</th>
                </tr>
                <%
                    String cart=new String(request.getParameter("cart").getBytes("iso-8859-1"),"utf-8");
//                    Cookie[] cookies=request.getCookies();URLDecoder.decode(cookies[0].getValue());
                    List<Map> list= (List<Map>) JSON.parse(cart);
                    int i=0;
                    for(Map map:list){
                %>
                <tr index="<%=i%>">
                    <td><%=map.get("productName")%></td>
                    <td><%=map.get("price")%></td>
                    <td><%=map.get("isbn")%></td>
                    <td><%=map.get("productSpec")%></td>
                    <td><input type="text" name="count" value="<%=map.get("productCount")%>" /></td>
                    <td><input type="text" name="comment" value="<%=map.get("comment")%>" /></td>
                </tr>
                <%  i++;
                    }
                %>
            </table>
                <input type="hidden" id="amt" name="amt" value=""/>
                <input type="hidden" id="itemAmt" name="itemAmt" value=""/>


                <button class="new-btn-login" type="submit" style="text-align:center;background-image: url('../images/but23.gif');">立即购买</button>
                <a href="javascript:window.history.go(-1);">返回</a>
        </center>
        </form>
        <div id="foot">

		</div>
	</div>
</body>
</html>