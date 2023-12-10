<b>管理员列表</b>
<#list adminList as item>${item.account}  <code><#if item.permission == 0> 权限人 <#else> 操作人 </#if></code>
</#list>