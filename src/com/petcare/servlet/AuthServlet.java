package com.petcare.servlet;
import com.petcare.dao.UserDAO; import com.petcare.dao.UserDAO.UserAuth; import com.petcare.util.PasswordUtil; import com.petcare.util.WebUtil;
import java.io.IOException; import java.sql.SQLException; import javax.servlet.*; import javax.servlet.http.*;

public class AuthServlet extends HttpServlet {
 private UserDAO dao=new UserDAO();
 protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
  WebUtil.setUtf8(req,res);String action=req.getServletPath();
  if("/logout".equals(action)){HttpSession s=req.getSession(false);if(s!=null)s.invalidate();res.sendRedirect(req.getContextPath()+"/login?logout=1");return;}
  req.getRequestDispatcher("/WEB-INF/views/"+("/register".equals(action)?"register.jsp":"login.jsp")).forward(req,res);
 }
 protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
  WebUtil.setUtf8(req,res);String action=req.getServletPath();
  try{
   if("/register".equals(action)){
    String name=WebUtil.clean(req.getParameter("fullName")),email=WebUtil.clean(req.getParameter("email")).toLowerCase(),phone=WebUtil.clean(req.getParameter("phone")),password=req.getParameter("password");
    if(name.length()<2||email.length()<5||email.indexOf("@")<1||password==null||password.length()<8){req.setAttribute("errorMessage","Enter a valid name and email. Password must contain at least 8 characters.");req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req,res);return;}
    if(dao.emailExists(email)){req.setAttribute("errorMessage","An account with this email already exists.");req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req,res);return;}
    String salt=PasswordUtil.generateSalt(),hash=PasswordUtil.hashPassword(password,salt);int id=dao.register(name,email,phone,hash,salt);
    HttpSession session=req.getSession(true);session.setAttribute("userId",id);session.setAttribute("fullName",name);session.setAttribute("role","OWNER");
    WebUtil.setMessage(req,"success","Account created successfully.");res.sendRedirect(req.getContextPath()+"/dashboard");return;
   }
   String email=WebUtil.clean(req.getParameter("email")).toLowerCase(),password=req.getParameter("password");
   UserAuth user=dao.authenticate(email);
   if(user!=null&&password!=null&&PasswordUtil.verify(password,user.salt,user.hash)){
    HttpSession old=req.getSession(false);if(old!=null)old.invalidate();HttpSession s=req.getSession(true);s.setAttribute("userId",user.id);s.setAttribute("fullName",user.name);s.setAttribute("role",user.role);s.setMaxInactiveInterval(30*60);
    res.sendRedirect(req.getContextPath()+"/dashboard");return;
   }
   req.setAttribute("errorMessage","Invalid email or password.");req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,res);
  }catch(SQLException e){getServletContext().log("Authentication/database error",e);req.setAttribute("errorMessage","Unable to complete the request. Check the database connection and try again.");req.getRequestDispatcher("/WEB-INF/views/"+("/register".equals(action)?"register.jsp":"login.jsp")).forward(req,res);}
 }
}
