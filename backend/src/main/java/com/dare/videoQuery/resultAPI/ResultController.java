package com.dare.videoQuery.resultAPI;

import com.dare.videoQuery.service.DisplayResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RequestMapping("resultAPI/v1/videoObject")
@RestController
public class ResultController {
    private final DisplayResultService displayResultService;

    @Autowired
    public ResultController(DisplayResultService displayResultService) {
        this.displayResultService = displayResultService;
    }

    @GetMapping
    public List getVideoResult() throws FileNotFoundException {
        return displayResultService.getVideoResult();
    }

}
