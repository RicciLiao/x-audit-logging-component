package ricciliao.x.log;

import jakarta.annotation.Nonnull;
import org.springframework.core.task.TaskDecorator;
import ricciliao.x.log.common.MdcConcurrentUtils;

public class MdcSupportTaskDecorator implements TaskDecorator {

    @Nonnull
    @Override
    public Runnable decorate(@Nonnull Runnable runnable) {

        return MdcConcurrentUtils.wrap(runnable);
    }

}
