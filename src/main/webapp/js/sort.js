$(document).ready(startSort());

function startSort()
{
  // Configure available list.
  Sortable.create($("#available-items").get(0), {
    group: {
      name: "widget-group"
    },
    handle: '.handle',
    animation: 120,
    direction: 'vertical'
  });

  // Configure palette.
  Sortable.create($("#chosen-items").get(0), {
    group: {
      name: "palette-group",
      put: ["widget-group"]
    },
    direction: 'horizontal',
    handle: '.handle',
    ghostClass: 'widget-ghost',
    animation: 120
  });
}

function sortWidgets()
{
  // TODO: Put a sort here.
}

function removeFromPalette(link)
{
  var widget = $(link).parent();
  alert('Removing: ' + widget);
}