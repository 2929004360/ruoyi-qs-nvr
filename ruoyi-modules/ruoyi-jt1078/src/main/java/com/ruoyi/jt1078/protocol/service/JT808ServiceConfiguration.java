package com.ruoyi.jt1078.protocol.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@RequiredArgsConstructor
@EnableConfigurationProperties(JT808ServiceProperties.class)
@ConditionalOnProperty(prefix = "jt808-service", name = "base-url")
public class JT808ServiceConfiguration {

    private final JT808ServiceProperties jt808ServiceProperties;
    private final ObjectMapper objectMapper;

    @Bean
    public HttpServiceProxyFactory jt808ServiceFactory() {
        return HttpServiceProxyFactory.builder()
                .exchangeAdapter(WebClientAdapter.create(WebClient.builder()
                        .codecs(configurer -> {
                            configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
                            configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
                        })
                        .baseUrl(jt808ServiceProperties.baseUrl)
                        .build()
                )).build();
    }

    @Bean
    public JT808Service jt808Api() {
        return jt808ServiceFactory().createClient(JT808Service.class);
    }
}
