<#import "/parts/base.ftlh" as base />
<#import "/parts/common.ftlh" as common />

<@base.content title="Delete entry - Learning Log">

	<h5>Delete the entry</h5>
	
	<p>
		Are you sure you want to delete the entry of the
		topic<a href="/topic/${topic.id}/entries"> ${topic.title} </a>from ${entry.dateTimeFormatted}?
	</p>
	
	<@common.delete_confirm
		url_yes_case="/topic/${topic.id}/entries/delete_entry/${entry.id}"
		url_no_case="/topic/${topic.id}/entries" />

</@base.content>