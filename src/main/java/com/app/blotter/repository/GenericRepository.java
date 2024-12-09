package com.app.blotter.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class GenericRepository {

    private final MongoTemplate mongoTemplate;

    /**
     * Fetches documents from a collection based on a query.
     *
     * @param query          The query to filter documents.
     * @param collectionName The name of the collection.
     * @param startRow       The starting row for pagination.
     * @param endRow         The ending row for pagination.
     * @return A list of documents from the collection.
     */
    public List<Document> findByRequest(Query query, String collectionName, int startRow, int endRow) {
        Query q = Query.of(query);
        q.skip(startRow).limit(endRow - startRow);
        return mongoTemplate.find(q, Document.class, collectionName);
    }

    /**
     * Counts documents in a collection based on a query.
     *
     * @param query          The query to filter documents.
     * @param collectionName The name of the collection.
     * @return The count of matching documents.
     */
    public long count(Query query, String collectionName) {
        return mongoTemplate.count(query, collectionName);
    }
}