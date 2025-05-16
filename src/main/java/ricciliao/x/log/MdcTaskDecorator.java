package ricciliao.x.log;

import org.springframework.core.task.TaskDecorator;
import org.springframework.lang.NonNull;
import ricciliao.x.log.common.MdcConcurrentUtils;

public class MdcTaskDecorator implements TaskDecorator {

    @NonNull
    @Override
    public Runnable decorate(@NonNull Runnable runnable) {

        return MdcConcurrentUtils.wrap(runnable);
    }

}
