<#import "/parts/base.ftlh" as base>

<@base.content title="Learning Log">

	<p>Add a new topic:</p>
	
	<form action="/new_topic" method="post">
		<input type="hidden" name="_csrf" value="${_csrf.token}" />
		<input type="text" name="title" /><br />
		
		Accessibility:
		<input id="private" type="radio" name="access" value="${PRIVATE}" checked="checked" />
		<label for="private">Private</label>
		<input id="public" type="radio" name="access" value="${PUBLIC}" />
		<label for="public">Public</label>
		<br />
		<button type="submit">Add topic</button>
	</form>

</@base.content>