package org.lebedeva;

import java.util.Date;

public interface AssistantMethods {
    static boolean checkParameter(String data) {
        return data != null && data.trim().length() > 0;
    }

    static String getHtmlTag(String message) {
        return "<p style=\"top: 3.5rem; left:10px; position: absolute; color: red\">" + message + "</p>";
    }

    static boolean checkDate(Date start, Date end) {
        Date now = new Date();

        return (start.after(now)) && end.after(now) && end.after(start);
    }
}
