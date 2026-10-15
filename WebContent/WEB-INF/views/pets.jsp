<%@ page import="java.util.*,com.petcare.model.Pet" %><%@ include file="partials/header.jspf" %>
<div class="page-heading"><div><div class="eyebrow">PET PROFILES</div><h1>My pets</h1><p class="muted">Keep each pet's details up to date.</p></div></div>
<div class="content-grid">
<section class="panel"><h2><%= request.getAttribute("pet") == null ? "Add a pet" : "Edit pet" %></h2>
<% Pet editing=(Pet)request.getAttribute("pet"); %>
<form method="post" action="<%=ctx%>/pets" class="form-stack">
<input type="hidden" name="petId" value="<%=editing==null?0:editing.getPetId()%>">
<label>Pet name *<input name="petName" required maxlength="80" value="<%=editing==null?"":editing.getPetName()%>"></label>
<label>Species *<select name="species" required><% String sp=editing==null?"":editing.getSpecies();String[] species={"Dog","Cat","Bird","Rabbit","Fish","Other"};for(String s:species){%><option value="<%=s%>" <%=s.equalsIgnoreCase(sp)?"selected":""%>><%=s%></option><%}%></select></label>
<label>Breed<input name="breed" maxlength="80" value="<%=editing==null?"":editing.getBreed()%>"></label>
<label>Age (years) *<input type="number" name="ageYears" min="0" max="100" step="0.1" required value="<%=editing==null?"0":editing.getAgeYears()%>"></label>
<label>Gender<select name="gender"><%String g=editing==null?"UNKNOWN":editing.getGender();String[] genders={"UNKNOWN","MALE","FEMALE"};for(String v:genders){%><option value="<%=v%>" <%=v.equals(g)?"selected":""%>><%=v%></option><%}%></select></label>
<label>Notes<textarea name="notes" maxlength="500" rows="3"><%=editing==null?"":editing.getNotes()==null?"":editing.getNotes()%></textarea></label>
<button class="btn btn-primary" type="submit">Save pet</button><%if(editing!=null){%><a class="text-link" href="<%=ctx%>/pets">Cancel edit</a><%}%>
</form></section>
<section class="panel panel-wide"><div class="section-heading"><div><h2>Your pet profiles</h2><p class="muted">Only your own pet records are shown.</p></div></div>
<div class="table-wrap"><table><thead><tr><th>Pet</th><th>Species / breed</th><th>Age</th><th>Gender</th><th>Actions</th></tr></thead><tbody>
<%List<Pet> pets=(List<Pet>)request.getAttribute("pets");if(pets.isEmpty()){%><tr><td colspan="5" class="empty-state">No pets added yet.</td></tr><%}else{for(Pet p:pets){%><tr><td><strong><%=p.getPetName()%></strong><div class="subtext"><%=p.getNotes()==null?"":p.getNotes()%></div></td><td><%=p.getSpecies()%><div class="subtext"><%=p.getBreed()==null?"":p.getBreed()%></div></td><td><%=p.getAgeYears()%></td><td><%=p.getGender()%></td><td class="actions"><a href="<%=ctx%>/pets?action=edit&id=<%=p.getPetId()%>">Edit</a><a class="danger-link" href="<%=ctx%>/pets?action=delete&id=<%=p.getPetId()%>" onclick="return confirm('Delete this pet? Related records may prevent deletion.')">Delete</a></td></tr><%}}%>
</tbody></table></div></section></div>
<%@ include file="partials/footer.jspf" %>
