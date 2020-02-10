package org.lebedeva.servlet.task;

import org.lebedeva.AssistantMethods;
import org.lebedeva.dao.CategoryDao;
import org.lebedeva.dao.TaskDao;
import org.lebedeva.model.Category;
import org.lebedeva.model.Task;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/addTask")
public class AddTaskServlet extends HttpServlet {
    CategoryDao categoryDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        categoryDao = (CategoryDao) req.getSession().getAttribute("categoryDao");

        if (categoryDao != null) {
            try {
                req.setAttribute("allCategories", categoryDao.getAll());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        req.getRequestDispatcher("WEB-INF/jsp/addTask.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/addTask.jsp");
        TaskDao taskDao = (TaskDao) req.getSession().getAttribute("taskDao");

        if (taskDao != null && categoryDao != null) {

            if (AssistantMethods.checkParameter(req.getParameter("shortDescription"))
                    && AssistantMethods.checkParameter(req.getParameter("longDescription"))
                    && AssistantMethods.checkParameter(req.getParameter("categoryName"))
                    && AssistantMethods.checkParameter(req.getParameter("start"))
                    && AssistantMethods.checkParameter(req.getParameter("end"))) {

                java.util.Date start = Date.valueOf(req.getParameter("start").trim());
                java.util.Date end = Date.valueOf(req.getParameter("end").trim());

                if (AssistantMethods.checkDate(start, end)) {

                    try {
                        taskDao.save(Task.builder()
                                .shortDescription(req.getParameter("shortDescription").trim())
                                .longDescription(req.getParameter("longDescription").trim())
                                .category(Category.builder().name(req.getParameter("categoryName").trim()).build())
                                .start(Date.valueOf(req.getParameter("start").trim()))
                                .end(Date.valueOf(req.getParameter("end").trim())).build());

                        resp.getWriter().println(AssistantMethods.getHtmlTag("New task added."));

                    } catch (SQLException e) {
                        resp.getWriter().println(AssistantMethods.getHtmlTag("Server error."));
                        e.printStackTrace();
                    }
                } else {
                    resp.getWriter().println(AssistantMethods.getHtmlTag(
                            "Error. Date cannot be less than the current date."));
                }
            } else {
                resp.getWriter().println(AssistantMethods.getHtmlTag("Wrong data."));
            }

            try {
                req.setAttribute("allCategories", categoryDao.getAll());
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            resp.sendRedirect(req.getContextPath() + "/addTask");
        }
        dispatcher.include(req, resp);
    }
}


