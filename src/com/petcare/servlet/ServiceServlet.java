package com.petcare.servlet;
import com.petcare.dao.ServiceDAO;import com.petcare.model.Service;import com.petcare.util.WebUtil;
import java.io.IOException;import java.math.BigDecimal;import javax.servlet.*;import javax.servlet.http.*;
public class ServiceServlet extends HttpServlet {
 private ServiceDAO dao=new ServiceDAO();
 protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
  WebUtil.setUtf8(req,res);try{req.setAttribute("services",WebUtil.hasRole(req,"ADMIN")?dao.listAll():dao.listActive());req.getRequestDispatcher("/WEB-INF/views/services.jsp").forward(req,res);}catch(Exception e){getServletContext().log("Services error",e);WebUtil.forwardError(req,res,"Services could not be loaded.");}
 }
 protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
  WebUtil.setUtf8(req,res);if(!WebUtil.hasRole(req,"ADMIN")){res.sendError(403);return;}
  String action=WebUtil.clean(req.getParameter("action"));
  try{
   if("delete".equals(action)){boolean ok=dao.delete(WebUtil.intValue(req.getParameter("id"),-1));WebUtil.setMessage(req,ok?"success":"error",ok?"Service deleted.":"Service is in use or could not be deleted.");}
   else {String name=WebUtil.clean(req.getParameter("serviceName")),desc=WebUtil.clean(req.getParameter("description"));BigDecimal price=WebUtil.decimalValue(req.getParameter("price"));int id=WebUtil.intValue(req.getParameter("serviceId"),0);if(name.isEmpty()||price==null||price.compareTo(BigDecimal.ZERO)<0){WebUtil.setMessage(req,"error","Enter a service name and a non-negative price.");}
   else{Service s=new Service();s.setServiceName(name);s.setDescription(desc);s.setPrice(price);s.setActive("1".equals(req.getParameter("active")));boolean ok;if(id>0){s.setServiceId(id);ok=dao.update(s);}else ok=dao.save(s);WebUtil.setMessage(req,ok?"success":"error",ok?"Service saved.":"Could not save service.");}}
   res.sendRedirect(req.getContextPath()+"/services");
  }catch(Exception e){getServletContext().log("Service save error",e);WebUtil.setMessage(req,"error","Could not save the service. It may already exist or be in use.");res.sendRedirect(req.getContextPath()+"/services");}
 }
}
