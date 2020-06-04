<#include "seqrity.ftlh">
<#import "login.ftlh" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand p-3 mb-2 bg-primary text-white" href="/">LogicApp</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <#if known>
                <#if user.roles?seq_contains("USER")>
                    <#if name != "undefinded">
                        <li class="nav-item">
                            <a class="nav-link" href="/user-page">Room</a>
                        </li>
                    </#if>
                </#if>
            </#if>

            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/user">User List</a>
                </li>
            </#if>

            <#if name != "undefinded">
                <li class="nav-item">
                    <a class="nav-link" href="/market">Market</a>
                </li>
            </#if>

            <#if name == "undefinded">
                <li class="nav-item">
                    <a class="nav-link" href="/login">Log In</a>
                </li>
            </#if>

            <#if name == "undefinded">
                <li class="nav-item">
                    <a class="nav-link" href="/reg">Sign Up</a>
                </li>
            </#if>
        </ul>

        <#if known>
        <div class="navbar-text mr-3">Name: ${name}</div>
        <div class="navbar-text mr-3">
            Roles:<#list user.getAuthorities() as role>
            <tr>
            ${role}<#sep> ,
                </tr>
            </#list>
            </#if>
        </div>
        <#if name != "undefinded"><@l.logout /></#if>
    </div>
</nav>