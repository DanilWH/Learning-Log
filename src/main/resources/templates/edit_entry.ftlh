<#import "/parts/base.ftlh" as base>

<@base.content title="Edit entry - Learning Log">

	<p><a href="/topic/${topic.id}/entries">${topic.title}</a></p>
	
	<p>Edit entry:</p>
	
	<form action="/topic/${topic.id}/entries/edit_entry/${entry.id}"
			method="POST" enctype="multipart/form-data">
			
		<input type="hidden" name="_csrf" value="${_csrf.token}" />
		<textarea name="text" rows="10" cols="80">${entry.text}</textarea><br />
		<#if entry.filenames??>
			<#list entry.filenames as filename>
				<div>
					<img src="/img/${filename}" style="width: 150px; height: auto;" /><br />
					<label>
						<input type="checkbox" name="onDelete" value="${filename}" />
						Delete ${filename}
					</label>
				</div>
			</#list>
		</#if>
		<input type="file" name="files" multiple />
		<div><button name="submit">Save changes</button></div>
		
	</form>

</@base.content>