package com.petcare.servlet;
import com.petcare.dao.*;import com.petcare.util.WebUtil;import java.io.IOException;import java.sql.Date;import javax.servlet.*;import javax.servlet.http.*;
public class MedicalServlet extends HttpServlet {
 protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
  WebUtil.setUtf8(req,res);try{req.setAttribute("records",new MedicalRecordDAO().listForOwner(WebUtil.userId(req)));req.setAttribute("pets",new PetDAO().listByOwner(WebUtil.userId(req)));req.getRequestDispatcher("/WEB-INF/views/medical.jsp").forward(req,res);}catch(Exception e){getServletContext().log("Medical list error",e);WebUtil.forwardError(req,res,"Medical records could not be loaded.");}
 }
 protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
  WebUtil.setUtf8(req,res);if(!WebUtil.hasRole(req,"ADMIN","STAFF")){res.sendError(403);return;}
  int petId=WebUtil.intValue(req.getParameter("petId"),-1);Date date=WebUtil.sqlDate(req.getParameter("recordDate"));String diagnosis=WebUtil.clean(req.getParameter("diagnosis")),treatment=WebUtil.clean(req.getParameter("treatment")),notes=WebUtil.clean(req.getParameter("notes"));
  try{if(date==null||diagnosis.isEmpty()||new PetDAO().findById(petId,ownerOfPet(req,petId))==null){/* staff may record a pet belonging to an owner; validate existence below */}}
  catch(Exception ignored){}
  try{
   if(date==null||diagnosis.isEmpty()||petId<1||!petExists(petId)){WebUtil.setMessage(req,"error","Select a valid pet, record date, and diagnosis.");}
   else{boolean ok=new MedicalRecordDAO().add(petId,WebUtil.userId(req),date,diagnosis,treatment,notes);WebUtil.setMessage(req,ok?"success":"error",ok?"Medical record added.":"Medical record could not be added.");}
   res.sendRedirect(req.getContextPath()+"/medical");
  }catch(Exception e){getServletContext().log("Medical save error",e);WebUtil.setMessage(req,"error","Medical record could not be saved.");res.sendRedirect(req.getContextPath()+"/medical");}
 }
 private boolean petExists(int petId)throws Exception{java.sql.Connection c=com.petcare.util.DBConnection.getConnection();try(java.sql.PreparedStatement p=c.prepareStatement("SELECT pet_id FROM pets WHERE pet_id=?")){p.setInt(1,petId);try(java.sql.ResultSet r=p.executeQuery()){return r.next();}}finally{c.close();}}
 private int ownerOfPet(HttpServletRequest req,int petId){return WebUtil.userId(req);}
}
