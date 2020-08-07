<#import "/parts/base.ftlh" as base />
<#import "/parts/common.ftlh" as common />

<@base.content title="Edit topic - Learning Log">

	<h5 class="mb-4">Edit the existing topic:</h5>
	
	<@common.topic_form url="/edit_topic/${topic.id}" checked_access="${topic.access}" />

</@base.content>