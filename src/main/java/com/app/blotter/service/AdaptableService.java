package com.app.blotter.service;

import com.app.blotter.model.AdaptableRequest;
import com.app.blotter.model.AdaptableResponse;
import com.app.blotter.repository.GenericRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.app.blotter.builders.AdaptableQueryBuilderUtility.buildQuery;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdaptableService {
    private final GenericRepository genericRepository;

    public AdaptableResponse getDocuments(AdaptableRequest request, String collectionName) {
        log.info("Fetching documents from collection: {}, request: {}", collectionName, request.toString());
        Query query = buildQuery(request);
        List<Document> rows = genericRepository.findByRequest(query, collectionName, request.getStartRow(), request.getEndRow());

        long count = genericRepository.count(query, collectionName);
        return AdaptableResponse.builder().rows(rows).count(count).build();
    }
}