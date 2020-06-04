<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    Registration
    <@l.login "/reg" "Sign Up" />
    <div class="mt-3">  ${regStatusMessage!""}</div>
</@c.page>