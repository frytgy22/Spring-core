<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@include file="template/header.jsp" %>


<div class="jumbotron">
    <div class="container">
        <p>Add category</p>
        <form method="post" action="${pageContext.request.contextPath}/addCategory">
            <div class="form-row align-items-center">
                <div class="col-auto my-1">
                    <label for="inputPassword2" class="sr-only"></label>
                    <input required class="form-control input" id="inputPassword2"
                           placeholder="New category" maxlength="50" name="categoryName">
                </div>
                <div class="col-auto my-1">
                    <button type="submit" class="btn btn-primary">Add category</button>
                </div>
            </div>
        </form>
    </div>
</div>


<%@include file="template/footer.jsp" %>
