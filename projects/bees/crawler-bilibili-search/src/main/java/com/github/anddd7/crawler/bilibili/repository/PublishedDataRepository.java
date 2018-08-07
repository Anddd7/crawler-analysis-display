package com.github.anddd7.crawler.bilibili.repository;

import com.github.anddd7.model.bilibili.domain.PublishedData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PublishedDataRepository extends MongoRepository<PublishedData, String> {

}
