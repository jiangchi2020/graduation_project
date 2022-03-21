package com.scarike.gp.web.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.scarike.gp.web.common.http.SimpleResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class SentinelConfiguration {

    /**
     * 自定义拦截后的处理器，
     * 有两种方式，自动配置类见com.alibaba.cloud.sentinel.gateway.scg.SentinelSCGAutoConfiguration
     * 1. 类GatewayCallbackManager负责管理BlockHandler，使用他的setBlockHandler静态方法即可设置想要的handle
     * 2. 在配置类SentinelSCGAutoConfiguration中，如果存在bean：BlockRequestHandler则会帮你调用GatewayCallbackManager.setBlockHandler方法，所以创建一个BlockRequestHandler的bean亦可配置进去
     * 两种方法的优先级取决于自己写的GatewayCallbackManager.setBlockHandler和配置类SentinelSCGAutoConfiguration的初始化谁先发生
     */

//    @PostConstruct
//    public void initBlockHandler(){
//        GatewayCallbackManager.setBlockHandler((exchange, t) -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(SimpleResponse.fail("TOO_MANY_REQUEST")));
//    }

    @Bean
    public BlockRequestHandler blockRequestHandler() {
        return (exchange, t) -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(SimpleResponse.fail("TOO_MANY_REQUEST"));
    }

}
