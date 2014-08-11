<%--
  Created by IntelliJ IDEA.
  User: qpp
  Date: 7/18/2014
  Time: 11:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>交易管理</title>
</head>
<body>
<div><p>查找交易</p></div>
<br/>
<div>
    <form action=/order/Ttransaction.hyml method="post">
       <span>开始日期: <input type="date" name="startDate" /></span><br/>
        <span>结束日期:<input type="date" name="endDate" /></span><br/>
        <input type="submit" value="提交">

    </form>
</div>
</body>
</html>
