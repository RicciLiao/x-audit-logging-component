package ricciliao.x.log;

import ch.qos.logback.classic.pattern.ExtendedThrowableProxyConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.logstash.logback.composite.AbstractJsonProvider;
import org.apache.commons.lang3.StringUtils;
import ricciliao.x.component.utils.CoreUtils;
import ricciliao.x.log.common.AuditLogConstants;
import ricciliao.x.log.pojo.AuditLogBo;
import ricciliao.x.log.pojo.AuditLogDurationBo;

import java.io.IOException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.TimeZone;

public class AuditLogJsonProvider extends AbstractJsonProvider<ILoggingEvent> {

    private final ObjectMapper objectMapper;
    private final TypeReference<Map<String, String>> typeReference;
    private String consumer;
    private String version;

    public AuditLogJsonProvider() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.objectMapper.registerModule(new JavaTimeModule());

        this.typeReference = new TypeReference<>() {
        };
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setTimezone(String timezone) {
        this.objectMapper.setTimeZone(TimeZone.getTimeZone(timezone));
    }

    @Override
    public void writeTo(JsonGenerator jsonGenerator, ILoggingEvent iLoggingEvent) throws IOException {
        AuditLogBo bo;

        Map<String, String> mdc = iLoggingEvent.getMDCPropertyMap();
        if (StringUtils.isNotBlank(mdc.get(AuditLogConstants.DURATION_START_ON))
                && StringUtils.isNotBlank(mdc.get(AuditLogConstants.DURATION_END_ON))) {

            bo = new AuditLogDurationBo(
                    mdc.get(AuditLogConstants.TRANSACTION_ID),
                    mdc.get(AuditLogConstants.OPERATION),
                    Duration.between(
                            CoreUtils.toLocalDateTimeNotNull(Long.parseLong(mdc.get(AuditLogConstants.DURATION_START_ON))),
                            CoreUtils.toLocalDateTimeNotNull(Long.parseLong(mdc.get(AuditLogConstants.DURATION_END_ON)))
                    )
            );
        } else {
            bo = new AuditLogBo(mdc.get(AuditLogConstants.TRANSACTION_ID), mdc.get(AuditLogConstants.OPERATION));
        }
        bo.setConsumer(consumer);
        bo.setVersion(version);
        bo.setOn(OffsetDateTime.now());
        bo.setLevel(iLoggingEvent.getLevel().levelStr);
        bo.setLoggerName(iLoggingEvent.getLoggerName());
        if (iLoggingEvent.getThrowableProxy() != null) {
            ExtendedThrowableProxyConverter throwableProxyConverter = new ExtendedThrowableProxyConverter();
            throwableProxyConverter.start();
            bo.setStackTrace(iLoggingEvent.getFormattedMessage() + System.lineSeparator() + throwableProxyConverter.convert(iLoggingEvent));
            throwableProxyConverter.stop();
        }
        for (Map.Entry<String, String> entry : objectMapper.convertValue(bo, typeReference).entrySet()) {
            jsonGenerator.writeStringField(entry.getKey(), entry.getValue());
        }
    }

}
