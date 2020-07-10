<#import "/parts/base.ftlh" as base>

<@base.content title="New entry - Learning Log">

	<p><a href="/topics/${topic.id}/entries">${topic.title}</a></p>
	<p>Add a new entry:</p>
	
	<form action="/topics/${topic.id}/entries/new_entry" method="post">
		<input type="hidden" name="_csrf" value="${_csrf.token}" />
		<textarea name="text" cols="80" rows="10"></textarea><br />
		<button type="submit">Add entry</button>
	</form>

</@base.content>