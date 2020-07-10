package weizhi.example.hiquartz;

import okhttp3.*;

import java.io.IOException;

/**
 * @author liweizhi
 * @date 2020/6/29
 */
public class Mytest {
    static OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    static String getUrl(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    static String postUrl(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    static String putUrl(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    static String deleteUrl(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .delete(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static void main(String[] args) throws IOException {
        String url = "http://42.120.7.54:8881/testing/restful";
        System.out.println("get request result: \n" + getUrl(url));
        System.out.println("post request result: \n" + postUrl(url, ""));
        System.out.println("put request result: \n" + putUrl(url, ""));
        System.out.println("delete request result: \n" + deleteUrl(url, ""));
    }
}
