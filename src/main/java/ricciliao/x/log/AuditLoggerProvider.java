package ricciliao.x.log;

import ricciliao.x.log.api.IXLoggerFactory;
import ricciliao.x.log.api.XLoggerProvider;

public class AuditLoggerProvider implements XLoggerProvider {

    private final IXLoggerFactory xLoggerFactory;

    public AuditLoggerProvider() {
        this.xLoggerFactory = new AuditLoggerContext();
    }

    @Override
    public IXLoggerFactory getXLoggerFactory() {

        return xLoggerFactory;
    }

}
