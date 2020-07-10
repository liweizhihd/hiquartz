package weizhi.example.hiquartz;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OkHttpUtil {
    //第一步创建OKHttpClient
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public static String getString(String url, Map<String, String> header) {

        Request.Builder builder = new Request.Builder()
                .url(url)
                .get();
        if (header != null && header.size() > 0) {
            header.forEach(builder::header);
        }
        Request request = builder.build();
        //第四步创建call回调对象 & 第五步发起请求
        long start = System.currentTimeMillis();
        try (Response response = CLIENT.newCall(request).execute();) {
            String resultStr = Objects.requireNonNull(response.body()).string();
            long end = System.currentTimeMillis();

            return resultStr;
        } catch (IOException e) {
            log.error("error", e);
            return null;
        }
    }

    public static void main(String[] args) {
        String url = "http://42.120.7.54:8881/testing/restful";
        System.out.println(getString(url, null));


    }

    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
