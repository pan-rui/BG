<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%--
  Created by IntelliJ IDEA.
  User: qpp
  Date: 7/18/2014
  Time: 12:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<table>
    <tr><td>交易号</td></tr>
<%
    Map<String,Object> data= (Map<String, Object>) request.getAttribute("data");
    ArrayList<Map<String,String>> result= (ArrayList<Map<String, String>>) data.get("result");
    for(Map<String,String> map:result){
%>
    <tr><td><%=map.get("transactionId")%></td></tr>
    <%
    }
%>
</table>
</body>
</html>
