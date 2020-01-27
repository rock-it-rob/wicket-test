$(document).ready(startSort());

function startSort()
{
  Sortable.create(document.getElementsByClassName("widget-palette")[0], {
    handle: '.handle',
    animation: 120,
    direction: 'horizontal',
    ghostClass: 'widget-ghost',
    group: {
      name: 'palette'
    },
    dataIdAttr: 'data-id'
  });

  Sortable.create(document.getElementsByClassName("widget-canvas")[0], {
    handle: '.handle',
    animation: 120,
    direction: 'horizontal',
    ghostClass: 'widget-ghost',
    group: {
      name: 'canvas',
      put: ['palette']
    },
    dataIdAttr: 'data-id'
  });
}

function getSortableId(element)
{
  /*
  var id = $(element).attr('data-id');
  alert($(element).parent().html() + ": " + id);
  */
 var widget = $(element).parents('.widget').first();
 alert("data-id: " + widget.attr('data-id'));

 var s = Sortable.get($('.widget-palette').get(0));
 alert(s.toArray());
}