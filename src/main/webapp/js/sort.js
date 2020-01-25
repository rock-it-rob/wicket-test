$(document).ready(startSort());

function startSort()
{
  Sortable.create($(".widget-canvas").get(0), {
    handle: '.handle',
    animation: 120,
    direction: 'horizontal',
    ghostClass: 'widget-ghost'
  });
}