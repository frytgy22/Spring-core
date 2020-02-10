<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@include file="template/header.jsp" %>

<c:choose>

    <c:when test="${!empty(requestScope.categories) and requestScope.categories.size()>0}">
        <div class="jumbotron">
            <div class="container">
                <p>Delete category</p>
                <form method="post" action="${pageContext.request.contextPath}/deleteCategory">
                    <div class="form-row align-items-center">
                        <div class="col-auto my-1">
                            <label class="mr-sm-2 sr-only" for="inlineFormCustomSelect"></label>
                            <select required name="categoryId" class="custom-select mr-sm-2 input"
                                    id="inlineFormCustomSelect">

                                <c:forEach items="${requestScope.categories}" var="category">
                                    <option value="${category.id}">${category.name}</option>
                                </c:forEach>

                            </select>
                        </div>
                        <div class="col-auto my-1">
                            <button type="submit" class="btn btn-primary">Delete category</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </c:when>

    <c:otherwise>
        <div class="jumbotron">
            <div class="container">
                <p>There are no category yet.</p>
                <p><a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/addCategory"
                      role="button">Add new category &raquo;</a></p>
            </div>
        </div>
    </c:otherwise>

</c:choose>

<%@include file="template/footer.jsp" %>
