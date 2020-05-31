<#import "parts/common.ftlh" as c>

<@c.page>
    <div>
        <h4>Edit Item</h4>
    </div>
    <div>
        <div class="mb-2">Error message: "${msg!""}"</div>
    </div>
    <form method="post" action="/user-page/update">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="hidden" name="id" value="${item.id}">
        Domain: <input type="text" name="domain" value="${item.domain}">
        Description: <input type="text" name="description" value="${item.description}">
        Price: <input type="text" name="price" value="${item.price}">
        <button type="submit">Save</button>
    </form>
</@c.page>