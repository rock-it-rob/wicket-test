// Creates a dropzone on the given element and places its hidden input on the containerElement.
function createDropzone(element, containerElement, url) {
    
    /*
    try {
        Dropzone.forElement(element);
        console.log('dropzone exists');
        return;
    }
    catch (e) {}
    */

    try {
        new Dropzone(element, {
            hiddenInputContainer: containerElement,
            url: url,
            headers: {
                'Wicket-Ajax': 'true',
                'Wicket-Ajax-BaseURL': Wicket.Ajax.baseUrl
            },
            init: function() {
                this.on("success", function(file, response) {
                    this.wicketResponse = response;
                }),
                this.on("queuecomplete", function() {
                    Wicket.Ajax.process(this.wicketResponse);
                })
            }
            //autoProcessQueue: false
        });
        console.log('dropzone created');
    }
    catch (e) {
        console.log('Could not create dropzone on element: ' + element)
        console.log(e);
     }
}