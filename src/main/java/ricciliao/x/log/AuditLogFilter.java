package ricciliao.x.log;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.MDC;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import ricciliao.x.component.servlet.ContentCachingFilter;
import ricciliao.x.log.common.AuditLogConstants;
import ricciliao.x.log.common.AuditLogHelper;
import ricciliao.x.log.logger.AuditLogger;

import java.io.IOException;

public class AuditLogFilter extends ContentCachingFilter {

    private static final AuditLogger logger = AuditLoggerFactory.getLogger(AuditLogFilter.class);

    @Override
    public void doFilter(ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper, FilterChain chain) throws ServletException, IOException {
        if (MapUtils.isNotEmpty(logger.duration().getThread2StartOnMap())) {
            logger.duration().clear();
        }
        MDC.put(AuditLogConstants.TRANSACTION_ID, AuditLogHelper.uniqueId());
        MDC.put(AuditLogConstants.OPERATION, requestWrapper.getRequestURI());

        logger.duration().start().info("AuditLogFilter started");

        try {
            chain.doFilter(requestWrapper, responseWrapper);
        } finally {
            logger.duration().stop().info("AuditLogFilter end");
        }
    }

}
