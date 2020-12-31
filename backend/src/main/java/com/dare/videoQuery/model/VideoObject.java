package com.dare.videoQuery.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VideoObject {
    private int objectTypeCode;
    private int objectId;
    private final double score;
    private String objectType;
    private final int[] coordinates;
    private final int frameNumber;

    public VideoObject(@JsonProperty("objectId") int objectId, @JsonProperty("objectType") String objectType, @JsonProperty("objectTypeCode") int objectTypeCode, @JsonProperty("frameNumber") int frameNumber, int[] coordinates, double score) {
        this.objectTypeCode = objectTypeCode;
        this.objectType = objectType;
        this.objectId = objectId;
        this.frameNumber = frameNumber;
        this.coordinates = coordinates;
        this.score = score;
    }

    public int getObjectTypeCode() {
        return objectTypeCode;
    }

    public int getObjectId(){
        return objectId;
    }

    public void setObjectId(int id){
        objectId = id;
    }

    public String getObjectType(){
        return objectType;
    }

    public int[] getCoordinates(){
        return coordinates;
    }

    public double getScore(){
        return score;
    }

    public void setObjectType(String type){
        objectType = type;
    }

    public void setObjectTypeCode(int typeCode){
        objectTypeCode = typeCode;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

}
