<%@ include file="partials/header.jspf" %>
<section class="auth-layout"><div class="auth-intro"><div class="eyebrow">CARE, ORGANIZED</div><h1>Your pet's care,<br><span>all in one place.</span></h1><p>Keep pet profiles, appointments, medical notes and vaccination history organized in one simple workspace.</p><div class="mini-stats"><div><strong>01</strong><span>Pet profiles</span></div><div><strong>02</strong><span>Appointments</span></div><div><strong>03</strong><span>Health records</span></div></div></div>
<div class="auth-card"><div class="eyebrow">WELCOME BACK</div><h2>Sign in</h2><p class="muted">Access your PetCare workspace.</p>
<form method="post" action="<%=ctx%>/login" class="form-stack"><label>Email address<input type="email" name="email" required maxlength="150" autocomplete="email"></label><label>Password<input type="password" name="password" required autocomplete="current-password"></label><button class="btn btn-primary" type="submit">Sign in <span>→</span></button></form>
<p class="auth-switch">New to PetCare? <a href="<%=ctx%>/register">Create an account</a></p></div></section>
<%@ include file="partials/footer.jspf" %>
