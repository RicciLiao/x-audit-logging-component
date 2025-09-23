package ricciliao.x.log.logger;

import org.slf4j.Logger;
import ricciliao.x.log.api.XLogger;

public class AuditLogger extends LoggerDelegate implements XLogger {

    private final AuditDurationLogger auditDurationLogger;

    public AuditLogger(Logger delegate) {
        super(delegate);
        this.auditDurationLogger = new AuditDurationLogger(delegate);
    }

    @Override
    public AuditDurationLogger duration() {

        return auditDurationLogger;
    }

}
