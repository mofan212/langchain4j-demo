package indi.mofan.controller;


import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.filter.MetadataFilterBuilder;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mofan
 * @date 2025/7/19 22:50
 */
@RestController
public class EmbeddingController {
    @Resource
    private EmbeddingModel embeddingModel;
    @Resource
    private QdrantClient qdrantClient;
    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;

    /**
     * 文本向量化测试，查看形成向量后的文本
     */
    @GetMapping("/embedding/embed")
    public String embed() {
        String prompt = """
                   咏鸡
                鸡鸣破晓光，
                红冠映朝阳。
                金羽披霞彩，
                昂首步高岗。
                """;
        Response<Embedding> embeddingResponse = embeddingModel.embed(prompt);
        return embeddingResponse.content().toString();
    }

    /**
     * 新增向量数据库实例和创建索引：test-qdrant
     * 类似于 MySQL `CREATE DATABASE test-qdrant`
     */
    @GetMapping(value = "/embedding/create-collection")
    public void createCollection() {
        var vectorParams = Collections.VectorParams.newBuilder()
                .setDistance(Collections.Distance.Cosine)
                .setSize(1024)
                .build();
        qdrantClient.createCollectionAsync("test-qdrant", vectorParams);
    }

    /**
     * 往向量数据库新增文本记录
     */
    @GetMapping(value = "/embedding/add")
    public String add() {
        String prompt = """
                   咏鸡
                鸡鸣破晓光，
                红冠映朝阳。
                金羽披霞彩，
                昂首步高岗。
                """;
        TextSegment segment = TextSegment.from(prompt);
        segment.metadata().put("author", "mofan");
        Embedding embedding = embeddingModel.embed(segment).content();
        return embeddingStore.add(embedding, segment);
    }

    @GetMapping(value = "/embedding/query1")
    public String query1() {
        Embedding queryEmbedding = embeddingModel.embed("咏鸡说的是什么").content();
        EmbeddingSearchRequest req = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .maxResults(1)
                .build();
        EmbeddingSearchResult<TextSegment> result = embeddingStore.search(req);
        return result.matches().getFirst().embedded().text();
    }

    @GetMapping(value = "/embedding/query2")
    public String query2() {
        Embedding queryEmbedding = embeddingModel.embed("咏鸡").content();
        EmbeddingSearchRequest req = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .filter(MetadataFilterBuilder.metadataKey("author").isEqualTo("mofan212"))
                .maxResults(1)
                .build();
        EmbeddingSearchResult<TextSegment> result = embeddingStore.search(req);
        List<EmbeddingMatch<TextSegment>> list = result.matches();
        if (list.isEmpty()) {
            return "未查询到指定内容";
        }
        return list.getFirst().embedded().text();
    }
}
