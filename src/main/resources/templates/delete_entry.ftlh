<#import "/parts/base.ftlh" as base>

<@base.content title="Delete entry - Learning Log">

	<p>Delete the entry</p>
	
	<p>
		Are you sure you want to delete the entry of the
		topic<a href="/topic/${topic.id}/entries"> ${topic.title} </a>from ${entry.dateTime}?
	</p>
	
	<form action="/topic/${topic.id}/entries/delete_entry/${entry.id}" method="post">
		<input type="hidden" name="_csrf" value="${_csrf.token}" />
		<button name="submit">Yes</button>
	</form>
	
	<form action="/topic/${topic.id}/entries" method="get">
		<button name="submit">No</button>
	</form>

</@base.content>