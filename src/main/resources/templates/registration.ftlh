<#import "/parts/base.ftlh" as base>

<@base.content title="Registration - Learning Log">
    <body>
        <form action="/registration" method="post">
            <div><label>User Name : <input type="text" name="username" required maxlength="150"/></label></div>
            <#if username_message??>
            	<p>${username_message}</p>
            </#if>
            Required. 150 characters or fewer. Letters, digits and @/./+/-/_ only.
            <div><label>Password: <input type="password" name="password" required/></label></div>
            <ul>
            	<li>Your password can't be too similar to your other personal information.</li>
            	<li>Your password must contain at least 8 characters.</li>
            	<li>Your password can't be a commonly used password.</li>
            	<li>Your password can't be entirely numeric.</li>
            </ul>
            <div><label>Password confirmation: <input type="password" name="password_confirm" required/></label></div>
            <#if password_message??>
	        	<p>${password_message}</p>
	        </#if>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <div><input type="submit" value="Sign Up"/></div>
        </form>
    </body>
    
</@base.content>