package com.petcare.servlet;
import com.petcare.dao.*;import com.petcare.model.Pet;import com.petcare.util.WebUtil;
import java.io.IOException;import java.sql.Date;import java.sql.Time;import java.util.List;import javax.servlet.*;import javax.servlet.http.*;
public class AppointmentServlet extends HttpServlet {
 protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
  WebUtil.setUtf8(req,res);try{int owner=WebUtil.userId(req);req.setAttribute("appointments",new AppointmentDAO().listForOwner(owner));req.setAttribute("pets",new PetDAO().listByOwner(owner));req.setAttribute("services",new ServiceDAO().listActive());req.getRequestDispatcher("/WEB-INF/views/appointments.jsp").forward(req,res);}catch(Exception e){getServletContext().log("Appointment list error",e);WebUtil.forwardError(req,res,"Appointments could not be loaded.");}
 }
 protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
  WebUtil.setUtf8(req,res);int owner=WebUtil.userId(req);String action=WebUtil.clean(req.getParameter("action"));
  try{
   AppointmentDAO dao=new AppointmentDAO();
   if("cancel".equals(action)){boolean ok=dao.cancel(WebUtil.intValue(req.getParameter("id"),-1),owner);WebUtil.setMessage(req,ok?"success":"error",ok?"Appointment cancelled.":"Appointment could not be cancelled.");res.sendRedirect(req.getContextPath()+"/appointments");return;}
   int petId=WebUtil.intValue(req.getParameter("petId"),-1),serviceId=WebUtil.intValue(req.getParameter("serviceId"),-1);Date date=WebUtil.sqlDate(req.getParameter("appointmentDate"));Time time=WebUtil.sqlTime(req.getParameter("appointmentTime"));String notes=WebUtil.clean(req.getParameter("notes"));
   Pet pet=new PetDAO().findById(petId,owner);
   if(pet==null||serviceId<1||!WebUtil.isFutureOrToday(date)||time==null){WebUtil.setMessage(req,"error","Choose one of your pets, a service, a valid time, and today or a future date.");}
   else if(dao.hasConflict(petId,date,time)){WebUtil.setMessage(req,"error","That appointment date and time is already booked. Choose another slot.");}
   else if(dao.book(petId,serviceId,date,time,notes)){WebUtil.setMessage(req,"success","Appointment booked.");}
   else{WebUtil.setMessage(req,"error","Appointment could not be booked.");}
   res.sendRedirect(req.getContextPath()+"/appointments");
  }catch(Exception e){getServletContext().log("Appointment save error",e);WebUtil.setMessage(req,"error","Appointment could not be saved. Check the selected values.");res.sendRedirect(req.getContextPath()+"/appointments");}
 }
}
