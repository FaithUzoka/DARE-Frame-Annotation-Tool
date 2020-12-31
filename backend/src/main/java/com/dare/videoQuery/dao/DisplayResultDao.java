package com.dare.videoQuery.dao;

import com.dare.videoQuery.model.VideoObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

public interface DisplayResultDao {
    List getVideoResult() throws FileNotFoundException;
    List getCorrectedResult() throws FileNotFoundException;
    int createList(List objList) throws FileNotFoundException;
    List leaseVideo();
}
