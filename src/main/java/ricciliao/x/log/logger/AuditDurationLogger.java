package ricciliao.x.log.logger;

import org.slf4j.Logger;
import org.slf4j.MDC;
import ricciliao.x.component.utils.CoreUtils;
import ricciliao.x.log.common.AuditLogConstants;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class AuditDurationLogger extends LoggerDelegate {
    private final Map<Long, Deque<LocalDateTime>> thread2StartOnMap = new HashMap<>();

    public AuditDurationLogger(Logger delegate) {
        super(delegate);
    }

    public Map<Long, Deque<LocalDateTime>> getThread2StartOnMap() {
        return thread2StartOnMap;
    }

    public Logger start() {
        LocalDateTime now = LocalDateTime.now();
        if (thread2StartOnMap.containsKey(Thread.currentThread().threadId())) {
            thread2StartOnMap.get(Thread.currentThread().threadId()).push(now);
        } else {
            ArrayDeque<LocalDateTime> deque = new ArrayDeque<>();
            deque.push(now);
            thread2StartOnMap.put(Thread.currentThread().threadId(), deque);
        }

        return this.getDelegate();
    }

    public Logger stop() {
        LocalDateTime startOn;
        LocalDateTime now = LocalDateTime.now();
        if (thread2StartOnMap.containsKey(Thread.currentThread().threadId())
                && !thread2StartOnMap.get(Thread.currentThread().threadId()).isEmpty()) {
            startOn = thread2StartOnMap.get(Thread.currentThread().threadId()).pop();
        } else {
            startOn = now;
        }
        MDC.put(AuditLogConstants.DURATION_START_ON, String.valueOf(CoreUtils.toLong(startOn)));
        MDC.put(AuditLogConstants.DURATION_END_ON, String.valueOf(CoreUtils.toLong(now)));
        this.clear();

        return this.getDelegate();
    }

    public void clear() {
        thread2StartOnMap.remove(Thread.currentThread().threadId());
    }

}
