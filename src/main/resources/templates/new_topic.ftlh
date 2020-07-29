<#import "/parts/base.ftlh" as base />
<#import "/parts/common.ftlh" as common />

<@base.content title="Learning Log">

	<p>Add a new topic:</p>
	
	<@common.topic_form url="/new_topic" checked_access="PRIVATE" />

</@base.content>