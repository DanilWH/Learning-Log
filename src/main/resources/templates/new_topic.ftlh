<#import "/parts/base.ftlh" as base>

<@base.content title="Learning Log">

	<p>Add a new topic:</p>
	
	<form action="/new_topic" method="post">
		<input type="hidden" name="_csrf" value="${_csrf.token}" />
		
		Title <br />
		<input type="text" name="title" required /><br />
		<#if message??>
			${message} <br />
		</#if>
		
		Description <br />
		<textarea name="description" cols="80" rows="10"></textarea><br />
		
		Accessibility:
		<#list accesses as access>
			<input id="${access}" type="radio" name="access" value="${access}" ${access?contains("PRIVATE")?string("checked", "")}/>
			<label for="${access}">${access?capitalize}</label>
		</#list>
		<br />
		
		<button type="submit">Add topic</button>
	</form>

</@base.content>