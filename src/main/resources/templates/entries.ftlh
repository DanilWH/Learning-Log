<#import "/parts/base.ftlh" as base>

<@base.content title="Learning Log">

	<h3>${topic.title}</h3>
	
	<#assign des_lines = topic.description?split("\n") />
	<#list des_lines as des_line>
		${des_line} <br />
	<#else>
		...No description...
	</#list>
	
	<p>Entries:</p>
	
	<#if topic.owner.username == __user.name>
		<a href="/topic/${topic.id}/entries/new_entry">Add a new entry</a>
	</#if>
	
	<ul>
		<#list entries as entry>
			<li>
				<i>${entry.dateTime}</i>
				
				<p>
					<#assign lines = entry.text?split("\n") />
					<#list lines as line>
					   ${line} <br />
					</#list>
				</p>
				
				<#if entry.filenames??>
					<#list entry.filenames as filename>
						<img src="/img/${filename}" />
					</#list>
				</#if>
				
				<#if topic.owner.username == __user.name>
					<p>
						<a href="/topic/${topic.id}/entries/edit_entry/${entry.id}">Edit entry</a> |
						<a href="/topic/${topic.id}/entries/delete_entry/${entry.id}">Delete entry</a>
					</p>
				</#if>
				
				<hr />
			</li>
		<#else>
			<li>...There are no entries for this topic yet...</li>
		</#list>
	</ul>
	
</@base.content>