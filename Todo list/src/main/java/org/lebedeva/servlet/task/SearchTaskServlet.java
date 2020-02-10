package org.lebedeva.servlet.task;

import org.lebedeva.AssistantMethods;
import org.lebedeva.dao.TaskDao;
import org.lebedeva.model.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/search")
public class SearchTaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            TaskDao taskDao = (TaskDao) req.getSession().getAttribute("taskDao");

            if (taskDao != null && AssistantMethods.checkParameter(req.getParameter("search"))) {
                final String TASK_SHORT_DESCRIPTION = req.getParameter("search").trim();

                List<Task> tasks = taskDao.getAll();

                List<Task> searchTasks = tasks.stream()
                        .filter(task -> task.getShortDescription() != null
                                && task.getShortDescription().contains(TASK_SHORT_DESCRIPTION))
                        .collect(Collectors.toList());

                req.setAttribute("tasks", searchTasks);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("WEB-INF/jsp/tasks.jsp").forward(req, resp);
    }
}
