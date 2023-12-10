入款（${inCount}笔）：
<#list inList as item>
    <#if rate!=1>    ${item.label}</#if>   <code><b>${item.createTime?string("HH:mm:ss")}</b></code>     ${item.amount?string("0.##")}<#if item.rate!=1> / ${item.rate?string("0.##")}=${item.amountUsdt?string("0.##")}</#if> ${currency}
</#list>

下发（${outCount}笔）：
<#list outList as item>
    <#if rate!=1>    ${item.label}</#if>   <code><b>${item.createTime?string("HH:mm:ss")}</b></code>     ${item.amountUsdt?string("0.##")} ${currency}
</#list>

总入款：${amountIn?string("0.##")}
费率：${fee?string("0.##")}%
<#if rate!=1>${currency}汇率：${rate?string("0.##")}</#if>

应下发：${amountUsdtIn?string("0.##")} ${currency}
总下发：${amountUsdtOut?string("0.##")} ${currency}
未下发：${amountUsdtPending?string("0.##")} ${currency}

<a href="https://telbot.sskj.xyz/${groupId?string("0.##")}/0.html">[显示完整账单]</a>