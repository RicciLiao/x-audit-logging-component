package ricciliao.x.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ricciliao.x.log.logger.AuditLogger;

public final class AuditLoggerFactory {

    private AuditLoggerFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static AuditLogger getLogger(Class<?> clazz) {
        Logger logger = LoggerFactory.getLogger(clazz);

        return new AuditLogger(logger);
    }

}
