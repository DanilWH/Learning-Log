<#import "/parts/base.ftlh" as base />
<#import "/parts/topicsList.ftlh" as topicsList />

<@base.content title="Topics - Learning Log">
    
	<h3 class="mb-4">Topics:</h3>
	<a href="/new_topic" class="btn btn-primary mb-1">Add a new topic</a>

	<@topicsList.showList topics />

</@base.content>