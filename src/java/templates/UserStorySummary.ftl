<html>
<head>
   <link rel="stylesheet" href="${userStory.pathToRoot}/overview.css">
</head>
<body>
<div class="storyCard">
    <div class="storyTitle">${userStory.description}</div>
    <div class="storyContent">
        ${userStory.text}<br>
    </div>
    <#if userStory.children?size == 0 >
        <div class="noData">
        There are currently no tests for this story.
        </div>
    <#else>
        <br/>
        <table class="results" border='0'>
            <tr class="heading">
                <td>
                    <div class="fat">User Stories / Acceptance Tests</div>
                </td>
            </tr>
        <#foreach test in userStory.children>
            <#if test.isTest()>
                <#if test.passed>
                <tr class="passed">
                <#else>
                <tr class="failed">
                </#if>
                    <td><div class="fat"><a href='./${test.name}.html'>${test.description}</a></div></td>
                 </tr>
            <#else>
                <tr class="passed">
                    <td><div class="fat"><a href='./${test.name}/summary.html'>${test.description} </a></div></td>
                 </tr>
            </#if>
        </#foreach>
        </table>
    </#if>
    <br/>
</div>
</body>
</html>
