package org.lebedeva.servlet.category;

import org.lebedeva.AssistantMethods;
import org.lebedeva.dao.CategoryDao;
import org.lebedeva.model.Category;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addCategory")
public class AddCategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/addCategory.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/addCategory.jsp");
        CategoryDao categoryDao = (CategoryDao) req.getSession().getAttribute("categoryDao");

        if (categoryDao != null && AssistantMethods.checkParameter(req.getParameter("categoryName"))) {
            try {
                final String CATEGORY_NAME = req.getParameter("categoryName").trim();
                Category category = Category.builder().name(CATEGORY_NAME).build();

                categoryDao.save(category);
                resp.getWriter().println(AssistantMethods.getHtmlTag("New category added."));

            } catch (Exception e) {
                resp.getWriter().println(AssistantMethods.getHtmlTag("Category already exists."));
                e.printStackTrace();
            }
        } else {
            resp.getWriter().println(AssistantMethods.getHtmlTag("Wrong data."));
        }
        dispatcher.include(req, resp);
    }
}
