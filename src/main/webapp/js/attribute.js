// Prints the attributes from "elementId" into the element identified by "targetElementId".
// the target element should be a div.
function listAttributes(elementId, targetElementId)
{
    var targetId = "#" + targetElementId;

    $(targetId).empty();
    var list = $(targetId).append("<ul></ul>");

    var sourceId = "#" + elementId;
    var css = $(sourceId).attr("class");

    if (css == null)
    {
        return;
    }

    var attributes = $(sourceId).attr("class").split(" ");

    attributes.forEach(e => list.append("<li>" + e + "</li>"));
}