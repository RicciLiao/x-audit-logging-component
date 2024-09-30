package ricciliao.audit.log;

import ch.qos.logback.classic.pattern.ExtendedThrowableProxyConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuditLogLayout extends LayoutBase<ILoggingEvent> {

    @Override
    public String doLayout(ILoggingEvent iLoggingEvent) {

        AuditLogBo bo = new AuditLogBo();
        bo.setTransactionId("setTransactionId");
        bo.setLevel(iLoggingEvent.getLevel().toString());
        bo.setVersion("setVersion");
        bo.setMessage(iLoggingEvent.getMessage());
        bo.setLoggerName("setLoggerName");
        bo.setTargetName("setTargetName");
        bo.setOperation("setOperation");
        if(iLoggingEvent.getThrowableProxy() != null) {
            ExtendedThrowableProxyConverter throwableProxyConverter = new ExtendedThrowableProxyConverter();
            throwableProxyConverter.start();
            bo.setStackTrace(iLoggingEvent.getFormattedMessage() + "\n" + throwableProxyConverter.convert(iLoggingEvent));
            throwableProxyConverter.stop();
        }
        try {
            return new ObjectMapper().writeValueAsString(bo) + System.lineSeparator();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
