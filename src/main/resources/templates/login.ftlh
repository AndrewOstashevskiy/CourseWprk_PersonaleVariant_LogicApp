<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <div class="text mb-2">Logination</div>
    <div><@l.login "/login" "Log In" /></div>
    <div class="text-danger mt-2">${errorMessage! " "}</div>
</@c.page>