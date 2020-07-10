<#import "/parts/base.ftlh" as base>

<@base.content title="Registration - Learning Log">
    <body>
        <form action="/registration" method="post">
            <div><label>User Name : <input type="text" name="username"/></label></div>
            <div><label>Password: <input type="password" name="password"/></label></div>
            <div><label>Password confirmation: <input type="password" name="repeat_password"/></label></div>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <div><input type="submit" value="Sign Up"/></div>
        </form>
        
        <#if message??>
        	<p>${message}</p> <!-- add the template checking if the password matches. -->
        </#if>
    </body>
</@base.content>