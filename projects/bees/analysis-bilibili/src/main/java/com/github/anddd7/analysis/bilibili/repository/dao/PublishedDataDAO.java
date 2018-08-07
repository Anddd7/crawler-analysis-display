package com.github.anddd7.analysis.bilibili.repository.dao;

import com.github.anddd7.model.bilibili.domain.PublishedData;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PublishedDataDAO extends MongoRepository<PublishedData, String> {

  List<PublishedData> findByRecordTimeOrderByCategoryId(String recordTime);
}
