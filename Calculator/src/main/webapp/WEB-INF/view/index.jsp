<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <link rel="shortcut icon" href="static/images/icon.png" type="image/png">
    <title>Calculator</title>
</head>
<body>

<p>
    <c:if test="${!empty(calculator)}">
        ${calculator.firstArg} ${calculator.operand} ${calculator.secondArg} =
    </c:if>

    ${result}
</p>
</body>
</html>
