package indi.mofan.controller;


import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesis;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisParam;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisResult;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.output.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mofan
 * @date 2025/7/13 22:03
 */
@RestController
public class WanxImageModelController {
    @Autowired
    private WanxImageModel wanxImageModel;

    @GetMapping("/image/create")
    public String createImage() {
        Response<Image> response = wanxImageModel.generate("beautiful girl");
        return response.content().url().toString();
    }

    @GetMapping(value = "/image/create-complex-image")
    public String createImageContent3() {

        String prompt = "近景镜头，18岁的中国女孩，古代服饰，圆脸，正面看着镜头，" +
                        "民族优雅的服装，商业摄影，室外，电影级光照，半身特写，精致的淡妆，锐利的边缘。";
        ImageSynthesisParam param = ImageSynthesisParam.builder()
                .apiKey(System.getenv("AliQwen_Key"))
                .model(ImageSynthesis.Models.WANX_V1)
                .prompt(prompt)
                .style("<watercolor>")
                .n(1)
                .size("1024*1024")
                .build();

        ImageSynthesis imageSynthesis = new ImageSynthesis();
        ImageSynthesisResult result;

        try {
            result = imageSynthesis.call(param);
        } catch (ApiException | NoApiKeyException e) {
            throw new RuntimeException(e.getMessage());
        }

        return JsonUtils.toJson(result);
    }
}
