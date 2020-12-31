package com.dare.videoQuery.leaseFileAPI;

import com.dare.videoQuery.dao.VideoAccessService;
import com.dare.videoQuery.service.LeaseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RequestMapping("leaseFileAPI/v1/videoObject")
@RestController
public class LeaseFileController {
    private final LeaseFileService leaseFileService;
    @Autowired
    public LeaseFileController(LeaseFileService leaseFileService) {
        this.leaseFileService = leaseFileService;
    }

    @GetMapping
    public List leaseVideo() {
        return leaseFileService.leaseVideo();
    }
}
