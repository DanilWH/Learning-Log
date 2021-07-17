<#import "/parts/base.ftlh" as base />
<#import "/parts/topicsList.ftlh" as tl />

<@base.content title="Topics - Learning Log">

    <h3 class="mb-4"><#if !isCurrentUser>${(user.username)?ifExists} </#if>Topics:</h3>

    <div class="row">
        <div class="col-sm-6">
            <div class="card mb-3">
                <div class="card-body">
                    <h1 class="card-title">${subscribersCount}</h1>
                    <div class="card-text">Subscribers</div>
                </div>
            </div>
        </div>

        <div class="col-sm-6">
            <div class="card mb-3">
                <div class="card-body">
                    <h1 class="card-title">${subscriptionsCount}</h1>
                    <div class="card-text">Subscriptions</div>
                </div>
            </div>
        </div>
    </div>

    <#if !isCurrentUser>
        <#if !isSubscribed>
            <a href="/user_topics/${user.id}/subscribe/" class="btn btn-primary mb-3">Subscribe</a>
        <#else>
            <a href="/user_topics/${user.id}/unsubscribe/" class="btn btn-primary mb-3">Unsubscribe</a>
        </#if>
    </#if>

<@tl.showList topics true />

</@base.content>
