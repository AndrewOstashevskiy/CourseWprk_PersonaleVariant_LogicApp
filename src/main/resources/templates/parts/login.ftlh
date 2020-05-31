<#macro login path but>
    <form action="${path}" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div class="form-group row">
        <label class="col-sm-2 col-form-label"> User Name</label>
            <div class="col-sm-4">
            <input type="text" name="username" class="form-control"/>
        </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Password</label>
            <div class="col-sm-4">
                <input type="password" name="password" class="form-control"/>
            </div>
        </div>
        <div><button type="submit" value="${path}" class="btn btn-primary"/>${but}</div>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit"   class="btn btn-primary">Sign Out</button>
    </form>
</#macro>
