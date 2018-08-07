package com.github.anddd7.crawler.bilibili.repository;

import com.github.anddd7.crawler.bilibili.client.response.RankData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RankRepository extends MongoRepository<RankData, String> {

}
