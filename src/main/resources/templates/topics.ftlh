<#import "/parts/base.ftlh" as base>

<@base.content title="Topics - Learning Log">
    
	<p>Topics:</p>
	
	<ul>
		<#list topics as topic>
			<li>
				<a href="/topics/${topic.id}/entries">${topic.title}</a>
			</li>
		<#else>
			<li>...No topics have been added yet...</li>
	    </#list>
    </ul>
    
    <a href="/new_topic">Add a new topic</a>
    
</@base.content>