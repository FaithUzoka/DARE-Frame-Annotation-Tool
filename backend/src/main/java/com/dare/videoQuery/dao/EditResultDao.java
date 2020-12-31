package com.dare.videoQuery.dao;

import com.dare.videoQuery.model.VideoObject;


public interface EditResultDao {
        int editVideoResult(int objectId, String objectType, int objectTypeCode, int frameNumber, int newObjectId);
}

