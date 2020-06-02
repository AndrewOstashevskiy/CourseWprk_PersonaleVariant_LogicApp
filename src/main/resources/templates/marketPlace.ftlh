<#import "parts/common.ftlh" as c>
<#include "parts/seqrity.ftlh">

<@c.page>
    MarketPlace
    <footer class="blockquote-footer mb-5">Your balance:
        <cite title="Source Title">
            ${balance}
        </cite> btc
    </footer>

    <#if user.roles?seq_contains("ADMIN")>
        <#if name != "undefinded">
            <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button"
               aria-expanded="false"
               aria-controls="collapseExample">
                Place new action
            </a>
            <div class="collapse mt-2" id="collapseExample">
                <div class="row">
                    <div class="col">
                        <form method="post" action="/market/add" class="form-inline">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <input type="text" class="form-control mr-2" name="description" placeholder="Description">
                            <input type="text" name="domain" class="form-control mr-2" placeholder="Domain">
                            <input type="text" name="price" class="form-control mr-2" placeholder="Price">
                            <button type="submit" class="btn btn-primary ml-3">Add</button>
                        </form>
                    </div>
                </div>
            </div>
            <div>
                <form method="post" action="/market/generate" class="form-inline">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-primary ml-3">Generate Data</button>
                </form>
            </div>
        </#if>
    </#if>

<div class="float-righttext-danger mt-5">
    ${message!" "}
</div>
    <table class="table mt-5">
        <thead class="thead-dark">
        <tr scope="row">
            <th scope="col">Action's domain</th>
            <th scope="col">Owner Name</th>
            <th scope="col">Description</th>
            <th scope="col">Creation/Update date</th>
            <th scope="col">Price</th>
            <th scope="col">Status</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#list products as product>
            <tr>
                <td>${product.domain}</td>
                <td>${product.owner.username}</td>
                <td>${product.description}</td>
                <td>${product.date}</td>
                <td>${product.price}</td>
                <td>${product.status}</td>
                <td>
                    <#if user.roles?seq_contains("USER")>
                    <form method="get" action="/market/buy/${product.id}" class="form-inline">
                        <button type="submit" class="btn btn-outline-primary">Buy</button>

                    </form>
                    </#if>
                </td>
            </tr>
        <#else>
            No elements
        </#list>
        </tbody>
    </table>

</@c.page>