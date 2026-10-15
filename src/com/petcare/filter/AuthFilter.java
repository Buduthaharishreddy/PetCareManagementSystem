package com.petcare.filter;
import java.io.IOException;
import javax.servlet.*; import javax.servlet.http.*;

public class AuthFilter implements Filter {
 public void init(FilterConfig c) throws ServletException {}
 public void destroy() {}
 public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
  HttpServletRequest request=(HttpServletRequest)req; HttpServletResponse response=(HttpServletResponse)res;
  String path=request.getRequestURI().substring(request.getContextPath().length());
  if(path.startsWith("/assets/") || path.equals("/login") || path.equals("/register") || path.equals("/")) {chain.doFilter(req,res);return;}
  HttpSession session=request.getSession(false);
  if(session==null || session.getAttribute("userId")==null){response.sendRedirect(request.getContextPath()+"/login");return;}
  chain.doFilter(req,res);
 }
}
