<%@ page import="java.util.Map" %>
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
	<title><%= request.getParameter("na")%></title>
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
/*.null-star{*/
	/*color:#fff;*/
/*}*/
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
            var cart=cart||[];
            function addCart(el){
            cart[cart.length]={
                productId:el.form.productId.value,
                productName:el.form.productName.value,
                price:el.form.price.value,
                isbn:el.form.isbn.value,
                productSpec:el.form.sp1.value+":"+el.form.va1.value+";"+el.form.sp2.value+":"+el.form.va2.value,
                productCount:el.form.count.value,
                comment:""
            }
               // cconsole.log(${sessionScope.userInfo['email']});
                setCookie("${userInfo['username']}_cart",JSON.stringify(cart));
                alert("添加成功!!!")
            }
        </script>

</head>
<body text="black" leftMargin=0 topMargin=4> <p><%=request.getContextPath()%></p>

<div id="main">
		<div id="head">
            <dl class="alipay_link">
                <a target="_blank" href="www.paypal.com/"><span>Paypal首页</span></a>|
                <a target="_blank" href="https://www.paypal.com/hk/webapps/mpp/merchant?locale.x=zh_HK"><span>商家服务</span></a>|
                <a target="_blank" href="https://www.paypal.com/cn/webapps/helpcenter/home/"><span>帮助中心</span></a>
            </dl>
            <span class="title">商品信息</span>
		</div>

        <form name=alipayment action=order.jsp method=post target="_blank">
            <div id="body" style="clear:left">
                <dl class="content">
					<dt>商品名：</dt>
					<dd>
						<span class="null-star">*</span>
						<input size="30" name="productName" readonly="true" value="<%=request.getParameter("na")%>" />
						<span>
</span>
					</dd>
                    <input type="hidden" name="productId" readonly="true" value="<%=request.getParameter("productId")%>" />
					<dt>商品规格:</dt><br/>
					<dd>
						<span >规格名:</span>
						<input size="30" name="sp1" value=""/>
                        <span >规格值:</span>
						<input size="30" name="va1" value=""/>
						<span>
</span>
					</dd>
                    <dd>
                        <span >规格名:</span>
                        <input size="30" name="sp2" value=""/>
                        <span >规格值:</span>
                        <input size="30" name="va2" value=""/>
						<span>
</span>
                    </dd>
					<dt>价格：</dt>
					<dd>
						<span class="null-star">*</span>
						<input size="30" name="price" readonly="true" value="3.5" />
						<span>
</span>
					</dd>
                    <dt>数量：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <input size="30" name="count" />
						<span>
</span>
                    </dd>
                    <dt>编号：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <input size="30" name="isbn" readonly="true" value="02241344362430023" />
						<span>
</span>
                    </dd>
                    <dt></dt>
                    <dd>
                        <span class="new-btn-login-sp">
                            <button  type="button" style="text-align:center;background-image: url('../images/but22.gif');" onclick="addCart(this);">添加到购物车</button>
                        </span>
                    </dd>

                    <dt></dt>
                    <dd>
                        <span class="new-btn-login-sp">
                            <button class="new-btn-login" type="submit" style="text-align:center;background-image: url('../images/but23.gif');">立即购买</button>
                        </span>
                    </dd>
                </dl>
            </div>
		</form>
        <center><a href="javascript:window.location.href='cart.jsp?cart='+JSON.stringify(cart)">查看购物车</a><br>
                <a href="javascript:window.history.go(-1);">返回</a>
        </center>
        <div id="foot">

		</div>
	</div>
</body>
</html>