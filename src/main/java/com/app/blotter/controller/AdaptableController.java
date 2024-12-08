package com.app.blotter.controller;

import com.app.blotter.model.AdaptableRequest;
import com.app.blotter.model.AdaptableResponse;
import com.app.blotter.service.AdaptableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/adaptable")
public class AdaptableController {

    private final AdaptableService adaptableService;

    @PostMapping("/search")
    public AdaptableResponse search(@RequestBody AdaptableRequest request, @RequestParam String collectionName) {
        return adaptableService.getDocuments(request, collectionName);
    }
}

