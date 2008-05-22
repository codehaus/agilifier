<html>
<head>
   <link rel="stylesheet" href="../overview.css">
</head>
<body>
<div class="storyCard">
    <div class="storyTitle">${userStory.description}</div>
    <div class="storyContent">
    <#foreach line in userStory.storyText>
        ${line}<br>
    </#foreach>
    </div>
    <#if userStory.allTests?size == 0 >
        <div class="noData">
        There are currently no tests for this story.
        </div>
    <#else>
        <br/>
        <table class="results" border='0'>
            <tr class="heading">
                <td>
                    <div class="fat">Acceptance Tests</div>
                </td>
            </tr>
        <#foreach test in userStory.allTests>
            <#if test.passed>
            <tr class="passed">
            <#else>
            <tr class="failed">
            </#if>
                <td><div class="fat"><a href='${test.name}.html'>${test.description}</a></div></td>
             </tr>
        </#foreach>
        </table>
    </#if>
    <br/>
</div>
</body>
</html>
