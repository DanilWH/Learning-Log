<#import "/parts/base.ftlh" as base>

<@base.content title="Topics - Learning Log">
    
	<p>Topics:</p>
	
	<ul>
		<#list topics as topic>
			<li>
				<a href="/topic/${topic.id}/entries">${topic.title}</a>
			</li>
		<#else>
			<li>...No topics have been added yet...</li>
	    </#list>
    </ul>
    
</@base.content>