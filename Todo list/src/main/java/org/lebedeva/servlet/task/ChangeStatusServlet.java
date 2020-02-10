package org.lebedeva.servlet.task;

import org.lebedeva.AssistantMethods;
import org.lebedeva.dao.TaskDao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/status")
public class ChangeStatusServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            TaskDao taskDao = (TaskDao) req.getSession().getAttribute("taskDao");

            if (taskDao != null
                    && AssistantMethods.checkParameter(req.getParameter("taskId"))
                    && AssistantMethods.checkParameter(req.getParameter("status"))) {

                final long ID = Long.parseLong(req.getParameter("taskId").trim());
                final String STATUS = req.getParameter("status").trim().equals("UNCHECKED") ? "1" : "2";

                taskDao.update(ID, STATUS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath() + "/tasks");
    }
}
