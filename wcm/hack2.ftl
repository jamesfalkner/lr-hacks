<#--DynamicQuery using Freemarker in Liferay-->

<#if request.lifecycle == "RENDER_PHASE">

<article>
	<h1 class="section-heading section-heading-b">
		<div>Hot Topics (FTL)</div>
		<div class="section-heading-hr"></div>
	</h1>

	<div id='${randomNamespace}tablediv' style='width: 85%;'><!-- --></div>
</article>

<script type="text/javascript">

  var ${randomNamespace}table;

	var ${randomNamespace}tablediv = document.getElementById('${randomNamespace}tablediv');

	function ${randomNamespace}drawChart() {

		var html =
		    '<table class="table table-hover table-bordered table-striped"> \
	<thead> \
	<tr> \
	<th> \
        ${languageUtil.get(locale, "count")} \
	</th> \
	<th> \
        ${languageUtil.get(locale, "subject")} \
	</th> \
	</tr> \
	</thead> \
	<tbody>';

	  for (i = 0; i < ${randomNamespace}table.length; i++) {
	    html +=
		('<tr><td>' + ${randomNamespace}table[i].msgCount + '</span></td>');

	    html +=
		('<td><a href="/web/guest/forums/-/message_boards/message/' +
                ${randomNamespace}table[i].msgid + '">' +
                ${randomNamespace}table[i].subject +
		  '</a></td></tr>');
    }

		html += ('</tbody></table>');

          console.log("pushing " + html + " to " + '${randomNamespace}tablediv');

    ${randomNamespace}tablediv.innerHTML = html;
	}


	function ${randomNamespace}getTable() {
          console.log("in FTL gettable");
		AUI().use(
		    "aui-base", "aui-io-plugin", "aui-io-request",
		    function (A) {
                console.log("sending request to " + "request['resource-url']");
			    A.io.request(
				"${request['resource-url']}",
				{
					data: {},
					dataType: "json",
					on: {
						success: function (event, id, obj) {
							var responseData = this.get("responseData");
                                                  ${randomNamespace}table = responseData.jsonArray || [];

                                                  ${randomNamespace}drawChart();
						},
						failure: function (event, id, obj) {
                    console.log("fail: " + JSON.stringify(event));
						}
					}
				}
			    );
		    }
		);
	}

        AUI().ready(function() {
           ${randomNamespace}getTable();
        });
</script>

<#elseif request.lifecycle == "RESOURCE_PHASE">

  <#assign jsonFactory = staticUtil["com.liferay.portal.kernel.json.JSONFactoryUtil"] />
  <#assign dqfu = staticUtil["com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"] />
  <#assign rfu = staticUtil["com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil"] />
  <#assign pfu = staticUtil["com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil"] />
  <#assign ofu = staticUtil["com.liferay.portal.kernel.dao.orm.OrderFactoryUtil"] />
  <#assign cru = staticUtil["com.liferay.portal.kernel.util.ClassResolverUtil"] />

  <#assign mbThreadLocalServiceUtil = staticUtil["com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil"] />
  <#assign mbMessageLocalServiceUtil = staticUtil["com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil"] />
  <#assign mbThreadClass = cru.resolveByPortalClassLoader("com.liferay.portlet.messageboards.model.MBThreadModel") />
  <#assign mbThreadQuery = dqfu.forClass(mbThreadClass) />

  <#assign calClass = objectUtil("java.util.GregorianCalendar").getInstance() />
  <#assign now = calClass.getInstance() />
  <#assign weeksago = calClass.getInstance() />
  <#assign VOID = weeksago.add(3, -52) />

  <#assign VOID = mbThreadQuery.add(rfu.ne("categoryId", getterUtil.getLong("-1"))) />
  <#assign VOID = mbThreadQuery.add(rfu.eq("groupId", groupId)) />
  <#assign VOID = mbThreadQuery.add(rfu.eq("companyId", companyId)) />
  <#assign VOID = mbThreadQuery.add(rfu.eq("status", getterUtil.getInteger("0"))) />
  <#assign VOID = mbThreadQuery.add(rfu.between("lastPostDate", weeksago.getTime(), now.getTime())) />
  <#assign VOID = mbThreadQuery.setProjection(pfu.property("threadId")) />

  <#assign threads = mbThreadLocalServiceUtil.dynamicQuery(mbThreadQuery) />

  <#assign mbMessageClass = cru.resolveByPortalClassLoader("com.liferay.portlet.messageboards.model.MBMessageModel") />
  <#assign mbMessageQuery = dqfu.forClass(mbMessageClass) />
  <#assign VOID = mbMessageQuery.add(rfu.in("threadId", threads)) />
  <#assign VOID = mbMessageQuery.add(rfu.between("createDate", weeksago.getTime(), now.getTime())) />

  <#assign VOID = mbMessageQuery.setProjection(
    pfu.projectionList().add(
    pfu.groupProperty("rootMessageId")).add(
    pfu.alias(
    pfu.rowCount(),"msgCount"))) />

  <#assign VOID = mbMessageQuery.addOrder(ofu.desc("msgCount")) />
  <#assign VOID = mbMessageQuery.setLimit(0, 7) />

  <#assign jsonArray = jsonFactory.createJSONArray() />

  <#list mbMessageLocalServiceUtil.dynamicQuery(mbMessageQuery) as mbMessage>
    <#assign rootMsgId = mbMessage[0] />
    <#assign msgCount = mbMessage[1] />
    <#assign subject = mbMessageLocalServiceUtil.getMessage(rootMsgId).getSubject() />

    <#assign jsonObject = jsonFactory.createJSONObject() />
    <#assign VOID = jsonObject.put("subject", stringUtil.shorten(htmlUtil.escape(subject), 55)) />
    <#assign VOID = jsonObject.put("msgid", rootMsgId) />
    <#assign VOID = jsonObject.put("msgCount", msgCount) />

    <#assign VOID = jsonArray.put(jsonObject) />
  </#list>
{
"lang": "ftl",
"jsonArray": ${jsonArray}
}
</#if>