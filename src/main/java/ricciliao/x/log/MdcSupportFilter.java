package ricciliao.x.log;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import ricciliao.x.component.servlet.ContentCachingFilter;
import ricciliao.x.log.common.AuditLogConstants;
import ricciliao.x.log.common.AuditLogHelper;

import java.io.IOException;

public class MdcSupportFilter extends ContentCachingFilter {

    @Override
    public void doFilter(ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper, FilterChain chain) throws ServletException, IOException {
        if (StringUtils.isBlank(MDC.get(AuditLogConstants.TRANSACTION_ID))
                || StringUtils.isBlank(MDC.get(AuditLogConstants.OPERATION))) {
            MDC.put(AuditLogConstants.TRANSACTION_ID, AuditLogHelper.uniqueId());
            MDC.put(AuditLogConstants.OPERATION, requestWrapper.getRequestURI());
        }
        chain.doFilter(requestWrapper, responseWrapper);
    }

}
