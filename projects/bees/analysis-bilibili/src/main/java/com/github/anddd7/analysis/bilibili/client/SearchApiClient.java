package com.github.anddd7.analysis.bilibili.client;


import org.springframework.cloud.openfeign.FeignClient;

/**
 * 对 search api 的调用
 */
@FeignClient(value = "crawler-bilibili-search", path = "/${api.prefix}/${api.version}")
public interface SearchApiClient {

}

