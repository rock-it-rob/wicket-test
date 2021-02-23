// Creates a dropzone on the given element and places its hidden input on the containerElement.
function createDropzone(element, containerElement) {
    
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
            hiddenInputContainer: containerElement
        });
        console.log('dropzone created');
    }
    catch (e) {
        console.log('Could not create dropzone on element: ' + element)
        console.log(e);
     }
}