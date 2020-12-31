package com.dare.videoQuery.service;

import com.dare.videoQuery.dao.EditResultDao;
import com.dare.videoQuery.model.VideoObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EditResultService {

    private final EditResultDao editResultDao;

    @Autowired
    public EditResultService(@Qualifier("videoDao") EditResultDao editResultDao) {
        this.editResultDao = editResultDao;
    }

    public void editVideoResult(int objectId, String objectType, int objectTypeCode, int frameNumber, int newObjectId) {
        editResultDao.editVideoResult(objectId, objectType, objectTypeCode, frameNumber, newObjectId);
    }
}
