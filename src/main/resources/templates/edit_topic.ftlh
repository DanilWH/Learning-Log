<#import "/parts/base.ftlh" as base />
<#import "/parts/common.ftlh" as common />

<@base.content title="Edit topic - Learning Log">

	<p>Edit the existing topic:</p>
	
	<@common.topic_form url="/edit_topic/${topic.id}" checked_access="${topic.access}" />

</@base.content>