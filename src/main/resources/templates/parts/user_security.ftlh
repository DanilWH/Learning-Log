<#assign
	know = Session.SPRING_SECURITY_CONTEXT??
/> 

<#if know>
	<#assign
		user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
		currentUserId = user.getId()
	/>
<#else>
	<#assign
		currentUserId = -1
	/>
</#if>