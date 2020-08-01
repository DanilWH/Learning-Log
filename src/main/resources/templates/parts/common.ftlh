<#macro topic_form url checked_access>

<form action="${url}" method="POST">
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
	
	Title <br />
	<input type="text" name="title" value="${(topic.title)?ifExists}" required /><br />
	${message?ifExists} <br />
	
	Description <br />
	<textarea name="description" cols="80" rows="10">${(topic.description)?ifExists}</textarea><br />
	
	Accessibility:
	<#list accesses as access>
		<input id="${access}" type="radio" name="access" value="${access}" ${access?contains(checked_access)?string("checked", "")}/>
		<label for="${access}">${access?capitalize}</label>
	</#list>
	<br />
	
	<button type="submit">Save</button>
</form>

</#macro>

<#macro delete_confirm url_yes_case url_no_case>

<form action="${url_yes_case}" method="post">
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
	<button name="submit">Yes</button>
</form>

<form action="${url_no_case}" method="get">
	<button name="submit">No</button>
</form>

</#macro>