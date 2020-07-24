<#macro content title>

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<!DOCTYPE html>
<html>
	<head>
		<title>${title}</title>
	</head>
	<body>
		<p id="top">
			<a href="/">Learning Log</a> - 
			<a href="/public_topics">Public topics</a> -
			<a href="/my_topics">My topics</a> -
			
			<#if __user??>
			    Hello, ${__user.name}.
			    <@security.authorize  access="hasAuthority('ADMIN')">
			    	- <a href="/admin/users">Users</a>
				</@security.authorize>
			    <form name="logoutForm" action="/logout" method="post">
					<input type="hidden" name="_csrf" value="${_csrf.token}" />
				    <input type="submit" value="Log out"/>
				</form>
			<#else>
				<a href="/registration">Register</a> -
			    <a href="/login">Log in</a> -
			</#if>
    
		    <a href="/new_topic">Add a new topic</a>
		</p>
		<hr />
		
		<#nested>
		
		<p><a href="#top">Up</a></p>
		
	</body>
</html>

</#macro>