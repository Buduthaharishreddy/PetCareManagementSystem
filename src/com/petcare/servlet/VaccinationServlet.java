package com.petcare.servlet;
import com.petcare.dao.*;import com.petcare.util.WebUtil;import java.io.IOException;import java.sql.Date;import javax.servlet.*;import javax.servlet.http.*;
public class VaccinationServlet extends HttpServlet {
 protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
  WebUtil.setUtf8(req,res);try{req.setAttribute("vaccinations",new VaccinationDAO().listForOwner(WebUtil.userId(req)));req.setAttribute("pets",new PetDAO().listByOwner(WebUtil.userId(req)));req.getRequestDispatcher("/WEB-INF/views/vaccinations.jsp").forward(req,res);}catch(Exception e){getServletContext().log("Vaccination list error",e);WebUtil.forwardError(req,res,"Vaccination history could not be loaded.");}
 }
 protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
  WebUtil.setUtf8(req,res);if(!WebUtil.hasRole(req,"ADMIN","STAFF")){res.sendError(403);return;}
  int petId=WebUtil.intValue(req.getParameter("petId"),-1);String name=WebUtil.clean(req.getParameter("vaccineName")),notes=WebUtil.clean(req.getParameter("notes"));Date date=WebUtil.sqlDate(req.getParameter("vaccinationDate")),next=WebUtil.sqlDate(req.getParameter("nextDueDate"));
  try{
   if(petId<1||name.isEmpty()||date==null||(next!=null&&next.before(date))||!petExists(petId)){WebUtil.setMessage(req,"error","Enter a valid pet, vaccine name, vaccination date, and next due date.");}
   else{boolean ok=new VaccinationDAO().add(petId,WebUtil.userId(req),name,date,next,notes);WebUtil.setMessage(req,ok?"success":"error",ok?"Vaccination recorded.":"Vaccination could not be saved.");}
   res.sendRedirect(req.getContextPath()+"/vaccinations");
  }catch(Exception e){getServletContext().log("Vaccination save error",e);WebUtil.setMessage(req,"error","Vaccination could not be saved.");res.sendRedirect(req.getContextPath()+"/vaccinations");}
 }
 private boolean petExists(int petId)throws Exception{java.sql.Connection c=com.petcare.util.DBConnection.getConnection();try(java.sql.PreparedStatement p=c.prepareStatement("SELECT pet_id FROM pets WHERE pet_id=?")){p.setInt(1,petId);try(java.sql.ResultSet r=p.executeQuery()){return r.next();}}finally{c.close();}}
}
