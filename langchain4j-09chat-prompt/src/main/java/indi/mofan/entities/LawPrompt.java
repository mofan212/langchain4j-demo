package indi.mofan.entities;


import dev.langchain4j.model.input.structured.StructuredPrompt;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mofan
 * @date 2025/7/17 12:28
 */
@Getter
@Setter
@StructuredPrompt("根据中国{{legal}}法律，解答以下问题: {{question}}")
public class LawPrompt {
    private String legal;
    private String question;
}
