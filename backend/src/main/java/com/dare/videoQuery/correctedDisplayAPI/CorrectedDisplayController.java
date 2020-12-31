package com.dare.videoQuery.correctedDisplayAPI;
import com.dare.videoQuery.service.CorrectedDisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RequestMapping("correctedDisplayAPI/v1/videoObject")
@RestController
public class CorrectedDisplayController {
    private final CorrectedDisplayService correctedDisplayService;
    @Autowired
    public CorrectedDisplayController(CorrectedDisplayService correctedDisplayService) {
        this.correctedDisplayService = correctedDisplayService;
    }


    @GetMapping
    public List getCorrectedResult() throws FileNotFoundException {
        return CorrectedDisplayService.getCorrectedResult();
    }

}
