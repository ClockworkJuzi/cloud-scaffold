package pers.regulus.cloud.gate.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import pers.regulus.cloud.common.constant.CommonConstants;
import pers.regulus.cloud.common.jwt.IJwtInfo;
import pers.regulus.cloud.common.resp.Result;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Gateway 访问全局过滤器
 *
 * @author Regulus
 */
@Component
public class AccessGatewayFilter implements GlobalFilter, Ordered {

    private static final String GATEWAY_PREFIX = "/api";

    private static final String IGNORE_URLS_SPILT_CHAR = ",";

    @Value("${gate.ignore.uris}")
    private String uris;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        LinkedHashSet requiredAttributes = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);

        String requestUri = request.getPath().pathWithinApplication().value();

        if (requiredAttributes != null) {
            Iterator<URI> iter = requiredAttributes.iterator();
            while (iter.hasNext()) {
                URI next = iter.next();
                if (next.getPath().startsWith(GATEWAY_PREFIX)) {
                    requestUri = next.getPath().substring(GATEWAY_PREFIX.length());
                }
            }
        }

        /**
         * URI 白名单
         */
        ServerHttpRequest.Builder mutate = request.mutate();

        if (isStartWith(requestUri)) {
            ServerHttpRequest builder = mutate.build();
            return chain.filter(exchange.mutate().request(builder).build());
        }

        IJwtInfo loginUser;

        return null;
    }

    /**
     * 网关异常处理
     *
     * @param exchange
     * @param status
     * @param result
     * @return
     */
    private Mono<Void> exceptionHandler(ServerWebExchange exchange, HttpStatus status, Result result) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(status);

        byte[] bytes = JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        response.getHeaders().setContentLength(bytes.length);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }

    /**
     * URI 白名单校验
     *
     * @param requestUri
     * @return
     */
    private boolean isStartWith(String requestUri) {
        for (String s : uris.split(IGNORE_URLS_SPILT_CHAR)) {
            if (requestUri.startsWith(s)) return true;
        }
        return false;
    }

    private IJwtInfo getLoginUser(ServerHttpRequest request, ServerHttpRequest.Builder ctx) {
        List<String> strings = request.getHeaders().get(CommonConstants.JWT_KEY_TOKEN_HEADER);
        String authToken = null;
        if (!CollectionUtils.isEmpty(strings)) {
            authToken = strings.get(0);
        }

        if (StringUtils.isBlank(authToken)) {
            strings = request.getQueryParams().get("token");
            if (strings != null) {
                authToken = strings.get(0);
            }
        }

        return null;
    }

    @Override
    public int getOrder() {
        return -1000;
    }

}
