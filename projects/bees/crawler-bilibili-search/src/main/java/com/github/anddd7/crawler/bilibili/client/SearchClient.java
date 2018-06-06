package com.github.anddd7.crawler.bilibili.client;

import com.github.anddd7.crawler.bilibili.client.response.SearchDataWrapper;
import feign.Param;
import feign.RequestLine;

public interface SearchClient {

  @RequestLine("GET")
  SearchDataWrapper search(
      @Param("main_ver") String mainVersion,
      @Param("search_type") String searchType,
      @Param("view_type") String viewType,
      @Param("pic_size") String pictureSize,
      @Param("order") String order,
      @Param("copy_right") int copyRight,
      @Param("pagesize") int pageSize,
      @Param("page") int pageNumber,
      @Param("cate_id") String categoryId,
      @Param("time_from") String dateFrom,
      @Param("time_to") String dateTo);
}
