package indi.mofan.tool;


import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * @author mofan
 * @date 2025/7/19 16:44
 */
public class WeatherTools {

    /**
     * 一个免费、免注册的天气 API，有使用频率限制，但测试够了
     */
    private static final String BASE_URL = "https://cn.apihz.cn/api/tianqi/tqyb.php?id=88888888&key=88888888&sheng=%s&place=%s";

    @Tool("返回给定省、市的天气预报")
    public String getWeather(@P("省") String sheng,
                             @P("市") String place) {
        return getRealWeather(sheng, place);
    }

    private String getRealWeather(String sheng, String place) {
        String url = String.format(BASE_URL, sheng, place);
        // 用 JDK 自带的
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        String weather = null;
        try (HttpClient client = HttpClient
                .newBuilder()
                .connectTimeout(Duration.ofMillis(500))
                .build()) {
            HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());
            ///  {
            ///     "precipitation": 0,
            ///     "temperature": 31.8,
            ///     "pressure": 945,
            ///     "humidity": 67,
            ///     "windDirection": "东南风",
            ///     "windDirectionDegree": 114,
            ///     "windSpeed": 0.4,
            ///     "windScale": "微风",
            ///     "feelst": 37.3,
            ///     "code": 200,
            ///     "place": "中国, 四川, 成都",
            ///     "weather1": "阵雨",
            ///     "weather2": "阵雨",
            ///     "weather1img": "https://rescdn.apihz.cn/resimg/tianqi/zhenyu.png",
            ///     "weather2img": "https://rescdn.apihz.cn/resimg/tianqi/zhenyu.png",
            ///     "uptime": "2025/07/19 16:20",
            ///     "jieqi": ""
            /// }
            weather = resp.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return weather;
    }
}
