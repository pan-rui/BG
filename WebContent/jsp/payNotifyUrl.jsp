<%--
  Created by IntelliJ IDEA.
  User: qpp
  Date: 7/14/2014
  Time: 11:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
<%--<script type="text/javascript" src="/js/jquery-1.9.1.js"></script>--%>
<%--<script type="text/javascript">--%>
    <%--$(function (el) {--%>
        <%--window.history.go(-2);--%>
    <%--})--%>

<%--</script>--%>
<center><p>Order is Success</p><br>
    <p>点击这里查看<a href="/order/detail.hyml?transactionId=${TRANSACTIONID}" >订单详情</a></p>&nbsp;&nbsp;&nbsp;
    <p><a href="javascript:history.go(-3);" >返回继续购物</a></p>
</center>

</body>
</html>
