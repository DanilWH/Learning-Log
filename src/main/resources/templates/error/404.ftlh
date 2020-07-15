<#import "../parts/base.ftlh" as base>

<@base.content title="404 Error - Learning Log">
	<h1>404 - Not Found</h1>
	<p>
		The requested URL ${springMacroRequestContext.getRequestUri()}
		search was not found in this server.
	</p>
</@base.content>