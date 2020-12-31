package com.dare.videoQuery.editResultAPI;

import com.dare.videoQuery.model.VideoEditObject;
import com.dare.videoQuery.model.VideoObject;
import com.dare.videoQuery.service.EditResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("editResultAPI/v1/videoObject")
@RestController
public class EditResultController {
    private final EditResultService editResultService;

    @Autowired
    public EditResultController(EditResultService editResultService) {
        this.editResultService = editResultService;
    }

    @PutMapping
    public void editVideoResult(@RequestBody VideoEditObject editingObject){
        int editingObjectId = editingObject.getObjectId();
        String editingObjectType = editingObject.getObjectType();
        int editingObjectTypeCode = editingObject.getObjectTypeCode();
        int editingFrameNumber = editingObject.getFrameNumber();
        int editingNewObjectId = editingObject.getNewObjectId();
        editResultService.editVideoResult(editingObjectId, editingObjectType, editingObjectTypeCode, editingFrameNumber, editingNewObjectId);
    }
}
