package com.petcare.servlet;
import com.petcare.dao.*; import com.petcare.util.WebUtil;
import java.io.IOException; import javax.servlet.*; import javax.servlet.http.*;
public class DashboardServlet extends HttpServlet {
 protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
  WebUtil.setUtf8(req,res);
  try{
   req.setAttribute("pets",new PetDAO().listByOwner(WebUtil.userId(req)));
   req.setAttribute("services",new ServiceDAO().listActive());
   req.setAttribute("appointments",new AppointmentDAO().listForOwner(WebUtil.userId(req)));
   req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req,res);
  }catch(Exception e){getServletContext().log("Dashboard error",e);WebUtil.forwardError(req,res,"Dashboard data could not be loaded. Check your database settings.");}
 }
}
