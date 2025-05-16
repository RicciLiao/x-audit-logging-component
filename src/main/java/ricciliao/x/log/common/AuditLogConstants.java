package ricciliao.x.log.common;

public class AuditLogConstants {

    private AuditLogConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String TRANSACTION_ID = "transactionId";
    public static final String OPERATION = "operation";
    public static final String DURATION_START_ON = "startOn";
    public static final String DURATION_END_ON = "endOn";

}
