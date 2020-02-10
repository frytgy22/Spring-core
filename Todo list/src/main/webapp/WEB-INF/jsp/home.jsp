<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="template/header.jsp" %>

<main role="main">

    <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
        <div class="container">
            <h3 class="display-3">Organize your tasks with Assistant.ToDo</h3>
            <p>Assistant.ToDo is an award-winning app used by millions of people to stay organized and get more
                done.</p>
            <p><a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/addTask"
                  role="button">Create new task &raquo;</a></p>
        </div>
    </div>

    <div class="container">
        <!-- Example row of columns -->
        <div class="row">
            <div class="col-md-4">
                <h2>My tasks</h2>
                <p>Wherever you are, take your to do list with you.<br>
                    It's comfortable. It's easy.</p>
                <p><a class="btn btn-secondary" href="${pageContext.request.contextPath}/tasks"
                      role="button">View details &raquo;</a></p>
            </div>
            <div class="col-md-4">
                <h2>New tasks</h2>
                <p>Regain clarity and calmness by getting all those tasks out of your head and onto your to-do list.</p>
                <p><a class="btn btn-secondary" href="${pageContext.request.contextPath}/addTask" role="button">View
                    details &raquo;</a></p>
            </div>
            <div class="col-md-4">
                <h2>Categories</h2>
                <p>You can divide your tasks into different categories for ease of use. You can delete your category.</p>
                <p><a class="btn btn-secondary" href="${pageContext.request.contextPath}/addCategory"
                      role="button">View details &raquo;</a></p>
            </div>
        </div>

        <hr>

    </div> <!-- /container -->

</main>

<%@include file="template/footer.jsp" %>


