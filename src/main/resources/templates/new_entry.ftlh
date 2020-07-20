<#import "/parts/base.ftlh" as base>


<@base.content title="New entry - Learning Log">

	<p><a href="/topic/${topic.id}/entries">${topic.title}</a></p>
	<p>Add a new entry:</p>
	
	<form action="/topic/${topic.id}/entries/new_entry" method="POST">
		<input type="hidden" name="_csrf" value="${_csrf.token}" />
		<textarea name="text" cols="80" rows="10" required="required"></textarea><br />
		<#if message??>
			${message} <br />
		</#if>
		<button type="submit">Add entry</button>
	</form>
	
</@base.content>