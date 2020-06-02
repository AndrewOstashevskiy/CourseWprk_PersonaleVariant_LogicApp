<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="float-right">
        <div class="form-row align-items-center">
            <div class="form-group col-md-1">
                <form method="get" action="/user-page" class="form-inline">
                    <input type="text" class="form-control" name="filter" placeholder="Filter">
                    <button type="submit" class="btn btn-primary mt-2">Submit</button>
                </form>
            </div>
        </div>
    </div>

    <h4>
    LogicApp main Page
    <h4>
    <footer class="blockquote-footer mb-5">Your balance:
        <cite title="Source Title">
            ${balance}
        </cite>$
    </footer>
    <div class="container">
        <div class="text-center mb-2">My actions for sales</div>
        <div class="text-right mb-2">Filtered by: "${filter!""}"</div>
        <table class="table">
            <thead class="thead-dark">
            <tr scope="row">
                <th scope="col">Action's domain</th>
                <th scope="col">Owner Name</th>
                <th scope="col">Description</th>
                <th scope="col">Creation/Update date</th>
                <th scope="col">Price</th>
                <th scope="col">Status</th>
                <th scope="col"></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <#list productsForSale as product>
                <tr>
                    <td>${product.domain}</td>
                    <td>${product.owner.username}</td>
                    <td>${product.description}</td>
                    <td>${product.date}</td>
                    <td>${product.price}</td>
                    <td>${product.status}</td>
                    <td><a href="/user-page/${product.id}">Edit</a></td>
                    <td>
                        <#if user.roles?seq_contains("USER")>
                            <form method="get" action="/user-page/place/${product.id}" class="form-inline">
                                <button type="submit" class="btn btn-outline-primary">Place</button>
                            </form>
                        </#if>
                    </td>
                </tr>
            <#else>
                No elements
            </#list>
            </tbody>
        </table>
    </div>

    <div class="container">
        <div class="text-center mt-5">Selling</div>
        <table class="table">
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
            <#list productsSelling as product>
                <tr>
                    <td>${product.domain}</td>
                    <td>${product.owner.username}</td>
                    <td>${product.description}</td>
                    <td>${product.date}</td>
                    <td>${product.price}</td>
                    <td>${product.status}</td>
                    <td><a href="/user-page/${product.id}">Edit</a></td>
                </tr>
            <#else>
                No elements
            </#list>
            </tbody>
        </table>
    </div>

    <div class="container">
        <div class="text-center mb-2 mt-5">My sold actions</div>
        <table class="table">
            <thead class="thead-dark">
            <tr scope="row">
                <th scope="col">Action's domain</th>
                <th scope="col">Owner Name</th>
                <th scope="col">Description</th>
                <th scope="col">Creation/Update date</th>
                <th scope="col">Price</th>
            </tr>
            </thead>
            <tbody>
            <#list soldProducts as product>
                <tr>
                    <td>${product.domain}</td>
                    <td>${product.owner.username}</td>
                    <td>${product.description}</td>
                    <td>${product.date}</td>
                    <td>${product.price}</td>
                </tr>
            <#else>
                No elements
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>