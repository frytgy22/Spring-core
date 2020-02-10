package org.lebedeva.listener;

import org.lebedeva.dao.CategoryDao;
import org.lebedeva.dao.TaskDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.SQLException;

@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        ApplicationContext context = new ClassPathXmlApplicationContext("springConfig.xml");
        TaskDao taskDao = context.getBean(TaskDao.class);
        CategoryDao categoryDao = context.getBean(CategoryDao.class);

        httpSessionEvent.getSession().setAttribute("taskDao", taskDao);
        httpSessionEvent.getSession().setAttribute("categoryDao", categoryDao);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        TaskDao taskDao = (TaskDao) httpSessionEvent.getSession().getAttribute("taskDao");
        CategoryDao categoryDao = (CategoryDao) httpSessionEvent.getSession().getAttribute("categoryDao");

        try {
            taskDao.close();
            categoryDao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
