<#import "/parts/base.ftlh" as base />
<#import "/parts/common.ftlh" as common />

<@base.content title="Delete topic - Learning Log">

	<p>Delete the topic</p>
	
	<p>
		Are you sure you want to delete the topic
		<a href="/topic/${topic.id}/entries"> ${topic.title}</a>?<br />
		(It will remove all the data that is associated with the topic such as
		all entries and files.)
	</p>
	
	<@common.delete_confirm
		url_yes_case="/delete_topic/${topic.id}"
		url_no_case="/topic/${topic.id}/entries" />

</@base.content>