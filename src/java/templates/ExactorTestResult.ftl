<html>
    <head>
        <h1>${acceptanceTestName}</h1>
    </head>
    <body>
        <table border="1" class="testScriptTable" cellpadding='4' cellspacing='1'>
        <#foreach result in results>
            <#if result.comment>
                <tr><td colspan="6" bgcolor="#cccccc">${result.command.name} </td></tr>
            <#else>
                <tr>
                    <td>${result.command.name}</td>
                    <#foreach parameter in result.parameters>
                        <td>${parameter}</td>
                    </#foreach>
                    <#if result.hasError >
                        <td bgcolor="#FFCCCC">${result.exceptionForHtml}</td>
                    <#else>
                        <td bgcolor="#CCFFCC">OK</td>
                    </#if>
                </tr>
            </#if>
        </#foreach>
    </table>

        <br/>

        <table class="testScriptTable" border="1" cellpadding='4' cellspacing='1'>
            <tr>
                <td valign='MIDDLE' colspan="2">
                    <p>
                        <i><b>
                            Summary
                        </b></i>
                    </p>
                </td>
            </tr>
            <tr>
                <td>
                    <font color='#808080'>
                        counts
                    </font>
                </td>
                <td bgcolor='#cfffcf'>
                    <font color='#808080'>
                        ${executed} commands executed. ${failed} failed.
                    </font>
                </td>
            </tr>
            <tr>
                <td>
                    <font color='#808080'>
                        input file
                    </font>
                </td>
                <td>
                    <font color='#808080'>
                        ${inputFile}
                    </font>
                </td>
            </tr>
            <tr>
                <td>
                    <font color='#808080'>
                        input update
                    </font>
                </td>

                <td>
                    <font color='#808080'>
                        ${inputUpdate}
                    </font>
                </td>
            </tr>
            <tr>
                <td>
                    <font color='#808080'>
                        output file
                    </font>
                </td>
                <td>
                    <font color='#808080'>
                        ${outputFile}
                    </font>
                </td>
            </tr>
            <tr>
                <td>
                    <font color='#808080'>
                        run date
                    </font>
                </td>
                <td>
                    <font color='#808080'>
                        ${runDate}
                    </font>
                </td>

            </tr>
            <tr>
                <td>
                    <font color='#808080'>
                        run elapsed time
                    </font>
                </td>
                <td>
                    <font color='#808080'>
                        ${elapsedTime} seconds
                    </font>
                </td>
            </tr>
        </table>
    </body>
</html>