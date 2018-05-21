package com.github.anddd7.crawler.bilibili.exception.advice;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(NON_EMPTY)
public class ApiErrors {
    private List<ApiError> errors;
}
