<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@include file="template/header.jsp" %>
<script src="static/js/tasks.js"></script>

<jsp:useBean id="now" class="java.util.Date"/>

<c:choose>

    <c:when test="${!empty(requestScope.tasks) and requestScope.tasks.size() > 0}">
        <div class="container container2">
            <br>
            <span class="display-5">Your tasks</span>
            <img src="static/images/1.png" alt="task">
            <table id="table" class="table table-hover">
                <thead class="thead-light">
                <tr>
                    <th scope="col">Status</th>
                    <th scope="col">Title</th>
                    <th scope="col">Description</th>
                    <th scope="col">Category</th>
                    <th scope="col">Created</th>
                    <th scope="col">Start</th>
                    <th scope="col">End</th>
                    <th scope="col">Delete</th>
                    <th class="hidden" scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.tasks}" var="task">
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${task.status.name() eq 'CHECKED'}">
                                    <img class="task" src="static/images/5.png" alt="CHECKED">
                                </c:when>
                                <c:otherwise>
                                    <img class="task" src="static/images/2.png" alt="UNCHECKED">
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${task.shortDescription}</td>
                        <td>${task.longDescription}</td>
                        <td>${task.category.name}</td>
                        <td>${task.created}</td>
                        <td>${task.start}</td>
                        <td <c:if test="${task.end lt now and task.status.name() eq 'UNCHECKED'}">
                            style="color: red" </c:if>>${task.end}
                        </td>
                        <td>
                            <img class="task" src="static/images/15.png" alt="delete">
                        </td>
                        <td class="hidden">${task.id}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:when>

    <c:otherwise>
        <div class="jumbotron">
            <div class="container">
                <p>There are no tasks yet.</p>
                <p><a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/addTask"
                      role="button">Create new task &raquo;</a></p>
            </div>
        </div>
    </c:otherwise>

</c:choose>

<form class="hidden" id="delete" method="post" action="${pageContext.request.contextPath}/deleteTask">
    <input type="hidden" name="taskId">
</form>

<form class="hidden" id="status" method="post" action="${pageContext.request.contextPath}/status">
    <input type="hidden" name="taskId">
    <input type="hidden" name="status">
</form>

<%@include file="template/footer.jsp" %>
