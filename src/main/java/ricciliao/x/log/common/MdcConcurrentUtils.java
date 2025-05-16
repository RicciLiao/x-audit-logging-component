package ricciliao.x.log.common;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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
