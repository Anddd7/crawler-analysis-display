package com.github.anddd7.boot.logging;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@UtilityClass
class LoggingFormatter {

  private static final List<MediaType> VISIBLE_TYPES = Arrays.asList(
      MediaType.valueOf("text/*"),
      MediaType.APPLICATION_FORM_URLENCODED,
      MediaType.APPLICATION_JSON,
      MediaType.APPLICATION_XML,
      MediaType.valueOf("application/*+json"),
      MediaType.valueOf("application/*+xml"),
      MediaType.MULTIPART_FORM_DATA
  );

  private static final String LINE_END_REGEX = "\r\n|\r|\n";

  static ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
    if (request instanceof ContentCachingRequestWrapper) {
      return (ContentCachingRequestWrapper) request;
    } else {
      return new ContentCachingRequestWrapper(request);
    }
  }

  static ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
    if (response instanceof ContentCachingResponseWrapper) {
      return (ContentCachingResponseWrapper) response;
    } else {
      return new ContentCachingResponseWrapper(response);
    }
  }

  static void logRequest(Logger log, ContentCachingRequestWrapper request) {
    String prefix = request.getRemoteAddr() + "|>";

    logRequestURI(log, prefix, request);
    logRequestHeader(log, prefix, request);
    logRequestBody(log, prefix, request);
  }

  private static void logRequestURI(Logger log, String prefix,
      ContentCachingRequestWrapper request) {
    val queryString = request.getQueryString();
    if (queryString == null) {
      log.info("{} {} {}", prefix, request.getMethod(), request.getRequestURI());
    } else {
      log.info("{} {} {}?{}", prefix, request.getMethod(), request.getRequestURI(), queryString);
    }
  }

  private static void logRequestHeader(Logger log, String prefix,
      ContentCachingRequestWrapper request) {
    Collections.list(request.getHeaderNames())
        .forEach(headerName ->
            Collections.list(request.getHeaders(headerName))
                .forEach(headerValue -> log.info("{} {}: {}", prefix, headerName, headerValue))
        );
  }

  private static void logRequestBody(Logger log, String prefix,
      ContentCachingRequestWrapper request) {
    val content = request.getContentAsByteArray();
    if (content.length > 0) {
      logContent(log, prefix, content, request.getContentType(), request.getCharacterEncoding());
    }
  }

  static void logResponse(Logger log, ContentCachingRequestWrapper request,
      ContentCachingResponseWrapper response) {
    String prefix = request.getRemoteAddr() + "|<";

    logResponseHeader(log, prefix, response);
    logResponseBody(log, prefix, response);
  }

  private static void logResponseHeader(Logger log, String prefix,
      ContentCachingResponseWrapper response) {
    val status = response.getStatus();
    log.info("{} {} {}", prefix, status, HttpStatus.valueOf(status).getReasonPhrase());
    response.getHeaderNames()
        .forEach(headerName -> response.getHeaders(headerName)
            .forEach(headerValue -> log.info("{} {}: {}", prefix, headerName, headerValue))
        );
    log.info("{}", prefix);
  }

  private static void logResponseBody(Logger log, String prefix,
      ContentCachingResponseWrapper response) {
    val content = response.getContentAsByteArray();
    if (content.length > 0) {
      logContent(log, prefix, content, response.getContentType(), response.getCharacterEncoding());
    }
  }

  private static void logContent(Logger log, String prefix, byte[] content, String contentType,
      String contentEncoding) {
    val mediaType = MediaType.valueOf(contentType);
    val visible = VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
    if (visible) {
      try {
        for (String line : new String(content, contentEncoding).split(LINE_END_REGEX)) {
          log.info("{} {}", prefix, line);
        }
      } catch (UnsupportedEncodingException e) {
        log.info("{} [{} bytes content]", prefix, content.length);
      }
    } else {
      log.info("{} [{} bytes content]", prefix, content.length);
    }
  }
}
