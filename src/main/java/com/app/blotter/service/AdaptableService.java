package com.app.blotter.service;

import com.app.blotter.model.AdaptableRequest;
import com.app.blotter.model.AdaptableResponse;
import com.app.blotter.model.QueryWrapper;
import com.app.blotter.repository.GenericRepository;
import com.app.blotter.util.AdaptableQueryBuilderUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdaptableService {
    private final AdaptableQueryBuilderUtility adaptableQueryBuilderUtility;
    private final GenericRepository genericRepository;

    public AdaptableResponse getDocuments(AdaptableRequest request, String collectionName) {
        log.info("Fetching documents from collection: {}, request: {}", collectionName, request.toString());
        QueryWrapper queryWrapper = adaptableQueryBuilderUtility.buildQuery(request);
        List<Document> rows = genericRepository.findByRequest(queryWrapper.getQuery(), collectionName, request.getStartRow(), request.getEndRow());

        long count = genericRepository.count(queryWrapper.getBasicQuery(), collectionName);
        return AdaptableResponse.builder()
                .rows(rows)
                .count(count)
                .build();
    }
}