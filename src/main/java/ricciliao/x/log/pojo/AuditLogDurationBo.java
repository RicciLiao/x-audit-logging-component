package ricciliao.x.log.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ricciliao.x.log.Duration2MillisSerializer;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;

public class AuditLogDurationBo extends AuditLogBo implements Serializable {
    @Serial
    private static final long serialVersionUID = -3767553470513553137L;

    @JsonSerialize(using = Duration2MillisSerializer.class)
    private Duration durationMillis;

    public AuditLogDurationBo(String transactionId,
                              String operation,
                              Duration durationMillis) {
        super(transactionId, operation);
        this.durationMillis = durationMillis;
    }

    public Duration getDurationMillis() {
        return durationMillis;
    }

    public void setDurationMillis(Duration durationMillis) {
        this.durationMillis = durationMillis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuditLogDurationBo that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getDurationMillis(), that.getDurationMillis());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDurationMillis());
    }
}
