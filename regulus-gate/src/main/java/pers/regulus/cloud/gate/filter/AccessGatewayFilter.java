package pers.regulus.cloud.gate.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.LinkedHashSet;

/**
 * Gateway 访问全局过滤器
 *
 * @author Regulus
 */
@Component
public class AccessGatewayFilter implements GlobalFilter, Ordered {

    private static final String GATEWAY_PREFIX = "/api";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        LinkedHashSet requiredAttributes = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);

        String requestUri = request.getPath().pathWithinApplication().value();

        return null;
    }

    @Override
    public int getOrder() {
        return -1000;
    }

}
