package ricciliao.x.log.common;

import ricciliao.x.component.random.RandomGenerator;

public class AuditLogHelper {

    private AuditLogHelper() {
        throw new IllegalStateException("Utility class");
    }


    public static String uniqueId() {

        return RandomGenerator.nextString(16).allAtLeast(5).clear(true).generate();
    }

}
