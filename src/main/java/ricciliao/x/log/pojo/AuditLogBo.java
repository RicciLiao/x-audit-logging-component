package ricciliao.x.log.pojo;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

public class AuditLogBo extends MdcSupport implements Serializable {
    @Serial
    private static final long serialVersionUID = -1638646043735910085L;

    private OffsetDateTime on;
    private String version; // APP VERSION
    private String consumer;    // APP NAME
    private String level;
    private String loggerName;
    private String stackTrace;

    public AuditLogBo(String transactionId, String operation) {
        super(transactionId, operation);
    }

    public OffsetDateTime getOn() {
        return on;
    }

    public void setOn(OffsetDateTime on) {
        this.on = on;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuditLogBo that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getOn(), that.getOn()) && Objects.equals(getVersion(), that.getVersion()) && Objects.equals(getConsumer(), that.getConsumer()) && Objects.equals(getLevel(), that.getLevel()) && Objects.equals(getLoggerName(), that.getLoggerName()) && Objects.equals(getStackTrace(), that.getStackTrace());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getOn(), getVersion(), getConsumer(), getLevel(), getLoggerName(), getStackTrace());
    }
}
