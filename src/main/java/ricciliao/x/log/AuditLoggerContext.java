package ricciliao.x.log;

import org.slf4j.LoggerFactory;
import ricciliao.x.log.api.IXLoggerFactory;
import ricciliao.x.log.api.XLogger;
import ricciliao.x.log.logger.AuditLogger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class AuditLoggerContext implements IXLoggerFactory {

    private static final ConcurrentMap<String, AuditLogger> CACHE = new ConcurrentHashMap<>();

    @Override
    public XLogger getLogger(Class<?> clazz) {

        return CACHE.computeIfAbsent(clazz.getName(), s -> new AuditLogger(LoggerFactory.getLogger(clazz)));
    }

}
