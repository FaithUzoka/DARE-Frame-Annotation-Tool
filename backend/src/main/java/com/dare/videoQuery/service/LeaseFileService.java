package com.dare.videoQuery.service;

import com.dare.videoQuery.dao.DisplayResultDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class LeaseFileService {
    private final DisplayResultDao displayResultDao;
    private int count = 0;

   @Autowired
    public LeaseFileService(DisplayResultDao displayResultDao) {
        this.displayResultDao = displayResultDao;
    }
    public List leaseVideo(){
        return displayResultDao.leaseVideo();
    }
}
