<#macro topic_form url checked_access>

<form action="${url}" method="POST">
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
	
	<div class="form-group">
		<label for="title">Title</label>
		<input id="title" type="text" name="title" value="${(topic.title)?ifExists}" required
			class="form-control" placeholder="Title" />
		<small id="message" class="form-text text-muted">${message?ifExists}</small>
	</div>
	
	<div class="form-group">
		<label for="description">Description</label>
		<textarea id="description" name="description"
				  rows="3" class="form-control"
				  placeholder="Description">${(topic.description)?ifExists}</textarea>
	</div>
	
	<div class="form-group">
		Accessibility:
		<#list accesses as access>
			<div class="custom-control custom-radio custom-control-inline">
				<input id="${access}" type="radio" name="access" value="${access}"
					   ${access?contains(checked_access)?string("checked", "")}
					   class="custom-control-input"/>
				<label for="${access}" class="custom-control-label">${access?capitalize}</label>
			</div>
		</#list>
	</div>
	
	<button type="submit" class="btn btn-primary">Save</button>
</form>

</#macro>

<#macro delete_confirm url_yes_case url_no_case>

<div class="form-inline">
	<form action="${url_yes_case}" method="post">
		<input type="hidden" name="_csrf" value="${_csrf.token}" />
		<button name="submit" class="btn btn-success mx-1">Yes</button>
	</form>
	
	<form action="${url_no_case}" method="get">
		<button name="submit" class="btn btn-danger mx-1">No</button>
	</form>
</div>

</#macro>