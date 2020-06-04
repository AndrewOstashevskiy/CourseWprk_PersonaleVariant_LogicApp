<#import "parts/common.ftlh" as c>

<@c.page>
    <div>Users list:</div>
    <table class="table">
        <thead class="thead-dark">
        <tr scope ="row">
            <th scope="col">Name</th>
            <th scope="col">Role</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#list users as u>
            <tr>
                <td>${u.username}</td>
                <td>
                    <#list u.roles as role>
                        ${role}<#sep> ,
                    </#list>
                </td>
                <td><a href="/user/${u.id}">edit</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>