# X-Component

## *Audit Log `🚀️ V1.0.0`*

### 📚 Dependency

Please refer to dependencies-control-center for the version number.

| groupId                  | artifactId                     | scope    | optional |
|--------------------------|--------------------------------|----------|----------|
| ch.qos.logback           | logback-classic                | compile  | false    |
| net.logstash.logback     | logstash-logback-encoder       | compile  | false    |
| ricciliao.x              | common-components              | compile  | true     |
| org.springframework.boot | spring-boot-starter-web        | provided | false    |
| jakarta.servlet          | jakarta.servlet-ap             | provided | false    |
| org.springframework.boot | spring-boot-starter-validation | provided | false    |

### 📌 Usage

**Audit Log** is a logger framework, it follows the SLF4J specification. The framework uses **MDC** to implement log
tracking, also, it can integrate **ELK** too.

- **Log Fields**

| Name           | Type                     | Value                                 | optional |
|----------------|--------------------------|---------------------------------------|----------|
| on             | java.time.OffsetDateTime | current date time                     | false    |
| version        | java.lang.String         | property `ricciliao.x.common.version` | false    |
| consumer       | java.lang.String         | property `spring.application.name`    | true     |
| level          | java.lang.String         | ch.qos.logback.classic.Level          | false    |
| loggerName     | java.lang.String         | class name                            | false    |
| stackTrace     | java.lang.String         | exception stacks                      | true     |
| version        | java.lang.String         | property `ricciliao.x.common.version` | false    |
| transactionId  | java.lang.String         | unique tracing id                     | false    |
| operation      | java.lang.String         | API path                              | false    |
| durationMillis | java.time.Duration       | milliseconds                          | true     |

If you want to integrate your **logstash**, please add an environment property `LOGSTASH_URL`.

### 📝 Coding

- **AuditLogger.class**

```java
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
```

You can define the logger just like define a SLF4J logger.

for example:

```java
public class AuditLogFilter extends ContentCachingFilter {

    private static final AuditLogger logger = AuditLoggerFactory.getLogger(AuditLogFilter.class);

    @Override
    public void doFilter(ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper, FilterChain chain) throws ServletException, IOException {
        try {
            logger.info("...");
            logger.warn("...");
        } catch (Exception ex) {
            logger.error("...", ex);
        }
    }

}
```

---

- **AuditDurationLogger.class**

```java
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
```

This logger can record the time taken of code execution.

for example:

```java
public class AuditLogFilter extends ContentCachingFilter {

    private static final AuditLogger logger = AuditLoggerFactory.getLogger(AuditLogFilter.class);

    @Override
    public void doFilter(ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper, FilterChain chain) throws ServletException, IOException {
        logger.duration().start().info("AuditLogFilter started");
        try {
            chain.doFilter(requestWrapper, responseWrapper);
        } finally {
            logger.duration().stop().info("AuditLogFilter end");
        }
    }

}
```

---

- As we know **MDC** will lose its context in multiple threads, but don't about that!
  The framework has some resolutions to handle this.
- ***MdcConcurrentUtils.class*

```java
public class MdcConcurrentUtils {

    private MdcConcurrentUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Runnable wrap(Runnable runnable) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();

        return () -> {
            MDC.setContextMap(contextMap);
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }

    public static <T> Callable<T> wrap(Callable<T> callable) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();

        return () -> {
            MDC.setContextMap(contextMap);
            try {
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    public static <T> Supplier<T> wrap(Supplier<T> supplier) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();

        return () -> {
            MDC.setContextMap(contextMap);
            try {
                return supplier.get();
            } finally {
                MDC.clear();
            }
        };
    }

    public static <T> Consumer<T> wrap(Consumer<T> consumer) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();

        return t -> {
            MDC.setContextMap(contextMap);
            try {
                consumer.accept(t);
            } finally {
                MDC.clear();
            }
        };
    }

    public static <T, R> Function<T, R> wrap(Function<T, R> function) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();

        return (T t) -> {
            setContextMap(contextMap);
            try {
                return function.apply(t);
            } finally {
                MDC.clear();
            }
        };
    }

    private static void setContextMap(Map<String, String> contextMap) {
        if (contextMap != null) {
            MDC.setContextMap(contextMap);
        }
    }

}
```

---

- **MdcTaskDecorator.class**

```java
public class MdcTaskDecorator implements TaskDecorator {

    @NonNull
    @Override
    public Runnable decorate(@NonNull Runnable runnable) {

        return MdcConcurrentUtils.wrap(runnable);
    }
```

for example:

```java
@Bean
public Executor mdcExecutor(){
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("async-");
        executor.setTaskDecorator(new MdcTaskDecorator());
        executor.initialize();

        return executor;
        }
```

---

- **logback-spring.xml**

```xml

<configuration>
    <springProperty scope="context" name="consumer" source="spring.application.name"/>
    <springProperty scope="context" name="version" source="ricciliao.x.common.version"/>
    <springProperty scope="context" name="timezone" source="ricciliao.x.common.time-zone"/>
    <appender name="logstash" class="net.logstash.logback.appender.LogstashTcpSocketAppender">
        <destination>${LOGSTASH_URL}</destination>
        <encoder class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
            <providers>
                <provider class="ricciliao.x.log.AuditLogJsonProvider">
                    <consumer>${consumer}</consumer>
                    <version>${version}</version>
                    <timezone>${timezone}</timezone>
                </provider>
                <timestamp/>
                <message/>
                <mdc/>
            </providers>
        </encoder>
    </appender>
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    <root level="info">
        <appender-ref ref="console"/>
        <appender-ref ref="logstash"/>
    </root>
</configuration>
```

---

🤖 Best wishes and enjoy it ~~