package com.dare.videoQuery.service;

import com.dare.videoQuery.dao.DisplayResultDao;
import com.dare.videoQuery.model.VideoObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DisplayResultService {
    private final DisplayResultDao displayResultDao;
    public static List<VideoObject> videoObjectArrayList = new ArrayList<>();


    @Autowired
    public DisplayResultService(@Qualifier("videoDao") DisplayResultDao displayResultDao) {
        this.displayResultDao = displayResultDao;

    }


    public List getVideoResult() throws FileNotFoundException {
            return displayResultDao.getVideoResult();
        }
}


