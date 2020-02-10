package org.lebedeva.servlet.category;

import org.lebedeva.AssistantMethods;
import org.lebedeva.dao.CategoryDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deleteCategory")
public class DeleteCategoryServlet extends HttpServlet {
    CategoryDao categoryDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        categoryDao = (CategoryDao) req.getSession().getAttribute("categoryDao");

        if (categoryDao != null) {
            try {
                req.setAttribute("categories", categoryDao.getAll());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        req.getRequestDispatcher("WEB-INF/jsp/deleteCategory.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/deleteCategory.jsp");

        if (categoryDao != null) {
            if (AssistantMethods.checkParameter(req.getParameter("categoryId"))) {

                try {
                    final long ID = Long.parseLong(req.getParameter("categoryId").trim());
                    categoryDao.delete(ID);

                } catch (Exception e) {
                    resp.getWriter().println(AssistantMethods.getHtmlTag("Wrong data."));
                    e.printStackTrace();
                }
            } else {
                resp.getWriter().println(AssistantMethods.getHtmlTag("Wrong data."));
            }

            try {
                req.setAttribute("categories", categoryDao.getAll());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            dispatcher.include(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/deleteCategory");
        }
    }
}
