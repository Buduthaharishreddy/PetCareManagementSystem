package com.petcare.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class WebUtil {
    public static String clean(String value) {
        return value == null ? "" : value.trim();
    }

    public static int intValue(String value, int defaultValue) {
        try { return Integer.parseInt(clean(value)); }
        catch (NumberFormatException e) { return defaultValue; }
    }

    public static BigDecimal decimalValue(String value) {
        try { return new BigDecimal(clean(value)); }
        catch (Exception e) { return null; }
    }

    public static Date sqlDate(String value) {
        try {
            LocalDate date = LocalDate.parse(clean(value));
            return Date.valueOf(date);
        } catch (Exception e) { return null; }
    }

    public static Time sqlTime(String value) {
        try { return Time.valueOf(LocalTime.parse(clean(value))); }
        catch (Exception e) { return null; }
    }

    public static boolean isFutureOrToday(Date date) {
        return date != null && !date.toLocalDate().isBefore(LocalDate.now());
    }

    public static int userId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) return -1;
        return (Integer) session.getAttribute("userId");
    }

    public static String role(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null ? "" : clean((String) session.getAttribute("role"));
    }

    public static boolean hasRole(HttpServletRequest request, String... roles) {
        String role = role(request);
        for (String allowed : roles) if (allowed.equals(role)) return true;
        return false;
    }

    public static void setMessage(HttpServletRequest request, String type, String message) {
        request.getSession().setAttribute("flashType", type);
        request.getSession().setAttribute("flashMessage", message);
    }

    public static void forwardError(HttpServletRequest request, javax.servlet.http.HttpServletResponse response, String message)
            throws javax.servlet.ServletException, java.io.IOException {
        request.setAttribute("errorMessage", message);
        request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
    }

    public static void setUtf8(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) {
        try { request.setCharacterEncoding("UTF-8"); } catch (UnsupportedEncodingException ignored) { }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
    }
}
