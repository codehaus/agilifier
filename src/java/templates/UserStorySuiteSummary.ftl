<html>
    <head>
        <link rel="stylesheet" href="overview.css">
        <meta http-equiv="refresh" content="60"/>
    </head>
    <body>
        <div class="suiteSummary">
            <div class="moduleSummaryTitle">
                ${suite.module.name} - ${suite.description}
            </div>
            <br/>
            <div class="moduleDetail">
        <#if suite.stories?size == 0>
            <div class="noData">
                There are currently no user stories in this section.
            </div>
        <#else>
            <table border='0' bordercolor='gray' cellspacing='5' width='100%'>
                <tr height="80">
                    <td width="100%" border="1">
                        <table width="100%" height="100%" bgcolor="gray">
                            <tr>
                                <#if (suite.passedPercent>0)>
                                    <td bgcolor='green' width="${suite.passedPercent}%">&nbsp;</td>
                                </#if>
                                <#if (suite.failedPercent>0) || suite.passedPercent == 0>
                                    <td bgcolor='red'   width="${suite.failedPercent}%">&nbsp;</td>
                                </#if>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td nowrap align="center">
                        Acceptance Tests: ${suite.passedCount} passed, ${suite.failedCount} failed
                    </td>
                </tr>
            </table>

            <br/>

            <table class="results" border='0'>
                <tr class="heading">
                    <td>
                        <b>User Story</b>
                    </td>
                    <td>
                        <b>Acceptance Tests</b>
                    </td>
                    <td>&nbsp;</td>
                </tr>
        <#foreach story in suite.stories>        <#if (story.failedCount>0) || story.passedCount == 0>                <tr class="failed">
        <#else>                <tr class="passed">
        </#if>                    <td>
                        <div class="fat">
                            <a href='${story.name}/summary.html'>${story.description}</a>
                        </div>
                    </td>
                    <td>
                        <div class="fat">
                            ${story.passedCount} passed, ${story.failedCount} failed.
                        </div>
                    </td>
                    <td width="250">
                        <table width="100%" height="100%" bgcolor="gray">
                            <tr height='20'>
        <#if (story.passedPercent>0)>                                <td bgcolor='green' width="${story.passedPercent}%">&nbsp;</td>
        </#if>        <#if (story.failedPercent>0) || story.passedPercent == 0>                                <td bgcolor='red'   width="${story.failedPercent}%">&nbsp;</td>
        </#if>                            </tr>
                        </table>
                    </td>
                </tr>
        </#foreach>              </table>
</#if>              <br/>
            </div>
        </div>
    </body>
</html>
