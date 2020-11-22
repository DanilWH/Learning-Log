<#import "/parts/base.ftlh" as base>
<#include "/parts/user_security.ftlh" />

<@base.content title="Learning Log">

	<h3>${topic.title}</h3>
	
	<#assign des_lines = topic.description?split("\n") />
	<#list des_lines as des_line>
		${des_line} <br />
	</#list>
	
	<#if topic.owner.username == user.getUsername()>
		<p>
			<div class="btn-group" role="group" aria-label="Basic example">
				<a href="/edit_topic/${topic.id}" class="btn btn-secondary btn-sm">Edit topic</a>
				<a href="/delete_topic/${topic.id}" class="btn btn-danger btn-sm">Delete topic</a>
			</div>
		</p>
	</#if>
	
	<#if topic.owner.username == user.getUsername()>
		<a href="/topic/${topic.id}/entries/new_entry" class="btn btn-primary">Add a new entry</a>
	</#if>
	
		<#list entries as entry>
			<div class="card my-3">
				<h5 class="card-header">${entry.dateTime}</h5>
				<div class="card-body">
					<p class="card-text">
						<#assign lines = entry.text?split("\n") />
						<#list lines as line>
						   ${line} <br />
						</#list>
					</p>
					
					<#if topic.owner.username == user.getUsername()>
						<div class="btn-group" role="group" aria-label="Basic example">
							<a href="/topic/${topic.id}/entries/edit_entry/${entry.id}" class="btn btn-secondary btn-sm">Edit entry</a>
							<a href="/topic/${topic.id}/entries/delete_entry/${entry.id}" class="btn btn-danger btn-sm">Delete entry</a>
						</div>
					</#if>
				</div>
				<#list entry.uploads as upload>
					<img src="/topic/images?id=${upload.id}" class="card-img-bottom px-3 pb-3" />
				</#list>
			</div>
		<#else>
			<div class="card my-3">
				<h5 class="card-header">...There are no entries for this topic yet...</h5>
			</div>
		</#list>
	
</@base.content>