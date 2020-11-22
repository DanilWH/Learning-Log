<#import "/parts/base.ftlh" as base>

<@base.content title="Edit entry - Learning Log">

	<h5 class="mb-4">Edit entry:</h5>
	
	<form action="/topic/${topic.id}/entries/edit_entry/${entry.id}"
			method="POST" enctype="multipart/form-data">
		<input type="hidden" name="_csrf" value="${_csrf.token}" />
		
		<div class="form group">
			<textarea name="text" rows="5" class="form-control">${entry.text}</textarea><br />
		</div>
		<#if entry.uploads??>
			<div class="card-columns">
				<#list entry.uploads as upload>
					<div class="card">
						<img src="/topic/images?id=${upload.id}" class="rounded card-img-top"  /><br />
						<div class="card-footer">
							<div class="custom-control custom-checkbox">
								<input id="${upload.filename}" type="checkbox" name="onDelete"
									   value="${upload.filename}" class="custom-control-input" />
								<label for="${upload.filename}" class="custom-control-label">Delete ${upload.filename}</label>
							</div>
						</div>
					</div>
				</#list>
			</div>
		</#if>
		
		<div class="form-group">
			<div class="custom-file">
				<input id="files" type="file" name="files" multiple class="custom-file-input" />
				<label for="files" class="custom-file-label">Choose files</label>
			</div>
		</div>
		
		<div><button name="submit" class="btn btn-primary">Save changes</button></div>
		
	</form>

</@base.content>