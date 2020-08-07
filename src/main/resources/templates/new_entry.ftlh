<#import "/parts/base.ftlh" as base>


<@base.content title="New entry - Learning Log">

	<h5 class="mb-3">Add a new entry for "<a href="/topic/${topic.id}/entries">${topic.title}</a>":</h5>
	
	<form action="/topic/${topic.id}/entries/new_entry" method="POST" enctype="multipart/form-data">
		<input type="hidden" name="_csrf" value="${_csrf.token}" />
		
		<div class="form-group">
			<textarea name="text" cols="80" rows="10" required class="form-control"
				placeholder="Type what you've learned today"></textarea>
			<small class="form-text text-danger">${message?ifExists}</small>
		</div>
		<div class="form-group">
			<div class="custom-file">
				<input id="files" type="file" name="files" multiple class="custom-file-input" />
				<label for="files" class="custom-file-label">Choose files</label>
			</div>
		</div>
		
		<button type="submit" class="btn btn-primary">Add entry</button>
	</form>
	
</@base.content>