package cn.daijc.filter;

import cn.daijc.enums.RequestHeaderEnum;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

/**
 * @author djc
 * @Description
 * @create 2024-06-07 17:50
 */
@Component
public class MetadataRouteFilter extends AbstractGatewayFilterFactory<MetadataRouteFilter.Config> {

    public MetadataRouteFilter() {
        super(Config.class);
    }

    public static class Config {
        // 配置类，如有需要可以添加属性
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String serviceGroup = request.getHeaders().getFirst(RequestHeaderEnum.X_SERVICE_GROUP.header());

            return chain.filter(exchange).contextWrite(ctx -> {
                if (serviceGroup != null) {
                    ctx = ctx.put(RequestHeaderEnum.X_SERVICE_GROUP.header(), serviceGroup);
                }
                return ctx;
            });
        };
    }
}
