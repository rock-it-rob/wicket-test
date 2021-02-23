// Creates a dropzone on the given element and places its hidden input on the containerElement.
function createDropzone(element, containerElement, url) {

    try {
        new Dropzone(element, {
            // Set the hidden element on the container of the dropzone.
            hiddenInputContainer: containerElement,
            // Wicket ajax behavior callback url.
            url: url,
            // These are necessary to get wicket to send back a valid response.
            headers: {
                'Wicket-Ajax': 'true',
                'Wicket-Ajax-BaseURL': Wicket.Ajax.baseUrl
            },
            // Don't automatically upload files; just queue them.
            autoProcessQueue: false,
            init: function () {
                // Save the wicket response from a file upload event. This may get called multiple times in a single upload depending on the number of files. Only save the last response.
                this.on("success", function (file, response) {
                    this.wicketResponse = response;
                });
                // Use the special wicket junk to interpret the saved response. If this isn't processed through the wicket jquery helper it won't render correctly.
                this.on("queuecomplete", function () {
                    Wicket.Ajax.process(this.wicketResponse);
                });
            }

        });
        console.log('dropzone created on: ' + element.id);
    }
    catch (e) {
        console.log('Could not create dropzone on element: ' + element.id)
        console.log(e);
    }
}

// Process the queue on the dropzone at the given element.
function processDropzoneQueue(event) {
    var element = $(event.target).parent().children('.dz-form').get(0);
    Dropzone.forElement(element).processQueue();
}