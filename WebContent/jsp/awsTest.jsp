<%--
  Created by IntelliJ IDEA.
  User: qpp
  Date: 7/25/2014
  Time: 13:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AWS测试</title>
</head>
<body>
<form action="/order/aws_pay.hyml" method="post">
    <input type="hidden" name="CallerReference" value="panrui-520@163.com" />
    <input type="hidden" name="CallerDescription" value="Aws Test" />
    <input type="hidden" name="RecipientTokenId" value="M_PANRAI_162945813" />
    <input type="hidden" name="SenderTokenId" value="M_PANRAI_162945813" />
    <input type="hidden" name="SenderDescription" value="aws-test" />
    <input type="hidden" name="TransactionAmount" value="23.5" />
    <input type="submit" value="pay" />
</form>
</body>
</html>
