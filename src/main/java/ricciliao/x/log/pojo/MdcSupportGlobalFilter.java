package ricciliao.x.log.pojo;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ricciliao.x.log.common.AuditLogConstants;
import ricciliao.x.log.common.AuditLogHelper;

import java.util.List;

public class MdcSupportGlobalFilter implements GlobalFilter, Ordered {

    private final Integer order;

    public MdcSupportGlobalFilter(Integer order) {
        super();

        this.order = order;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        MDC.clear();

        ServerHttpRequest request = exchange.getRequest();
        List<String> transactionList = request.getHeaders().get(AuditLogConstants.TRANSACTION_ID);
        if (CollectionUtils.isNotEmpty(transactionList)) {
            MDC.put(AuditLogConstants.TRANSACTION_ID, transactionList.get(0));
        } else {
            MDC.put(AuditLogConstants.TRANSACTION_ID, AuditLogHelper.uniqueId());
        }
        MDC.put(AuditLogConstants.OPERATION, request.getURI().getPath());
        ServerWebExchange.Builder mutateBuilder = exchange.mutate().request(
                exchange.getRequest().mutate()
                        .header(AuditLogConstants.TRANSACTION_ID, MDC.get(AuditLogConstants.TRANSACTION_ID))
                        .build()
        );

        return chain.filter(mutateBuilder.build());
    }

    @Override
    public int getOrder() {

        return order;
    }
}
