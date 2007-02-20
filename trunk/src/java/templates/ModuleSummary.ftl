<html>
    <head>
       <link rel="stylesheet" href="overview.css">
    </head>
    <body>
        <div class="moduleSummary">
            <div class="moduleSummaryTitle">
                Project Summary - ${module.name}
            </div>
            <div class="moduleDetails"/>
            <#foreach suite in module.userStorySuites>
                <p class="storySuite">
                <a href="summary${suite.name}.html">${suite.description} (${suite.stories?size} user stories)</a></p>
            </#foreach>
            </div>
        </div>
    </body>
</html>
