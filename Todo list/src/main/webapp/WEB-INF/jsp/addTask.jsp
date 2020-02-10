<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@include file="template/header.jsp" %>

<c:choose>

    <c:when test="${!empty(requestScope.allCategories) and requestScope.allCategories.size()>0}">
        <div class="container container2">
            <br>
            <span class="display-5">New task</span>
            <img src="static/images/1.png" alt="task">

            <form method="post" action="${pageContext.request.contextPath}/addTask">
                <div class="form-group">
                    <label for="exampleFormControlTextarea1"></label>
                    <textarea class="form-control" id="exampleFormControlTextarea1" rows="1" required
                              name="shortDescription">Task name</textarea>
                </div>

                <div class="form-group">
                    <label for="exampleFormControlTextarea2"></label>
                    <textarea class="form-control" id="exampleFormControlTextarea2" rows="3" required
                              name="longDescription">Description</textarea>
                </div>

                <div class="form-group">
                    <label for="exampleFormControlSelect1">Category</label>
                    <select class="form-control" id="exampleFormControlSelect1" required name="categoryName">

                        <c:forEach items="${requestScope.allCategories}" var="category">
                            <option value="${category.name}">${category.name}</option>
                        </c:forEach>

                    </select>
                </div>

                <div class="row">
                    <div class="col">
                        <input onfocus="(this.type='date')" class="form-control" placeholder="The date of the beginning"
                               type="text" required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" name="start"
                               onblur="(this.type='text')">
                    </div>
                    <div class="col">
                        <input type="text" onfocus="(this.type='date')" class="form-control"
                               placeholder="Expiration date"
                               required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" name="end" onblur="(this.type='text')">
                    </div>
                </div>
                <br>
                <button type="submit" class="btn btn-primary">Add task</button>
            </form>
        </div>
    </c:when>

    <c:otherwise>
        <div class="jumbotron">
            <div class="container">
                <p>The list of categories is empty. Before adding a task, add a category.</p>
                <p><a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/addCategory"
                      role="button">Add new category &raquo;</a></p>
            </div>
        </div>
    </c:otherwise>

</c:choose>

<%@include file="template/footer.jsp" %>