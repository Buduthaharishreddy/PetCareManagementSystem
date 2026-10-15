package com.petcare.servlet;
import java.io.IOException; import javax.servlet.*; import javax.servlet.http.*;
public class HomeServlet extends HttpServlet {
 protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
  if(req.getSession(false)!=null && req.getSession(false).getAttribute("userId")!=null) resp.sendRedirect(req.getContextPath()+"/dashboard");
  else resp.sendRedirect(req.getContextPath()+"/login");
 }
}
