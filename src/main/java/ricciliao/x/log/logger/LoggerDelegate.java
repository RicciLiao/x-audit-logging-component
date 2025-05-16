package ricciliao.x.log.logger;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.CheckReturnValue;
import org.slf4j.spi.LoggingEventBuilder;

public class LoggerDelegate implements Logger {
    private final Logger delegate;

    public LoggerDelegate(Logger delegate) {
        this.delegate = delegate;
    }

    public Logger getDelegate() {

        return delegate;
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public LoggingEventBuilder makeLoggingEventBuilder(Level level) {
        return delegate.makeLoggingEventBuilder(level);
    }

    @Override
    @CheckReturnValue
    public LoggingEventBuilder atLevel(Level level) {
        return delegate.atLevel(level);
    }

    @Override
    public boolean isEnabledForLevel(Level level) {
        return delegate.isEnabledForLevel(level);
    }

    @Override
    public boolean isTraceEnabled() {
        return delegate.isTraceEnabled();
    }

    @Override
    public void trace(String s) {
        delegate.trace(s);
    }

    @Override
    public void trace(String s, Object o) {
        delegate.trace(s, o);
    }

    @Override
    public void trace(String s, Object o, Object o1) {
        delegate.trace(s, o, o1);
    }

    @Override
    public void trace(String s, Object... objects) {
        delegate.trace(s, objects);
    }

    @Override
    public void trace(String s, Throwable throwable) {
        delegate.trace(s, throwable);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return delegate.isTraceEnabled(marker);
    }

    @Override
    @CheckReturnValue
    public LoggingEventBuilder atTrace() {
        return delegate.atTrace();
    }

    @Override
    public void trace(Marker marker, String s) {
        delegate.trace(marker, s);
    }

    @Override
    public void trace(Marker marker, String s, Object o) {
        delegate.trace(marker, s, o);
    }

    @Override
    public void trace(Marker marker, String s, Object o, Object o1) {
        delegate.trace(marker, s, o, o1);
    }

    @Override
    public void trace(Marker marker, String s, Object... objects) {
        delegate.trace(marker, s, objects);
    }

    @Override
    public void trace(Marker marker, String s, Throwable throwable) {
        delegate.trace(marker, s, throwable);
    }

    @Override
    public boolean isDebugEnabled() {
        return delegate.isDebugEnabled();
    }

    @Override
    public void debug(String s) {
        delegate.debug(s);
    }

    @Override
    public void debug(String s, Object o) {
        delegate.debug(s, o);
    }

    @Override
    public void debug(String s, Object o, Object o1) {
        delegate.debug(s, o, o1);
    }

    @Override
    public void debug(String s, Object... objects) {
        delegate.debug(s, objects);
    }

    @Override
    public void debug(String s, Throwable throwable) {
        delegate.debug(s, throwable);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return delegate.isDebugEnabled(marker);
    }

    @Override
    public void debug(Marker marker, String s) {
        delegate.debug(marker, s);
    }

    @Override
    public void debug(Marker marker, String s, Object o) {
        delegate.debug(marker, s, o);
    }

    @Override
    public void debug(Marker marker, String s, Object o, Object o1) {
        delegate.debug(marker, s, o, o1);
    }

    @Override
    public void debug(Marker marker, String s, Object... objects) {
        delegate.debug(marker, s, objects);
    }

    @Override
    public void debug(Marker marker, String s, Throwable throwable) {
        delegate.debug(marker, s, throwable);
    }

    @Override
    @CheckReturnValue
    public LoggingEventBuilder atDebug() {
        return delegate.atDebug();
    }

    @Override
    public boolean isInfoEnabled() {
        return delegate.isInfoEnabled();
    }

    @Override
    public void info(String s) {
        delegate.info(s);
    }

    @Override
    public void info(String s, Object o) {
        delegate.info(s, o);
    }

    @Override
    public void info(String s, Object o, Object o1) {
        delegate.info(s, o, o1);
    }

    @Override
    public void info(String s, Object... objects) {
        delegate.info(s, objects);
    }

    @Override
    public void info(String s, Throwable throwable) {
        delegate.info(s, throwable);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return delegate.isInfoEnabled(marker);
    }

    @Override
    public void info(Marker marker, String s) {
        delegate.info(marker, s);
    }

    @Override
    public void info(Marker marker, String s, Object o) {
        delegate.info(marker, s, o);
    }

    @Override
    public void info(Marker marker, String s, Object o, Object o1) {
        delegate.info(marker, s, o, o1);
    }

    @Override
    public void info(Marker marker, String s, Object... objects) {
        delegate.info(marker, s, objects);
    }

    @Override
    public void info(Marker marker, String s, Throwable throwable) {
        delegate.info(marker, s, throwable);
    }

    @Override
    @CheckReturnValue
    public LoggingEventBuilder atInfo() {
        return delegate.atInfo();
    }

    @Override
    public boolean isWarnEnabled() {
        return delegate.isWarnEnabled();
    }

    @Override
    public void warn(String s) {
        delegate.warn(s);
    }

    @Override
    public void warn(String s, Object o) {
        delegate.warn(s, o);
    }

    @Override
    public void warn(String s, Object... objects) {
        delegate.warn(s, objects);
    }

    @Override
    public void warn(String s, Object o, Object o1) {
        delegate.warn(s, o, o1);
    }

    @Override
    public void warn(String s, Throwable throwable) {
        delegate.warn(s, throwable);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return delegate.isWarnEnabled(marker);
    }

    @Override
    public void warn(Marker marker, String s) {
        delegate.warn(marker, s);
    }

    @Override
    public void warn(Marker marker, String s, Object o) {
        delegate.warn(marker, s, o);
    }

    @Override
    public void warn(Marker marker, String s, Object o, Object o1) {
        delegate.warn(marker, s, o, o1);
    }

    @Override
    public void warn(Marker marker, String s, Object... objects) {
        delegate.warn(marker, s, objects);
    }

    @Override
    public void warn(Marker marker, String s, Throwable throwable) {
        delegate.warn(marker, s, throwable);
    }

    @Override
    @CheckReturnValue
    public LoggingEventBuilder atWarn() {
        return delegate.atWarn();
    }

    @Override
    public boolean isErrorEnabled() {
        return delegate.isErrorEnabled();
    }

    @Override
    public void error(String s) {
        delegate.error(s);
    }

    @Override
    public void error(String s, Object o) {
        delegate.error(s, o);
    }

    @Override
    public void error(String s, Object o, Object o1) {
        delegate.error(s, o, o1);
    }

    @Override
    public void error(String s, Object... objects) {
        delegate.error(s, objects);
    }

    @Override
    public void error(String s, Throwable throwable) {
        delegate.error(s, throwable);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return delegate.isErrorEnabled(marker);
    }

    @Override
    public void error(Marker marker, String s) {
        delegate.error(marker, s);
    }

    @Override
    public void error(Marker marker, String s, Object o) {
        delegate.error(marker, s, o);
    }

    @Override
    public void error(Marker marker, String s, Object o, Object o1) {
        delegate.error(marker, s, o, o1);
    }

    @Override
    public void error(Marker marker, String s, Object... objects) {
        delegate.error(marker, s, objects);
    }

    @Override
    public void error(Marker marker, String s, Throwable throwable) {
        delegate.error(marker, s, throwable);
    }

    @Override
    @CheckReturnValue
    public LoggingEventBuilder atError() {
        return delegate.atError();
    }
}
