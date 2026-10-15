<%@ page import="java.util.*,com.petcare.model.Pet,com.petcare.model.Service,com.petcare.model.Appointment" %>
<%@ include file="partials/header.jspf" %>
<div class="page-heading"><div><div class="eyebrow">YOUR WORKSPACE</div><h1>Good to see you, <%= session.getAttribute("fullName") %>.</h1><p class="muted">Here's an overview of your pet care information.</p></div><a class="btn btn-primary" href="<%=ctx%>/pets">+ Add a pet</a></div>
<div class="stat-grid">
<div class="stat-card"><span class="stat-icon blue">♥</span><div><span class="stat-label">My pets</span><strong><%= ((List)request.getAttribute("pets")).size() %></strong></div></div>
<div class="stat-card"><span class="stat-icon mint">◷</span><div><span class="stat-label">Appointments</span><strong><%= ((List)request.getAttribute("appointments")).size() %></strong></div></div>
<div class="stat-card"><span class="stat-icon peach">✚</span><div><span class="stat-label">Services available</span><strong><%= ((List)request.getAttribute("services")).size() %></strong></div></div>
</div>
<div class="section-heading"><div><h2>Quick actions</h2><p class="muted">Common tasks, just a click away.</p></div></div>
<div class="action-grid"><a class="action-card" href="<%=ctx%>/pets"><span class="action-icon">♥</span><strong>Manage pets</strong><small>Add, edit or review pet profiles</small><span class="action-arrow">→</span></a><a class="action-card" href="<%=ctx%>/appointments"><span class="action-icon">◷</span><strong>Book appointment</strong><small>Choose a pet, service, date and time</small><span class="action-arrow">→</span></a><a class="action-card" href="<%=ctx%>/medical"><span class="action-icon">✚</span><strong>Medical history</strong><small>Review your pets' medical records</small><span class="action-arrow">→</span></a><a class="action-card" href="<%=ctx%>/vaccinations"><span class="action-icon">✓</span><strong>Vaccinations</strong><small>Review vaccine and due-date history</small><span class="action-arrow">→</span></a></div>
<div class="section-heading"><div><h2>Upcoming & recent appointments</h2><p class="muted">Your appointment activity.</p></div><a class="text-link" href="<%=ctx%>/appointments">View all →</a></div>
<div class="table-wrap"><table><thead><tr><th>Pet</th><th>Service</th><th>Date</th><th>Time</th><th>Status</th></tr></thead><tbody>
<% List<Appointment> appts=(List<Appointment>)request.getAttribute("appointments"); if(appts.isEmpty()){ %><tr><td colspan="5" class="empty-state">No appointments yet. Book your first appointment when you're ready.</td></tr><% } else { for(int i=0;i<appts.size()&&i<5;i++){Appointment a=appts.get(i);%><tr><td><strong><%=a.getPetName()%></strong></td><td><%=a.getServiceName()%></td><td><%=a.getAppointmentDate()%></td><td><%=a.getAppointmentTime()%></td><td><span class="status <%=a.getStatus().toLowerCase()%>"><%=a.getStatus()%></span></td></tr><% }} %>
</tbody></table></div>
<%@ include file="partials/footer.jspf" %>
