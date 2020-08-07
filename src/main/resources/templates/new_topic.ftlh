<#import "/parts/base.ftlh" as base />
<#import "/parts/common.ftlh" as common />

<@base.content title="Learning Log">

	<h5 class="mb-3">Add a new topic:</h5>
	
	<@common.topic_form url="/new_topic" checked_access="PRIVATE" />

</@base.content>