<#macro showList topics showOwner=true>

<div class="list-group">
    <#list topics as topic>
    <a href="/topic/${topic.id}/entries"
       class="list-group-item list-group-item-action">
        <div class="d-flex w-100 justify-content-between">
            <h5 class="mb-1">${topic.title}<span class="badge badge-primary ml-3">${topic.getEntriesNumber()} entries</span></h5>
            <small class="text-muted">${topic.getTimeCreationAgo()}</small>
        </div>
        <p class="mb-1">
            <#assign des_lines = topic.description?split("\n") />
            <#list des_lines as des_line>
                ${des_line} <br />
            </#list>
            </p>
            <!--
                we show the username of a particular topic if the current
                user is watching public topics.
             -->
            <#if showOwner>
                <small class="text-muted"><a href="/user_topics/${topic.owner.id}">u/${topic.owner.username}</a></small>
            </#if>
    </a>
    <#else>
        ...No topics have been added yet...
    </#list>
</div>

</#macro>