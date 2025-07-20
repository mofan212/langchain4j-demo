package indi.mofan.controller;


import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolProvider;
import indi.mofan.service.McpService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

/**
 * @author mofan
 * @date 2025/7/20 23:02
 */
@RestController
public class McpCallServerController {
    @Resource
    private StreamingChatModel streamingChatModel;

    @GetMapping("/mcp/chat")
    public Flux<String> chat(@RequestParam("question") String question) throws Exception {
        /*
         * 1. 构建 McpTransport 协议
         * 1.1 cmd：启动 Windows 命令行解释器。
         * 1.2 /c：告诉 cmd 执行完后面的命令后关闭自身。
         * 1.3 npx：npx = npm execute package，Node.js 的一个工具，用于执行 npm 包中的可执行文件。
         * 1.4 -y 或 --yes：自动确认操作（类似于默认接受所有提示）
         * 1.5 @baidumap/mcp-server-baidu-map：要通过 npx 执行的 npm 包名
         * 1.6 BAIDU_MAP_API_KEY 是访问百度地图开放平台 API 的 AK
         *
         * 先尝试在 cmd 中执行 `npx -y @baidumap/mcp-server-baidu-map` 命令，查看 MCP 服务端是否能够正常启动
         * 如果不能正常启动，需要查看错误信息并解决。
         * 我在此遇到问题，错误信息是：请求 npm 淘宝源下的 baidu map mcp server 失败，原因是证书过期
         * 于是我将 npm 镜像源又切回默认，然后再尝试执行，提示 `Baidu Map MCP Server running on stdio`，正常启动 MCP 服务端
         */
        McpTransport transport = new StdioMcpTransport.Builder()
                .command(List.of("cmd", "/c", "npx", "-y", "@baidumap/mcp-server-baidu-map"))
                .environment(Map.of("BAIDU_MAP_API_KEY", System.getenv("BAIDU_MAP_API_KEY")))
                .build();

        // 2. 构建 McpClient 客户端
        McpClient mcpClient = new DefaultMcpClient.Builder()
                .transport(transport)
                .build();

        // 3. 创建工具集和原生的 FunctionCalling 类似
        ToolProvider toolProvider = McpToolProvider.builder()
                .mcpClients(mcpClient)
                .build();

        // 4. 通过 AiServices 给自定义接口 McpService 构建实现类并将工具集和大模型赋值给 AiService
        McpService mcpService = AiServices.builder(McpService.class)
                .streamingChatModel(streamingChatModel)
                .toolProvider(toolProvider)
                .build();

        // 5.调用定义的接口，通过大模型对百度 MCP Server 进行调用
        try {
            return mcpService.chat(question);
        } finally {
            mcpClient.close();
        }
    }
}
