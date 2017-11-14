package github.eddy.common;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HttpTools {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient httpClient;

    /*---------------------------------------------------------------------------------*/
    private static HttpTools instance;

    public static HttpTools getInstance() {
        if (instance == null) {
            instance = new HttpTools();
        }
        return instance;
    }

    /*---------------------------------------------------------------------------------*/
    public HttpTools() {
        httpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public String get(String url) throws IOException {
        //创建一个Request
        Request request = new Request.Builder()
                .url(url)
                .build();
        log.info("发送HTTP GET: to [{}]", url);
        Response response = send(request);
        return response.body().string();
    }

    public String post(String url, String json) throws IOException {
        return post(url, JSON, json);
    }

    public String post(String url, MediaType mediaType, String content) throws IOException {
        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        log.info("发送HTTP POST: to [{}] \n {}", url, body);
        Response response = send(request);
        return response.body().string();
    }

    private Response send(Request request) throws IOException {
        Instant start = Instant.now();

        Response response = httpClient.newCall(request).execute();

        log.info("Http Response in [{}]ms : code={} message={}",
                Duration.between(start, Instant.now()).toMillis(), response.code(), response.message());

        if (response.isSuccessful()) {
            return response;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
}
