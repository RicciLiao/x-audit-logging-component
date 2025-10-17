package ricciliao.x.log.logger;

import org.slf4j.Logger;
import org.slf4j.MDC;
import ricciliao.x.log.api.XDurationLogger;
import ricciliao.x.log.common.AuditLogConstants;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class AuditDurationLogger extends LoggerDelegate implements XDurationLogger {
    private final Map<Long, Deque<Instant>> thread2StartOnMap = new HashMap<>();

    public AuditDurationLogger(Logger delegate) {
        super(delegate);
    }

    public Map<Long, Deque<Instant>> getThread2StartOnMap() {
        return thread2StartOnMap;
    }

    @Override
    public Logger start() {
        Instant now = Instant.now();
        if (thread2StartOnMap.containsKey(Thread.currentThread().threadId())) {
            thread2StartOnMap.get(Thread.currentThread().threadId()).push(now);
        } else {
            ArrayDeque<Instant> deque = new ArrayDeque<>();
            deque.push(now);
            thread2StartOnMap.put(Thread.currentThread().threadId(), deque);
        }

        return this.getDelegate();
    }

    @Override
    public Logger stop() {
        Instant startOn;
        Instant now = Instant.now();
        if (thread2StartOnMap.containsKey(Thread.currentThread().threadId())
                && !thread2StartOnMap.get(Thread.currentThread().threadId()).isEmpty()) {
            startOn = thread2StartOnMap.get(Thread.currentThread().threadId()).pop();
        } else {
            startOn = now;
        }
        MDC.put(AuditLogConstants.DURATION_START_ON, String.valueOf(startOn.toEpochMilli()));
        MDC.put(AuditLogConstants.DURATION_END_ON, String.valueOf(now.toEpochMilli()));
        this.clear();

        return this.getDelegate();
    }

    public void clear() {
        thread2StartOnMap.remove(Thread.currentThread().threadId());
    }

}
