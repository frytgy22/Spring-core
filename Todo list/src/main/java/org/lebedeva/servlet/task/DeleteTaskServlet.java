package org.lebedeva.servlet.task;

import org.lebedeva.AssistantMethods;
import org.lebedeva.dao.TaskDao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteTask")
public class DeleteTaskServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            TaskDao taskDao = (TaskDao) req.getSession().getAttribute("taskDao");

            if (taskDao != null && AssistantMethods.checkParameter(req.getParameter("taskId"))) {
                final long ID = Long.parseLong(req.getParameter("taskId").trim());

                taskDao.delete(ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath() + "/tasks");
    }
}
