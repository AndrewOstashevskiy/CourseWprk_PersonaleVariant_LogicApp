<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    Registration
    <div>  ${regStatusMessage!""}</div>
    <@l.login "/reg" "Sign Up" />
</@c.page>