package ua.lady.scm.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ua.lady.scm.service.DbSynchronizer;

@Component
@Slf4j
public class StartupEventListener {

    @Autowired
    private DbSynchronizer synchronizer;

    @EventListener(ApplicationReadyEvent.class)
    public void afterStartup() {
        synchronizer.loadNewest();
    }
}
