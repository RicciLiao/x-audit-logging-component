package ricciliao.x.log.logger;

import org.slf4j.Logger;

public class AuditLogger extends LoggerDelegate {

    private final AuditDurationLogger auditDurationLogger;

    public AuditLogger(Logger delegate) {
        super(delegate);
        this.auditDurationLogger = new AuditDurationLogger(delegate);
    }

    public AuditDurationLogger duration() {

        return auditDurationLogger;
    }

}
