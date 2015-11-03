<%@ page import="com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.journal.model.JournalArticle" %>
<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.service.ClassNameLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil" %>
<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="liferay-ui" uri="http://liferay.com/tld/theme" %>
<%@ taglib prefix="liferay-theme" uri="http://java.sun.com/portlet" %>

<style type="text/css">
    #editor {
        position: relative;
        width: 800px;
        height: 400px;
    }
</style>

<portlet:defineObjects />
<liferay-ui:defineObjects />
<liferay-theme:defineObjects/>
<portlet:resourceURL var="resourceURL">
</portlet:resourceURL>


<div class="row-fluid">
    <div class="span3">

        <label for="articleSelect">Select Article</label>
        <select onchange="isDirty = true; lastchange=new Date().getTime();<portlet:namespace/>populateVars();" id="articleSelect">
            <option label="-- None --" name="-- None --" value="" ></option>
            <%
                List<JournalArticle> articles = JournalArticleLocalServiceUtil.getArticles(scopeGroupId);
                for (JournalArticle article : articles) {
                    String articleId = article.getArticleId();
                    String articleTitle = article.getTitle(locale);
                    String structureId = article.getStructureId();
                    String structureName = DDMStructureLocalServiceUtil.getStructure(scopeGroupId, ClassNameLocalServiceUtil.getClassNameId(JournalArticle.class), structureId).getName(locale);


            %> <option label="<%=articleTitle%> (<%= structureName %>)" name="<%= articleId %>" value="<%= articleId %>" ></option> <%
            }
        %>

        </select>
        <div id="vars"></div>

    </div>
    <div class="span9">
        <div id="theError"></div>
        <label for="editor">Template code</label>
        <div id="editor">some text</div>
        <h2>Result:</h2>
        <div id="theResult"></div>

    </div>
</div>

<script type="text/javascript">

    var isDirty = false;
    var lastchange = new Date().getTime();
    var editor;

    setInterval(function() {
        if (isDirty && ((new Date().getTime()) - lastchange) > 1300) {
            console.log("rendering at " + new Date());
            lastchange = new Date().getTime();
            isDirty = false;
            <portlet:namespace/>renderTemplate();
        }
    }, 500);

    function <portlet:namespace/>populateVars() {
        var articleId = document.getElementById('articleSelect').value;

        if (!articleId) {
            document.getElementById('vars').innerHTML = '';
            return;
        }

        <portlet:namespace/>auiXmlHttpRequest('<%= resourceURL %>', {
            "<portlet:namespace/>cmd": 'getVars',
            "<portlet:namespace/>articleId": articleId
        }, function (result) {
            var varobj = JSON.parse(result);
            var html='<p style="line-height: 110%;">';

            varobj.forEach(function(theVar) {
                html+= ("<div style='display:inline-block'><code>" + theVar + "</code>&nbsp;&nbsp;</div>");
            });
            html += ('</p>');
            document.getElementById('vars').innerHTML = html;
        }, function (errmsg) {
            document.getElementById('vars').innerHTML = errmsg;
        });

    }

    function <portlet:namespace/>renderTemplate() {
        var templateStr = editor.getValue();
        var articleId = document.getElementById('articleSelect').value;

        editor.session.clearAnnotations();

        <portlet:namespace/>auiXmlHttpRequest('<%= resourceURL %>', {
            "<portlet:namespace/>cmd": 'renderTemplate',
            "<portlet:namespace/>template": templateStr,
            "<portlet:namespace/>articleId": articleId
        }, function (result) {
            document.getElementById("theResult").innerHTML = result;
            document.getElementById("theError").innerHTML = '';
        }, function (errmsg) {
            document.getElementById("theResult").innerHTML = '';
            document.getElementById("theError").innerHTML = '<span style="font-size:.8em;color:red;font-style:italic;">' +
                    errmsg + '</span><br>';
            // highlight line
            // at line 62, column 39 in templateName
            var re = /\s+line\W+(\d+).*column\W+(\d+)/i ;
            var result = errmsg.match(re);
            var lineno = result[1];
            var colno = result[2];
            console.log("err: line: " + lineno + " col: " + colno);
            if (lineno >= 0 && colno >= 0) {
                editor.gotoLine(lineno, colno, true);

                editor.session.setAnnotations([{
                    row: lineno-1,
                    column: colno-1,
                    text: errmsg,
                    type: "error"
                }]);
            }

        });
    }

    function <portlet:namespace/>auiXmlHttpRequest(url, data, onSuccess, onError) {

        AUI().use(
                "aui-base", "aui-io-plugin", "aui-io-request",
                function (A) {
                    A.io.request(
                            url,
                            {
                                data: data,
                                dataType: "json",
                                on: {
                                    success: function (event, id, obj) {
                                        var responseData = this.get("responseData");
                                        var stat = responseData.stat;
                                        if (stat == 'ok') {
                                            onSuccess(responseData.result);
                                        } else if (stat == 'error') {
                                            onError(responseData.error);
                                        } else {
                                            onError("unknown error");
                                        }
                                    },
                                    failure: function (event, id, obj) {
                                        onError(JSON.stringify(event));
                                    }
                                }
                            }
                    );
                }
        );
    }


    AUI().ready(function() {
        editor = ace.edit("editor");
        ace.require("ace/ext/language_tools");
        editor.setOptions({
            enableBasicAutocompletion: true
        });

        var ftlmode = ace.require("ace/mode/ftl").Mode;
        editor.session.setMode(new ftlmode());

        editor.on('change', function() {
            lastchange = new Date().getTime();
            isDirty = true;
        });
    });



</script>