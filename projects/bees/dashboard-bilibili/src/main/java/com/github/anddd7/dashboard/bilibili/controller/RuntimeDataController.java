package com.github.anddd7.dashboard.bilibili.controller;

import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping(value = "/${api.prefix}/${api.version}/runtime", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RuntimeDataController {

  // 直接调用search api的实时接口
}
