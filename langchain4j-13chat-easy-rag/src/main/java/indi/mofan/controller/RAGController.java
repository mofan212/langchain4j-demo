package indi.mofan.controller;


import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.UrlDocumentLoader;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import indi.mofan.service.ChatAssistant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URI;

/**
 * @author mofan
 * @date 2025/7/20 21:06
 */
@RestController
public class RAGController {

    @Resource
    private InMemoryEmbeddingStore<TextSegment> embeddingStore;

    @Resource
    private ChatAssistant chatAssistant;

    @GetMapping("/rag/add")
    public String test() throws MalformedURLException {
        // about me
        URI uri = URI.create("https://mofan212.github.io/mine/");
        Document document = UrlDocumentLoader.load(
                uri.toURL(),
                new ApacheTikaDocumentParser()
        );
        EmbeddingStoreIngestor.ingest(document, embeddingStore);
        return chatAssistant.chat("如何联系默烦？");
    }
}
