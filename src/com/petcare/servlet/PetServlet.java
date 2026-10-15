package com.petcare.servlet;
import com.petcare.dao.PetDAO; import com.petcare.model.Pet; import com.petcare.util.WebUtil;
import java.io.IOException; import javax.servlet.*; import javax.servlet.http.*;

public class PetServlet extends HttpServlet {
 private PetDAO dao=new PetDAO();
 protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
  WebUtil.setUtf8(req,res);int owner=WebUtil.userId(req);String action=WebUtil.clean(req.getParameter("action"));
  try{
   if("delete".equals(action)){int id=WebUtil.intValue(req.getParameter("id"),-1);if(id>0&&dao.delete(id,owner))WebUtil.setMessage(req,"success","Pet deleted.");else WebUtil.setMessage(req,"error","Pet could not be deleted. Related records may exist.");res.sendRedirect(req.getContextPath()+"/pets");return;}
   if("edit".equals(action)){Pet pet=dao.findById(WebUtil.intValue(req.getParameter("id"),-1),owner);if(pet==null){WebUtil.forwardError(req,res,"Pet not found or you do not have permission to view it.");return;}req.setAttribute("pet",pet);}
   req.setAttribute("pets",dao.listByOwner(owner));req.getRequestDispatcher("/WEB-INF/views/pets.jsp").forward(req,res);
  }catch(Exception e){getServletContext().log("Pet list error",e);WebUtil.forwardError(req,res,"Pet information could not be loaded.");}
 }
 protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
  WebUtil.setUtf8(req,res);int owner=WebUtil.userId(req);
  String name=WebUtil.clean(req.getParameter("petName")),species=WebUtil.clean(req.getParameter("species")),breed=WebUtil.clean(req.getParameter("breed")),gender=WebUtil.clean(req.getParameter("gender")),notes=WebUtil.clean(req.getParameter("notes"));
  double age;try{age=Double.parseDouble(WebUtil.clean(req.getParameter("ageYears")));}catch(Exception e){age=-1;}
  int id=WebUtil.intValue(req.getParameter("petId"),0);
  if(name.isEmpty()||species.isEmpty()||age<0||age>100||!(gender.equals("MALE")||gender.equals("FEMALE")||gender.equals("UNKNOWN"))){req.setAttribute("errorMessage","Enter a pet name, species, valid age (0–100), and gender.");try{req.setAttribute("pets",dao.listByOwner(owner));}catch(Exception ignored){}req.getRequestDispatcher("/WEB-INF/views/pets.jsp").forward(req,res);return;}
  Pet p=new Pet();p.setOwnerId(owner);p.setPetName(name);p.setSpecies(species);p.setBreed(breed);p.setGender(gender);p.setAgeYears(age);p.setNotes(notes);
  try{boolean ok;if(id>0){p.setPetId(id);ok=dao.update(p);}else{ok=dao.save(p);}WebUtil.setMessage(req,ok?"success":"error",ok?"Pet information saved.":"No changes were made.");res.sendRedirect(req.getContextPath()+"/pets");}
  catch(Exception e){getServletContext().log("Pet save error",e);WebUtil.forwardError(req,res,"Pet could not be saved. Check the entered information.");}
 }
}
