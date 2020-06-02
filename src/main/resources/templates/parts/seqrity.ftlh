<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    isAdmin = user.isAdmin()
    balance = user.getBalance().getBalance()
    >
<#else>
    <#assign
    name = "undefinded"
    isAdmin = false
    balance = 0
    >
</#if>
