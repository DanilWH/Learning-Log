<#import "/parts/base.ftlh" as base>

<@base.content title="Learning Log">

	<p>Add a new topic:</p>
	
	<form action="/new_topic" method="post">
		<input type="hidden" name="_csrf" value="${_csrf.token}" />
		<input type="text" name="title" /><br />
		<button type="submit">Add topic</button>
	</form>

</@base.content>