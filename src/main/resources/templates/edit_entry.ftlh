<#import "/parts/base.ftlh" as base>

<@base.content title="Edit entry - Learning Log">

	<p><a href="/topic/${topic.id}/entries">${topic.title}</a></p>
	
	<p>Edit entry:</p>
	
	<form action="/topic/${topic.id}/entries/edit_entry/${entry.id}"
			method="POST" enctype="multipart/form-data">
			
		<input type="hidden" name="_csrf" value="${_csrf.token}" />
		<textarea name="text" rows="10" cols="80">${entry.text}</textarea><br />
		<#if entry.filename??>
			<img src="/img/${entry.filename}" /><br />
			<label><input type="checkbox" name="onDelete" /> Delete the file</label>
			<br />or<br />
			change the file to another:<br />
		</#if>
		<input type="file" name="file" />
		<button name="submit">Save changes</button>
		
	</form>

</@base.content>