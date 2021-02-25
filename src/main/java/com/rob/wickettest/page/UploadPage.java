package com.rob.wickettest.page;

import com.rob.wickettest.component.dropzone.DropZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadPage extends AbstractPage
{
    private static final Logger log = LoggerFactory.getLogger(UploadPage.class);

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final DropZone dropZone = new DropZone("dropZone");
        add(dropZone);
    }
}
