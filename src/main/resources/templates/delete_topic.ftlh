<#import "/parts/base.ftlh" as base>

<@base.content title="Delete topic - Learning Log">

	<p>Delete the topic</p>
	
	<p>
		Are you sure you want to delete the topic
		<a href="/topic/${topic.id}/entries"> ${topic.title}</a>?<br />
		(It will remove all the data that is associated with the topic such as
		all entries and files.)
	</p>
	
	<form action="/delete_topic/${topic.id}" method="post">
		<input type="hidden" name="_csrf" value="${_csrf.token}" />
		<button name="submit">Yes</button>
	</form>
	
	<form action="/topic/${topic.id}/entries" method="get">
		<button name="submit">No</button>
	</form>

</@base.content>