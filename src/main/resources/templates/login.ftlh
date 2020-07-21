<#import "/parts/base.ftlh" as base>

<@base.content title="Log In - Learning Log">

    <form action="/login" method="post">
    	<div><label> User Name : <input type="text" name="username" required/> </label></div>
    	<div><label> Password: <input type="password" name="password" required/> </label></div>
    	<input type="hidden" name="_csrf" value="${_csrf.token}" />
    	<div><input type="submit" value="Sign In"/></div>
	</form>

    <p>Don't have an account? <a href="/registration">Sign Up</a>.</p>
    
</@base.content>
