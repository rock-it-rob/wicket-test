// Creates a dropzone on the given element and places its hidden input on the containerElement.
function createDropzone(element, containerElement, url, maxFiles) {

    try {
        new Dropzone(element, {
            uploadMultiple: true,
            maxFiles: maxFiles,
            parallelUploads: maxFiles,
            // Set the hidden element on the container of the dropzone.
            hiddenInputContainer: containerElement,
            // Wicket ajax behavior callback url.
            url: url,
            // Turn off the timeout for ajax requests. This matches wicket's default.
            timeout: 0,
            // These are necessary to get wicket to send back a valid response.
            headers: {
                'Wicket-Ajax': 'true',
                'Wicket-Ajax-BaseURL': Wicket.Ajax.baseUrl
            },
            // Don't automatically upload files; just queue them.
            autoProcessQueue: false,
            init: function () {

                this.on('sendingmultiple', function (files) {
                    //
                    // Do clickblocker stuff here.
                    //
                });

                // On success process the wicket response.
                this.on("successmultiple", function (file, response) {
                    Wicket.Ajax.process(response);
                });

                // On error process the wicket response if the error came from the server.
                // Dropzone will set the request object in this case. If there is no server 
                // error then just console the error and let dropzone display it on its preview.
                this.on("errormultiple", function (file, msg, xhr) {
                    if (xhr != null) {
                        Wicket.Ajax.process(msg);
                    }
                    else {
                        console.error(msg);
                    }
                });

                // Once the upload process is finished, remove any files.
                this.on("queuecomplete", function () {
                    this.removeAllFiles();

                    //
                    // Hide clickblocker here
                    //
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
    var element = $(event.target).parent().children('.dz-item').get(0);
    var dz = Dropzone.forElement(element);

    if (dz.getRejectedFiles().length == 0) {
        dz.processQueue();
    }
}

function submitDropzoneRequest(id) {
    var dz = Dropzone.forElement(document.getElementById(id));

    if (dz.getRejectedFiles().length == 0) {
        dz.processQueue();
    }
}