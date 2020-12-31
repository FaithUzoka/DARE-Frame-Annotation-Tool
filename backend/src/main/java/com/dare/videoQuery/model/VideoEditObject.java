package com.dare.videoQuery.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VideoEditObject {
    private final int objectId;
    private final String objectType;
    private final int objectTypeCode;
    private final int frameNumber;
    private final int newObjectId;

    public VideoEditObject(@JsonProperty("objectId") int objectId, @JsonProperty("objectType") String objectType, @JsonProperty("objectTypeCode") int objectTypeCode, @JsonProperty("frameNumber") int frameNumber, @JsonProperty("newObjectId") int newObjectId){
        this.objectId = objectId;
        this.objectType = objectType;
        this.objectTypeCode = objectTypeCode;
        this.frameNumber = frameNumber;
        this.newObjectId = newObjectId;
    }

    public int getObjectId(){
        return objectId;
    }

    public String getObjectType(){
        return objectType;
    }

    public int getObjectTypeCode(){
        return objectTypeCode;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public int getNewObjectId(){
        return newObjectId;
    }
}
