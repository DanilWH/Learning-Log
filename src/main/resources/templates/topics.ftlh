<#import "/parts/base.ftlh" as base>

<@base.content title="Topics - Learning Log">
    
	<p>Topics:</p>
	
	<ul>
		<#list topics as topic>
			<li>
				<!--
					we show the username of a particular topic if the current
					user is watching public topics.
				 -->
				<#if springMacroRequestContext.getRequestUri() == "/public_topics">
					<i>u/${topic.owner.username}</i><br />
				</#if>
				<b><a href="/topic/${topic.id}/entries">${topic.title}</a></b>
			</li>
		<#else>
			<li>...No topics have been added yet...</li>
	    </#list>
    </ul>
    
</@base.content>