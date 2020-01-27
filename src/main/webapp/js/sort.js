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
    dataIdAttr: 'data-id',
    onSort: onCanvasSort
  });
}

function onCanvasSort(event)
{
  $('#canvas-order').val(getCanvasDataIdOrder());
  $('#canvas-order').trigger('click');
}

function getCanvasDataIdOrder()
{
  var canvas = $(".widget-canvas").get(0);
  var sortable = Sortable.get(canvas);

  return sortable.toArray();
}