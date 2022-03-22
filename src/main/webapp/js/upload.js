// Creates a dropzone on the given element and places its hidden input on the containerElement.
function createDropzone(element, containerElement, url, maxFiles, multiple) {

    try {
        new Dropzone(element, {
            previewTemplate: $(containerElement).find('.preview-template').parent()[0].innerHTML,
            uploadMultiple: multiple,
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

                this.on('sending', function (file) {
                    //
                    // Do clickblocker stuff here.
                    //
                });
                this.on('sendingmultiple', function (files) {
                    //
                    // Do clickblocker stuff here.
                    //
                });

                // On success process the wicket response.
                this.on('success', function (file, response) {
                    console.log('success');
                    this.removeFile(file);
                    Wicket.Ajax.process(response);
                });
                this.on("successmultiple", function (file, response) {
                    console.log("success multiple");
                    this.removeAllFiles();
                    Wicket.Ajax.process(response);
                });

                // On error process the wicket response if the error came from the server.
                // Dropzone will set the request object in this case. If there is no server 
                // error then just console the error and let dropzone display it on its preview.
                this.on('error', function (file, msg, xhr) {
                    if (xhr != null) {
                        Wicket.Ajax.process(msg);
                    }
                    else {
                        console.error(msg);
                    }
                });
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
                    //this.removeAllFiles();
                    console.log("queue complete");

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
function processDropzoneQueue(id) {
    var dz = Dropzone.forElement(document.getElementById(id));

    if (dz.getRejectedFiles().length == 0) {
        dz.processQueue();
    }
}