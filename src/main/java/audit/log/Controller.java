package audit.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("")
public class Controller {

    private final static Logger logger = LoggerFactory.getLogger(Controller.class);

    @GetMapping
    public void log() {
        MDC.clear();
        logger.info("my info.");
        logger.warn("my warn.");
        logger.error("my error.", new RuntimeException("my exception."));
    }

}
