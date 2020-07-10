<#import "/parts/base.ftlh" as base>

<@base.content title="Learning Log">

	<p>Topic: ${topic.title}</p>
	
	<p>Entries:</p>
	<a href="/topics/${topic.id}/entries/new_entry">Add a new entry</a>
	<ul>
		<#list entries as entry>
			<li>
				<i>${entry.dateTime}</i>
				<p>${entry.text}</p>
				<hr />
			</li>
		<#else>
			<li>...There are no entries for this topic yet...</li>
		</#list>
	</ul>
	
</@base.content>