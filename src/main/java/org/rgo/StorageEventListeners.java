package org.kie.kogito.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;

import org.kie.api.event.process.ProcessCompletedEvent;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.process.ProcessNodeLeftEvent;
import org.kie.api.event.process.ProcessNodeTriggeredEvent;
import org.kie.api.event.process.ProcessStartedEvent;
import org.kie.api.event.process.ProcessVariableChangedEvent;
import org.kie.kogito.internal.process.event.KogitoProcessEventListener;
import org.kie.kogito.internal.process.runtime.KogitoProcessInstance;

@ApplicationScoped
public class StorageEventListeners implements KogitoProcessEventListener {


    @Override
    public void beforeProcessStarted(ProcessStartedEvent processStartedEvent) {
        try {
            String instanceID = ((KogitoProcessInstance) processStartedEvent.getProcessInstance()).getStringId();
            Path of = Path.of("/tmp/", instanceID);
            System.out.println("creating " + of);
            Path tempFile = Files.createDirectory(of);
            System.out.println("created directory " + tempFile.toAbsolutePath());
            System.out.println("Instance id  " + instanceID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void afterProcessStarted(ProcessStartedEvent processStartedEvent) {

    }

    @Override
    public void beforeProcessCompleted(ProcessCompletedEvent processCompletedEvent) {

    }

    @Override
    public void afterProcessCompleted(ProcessCompletedEvent processCompletedEvent) {
        String instanceID = ((KogitoProcessInstance) processCompletedEvent.getProcessInstance()).getStringId();
        try {
            Path of = Path.of("/tmp/", instanceID);
            System.out.println("removing " + of);
            Files.delete(of);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void beforeNodeTriggered(ProcessNodeTriggeredEvent processNodeTriggeredEvent) {

    }

    @Override
    public void afterNodeTriggered(ProcessNodeTriggeredEvent processNodeTriggeredEvent) {

    }

    @Override
    public void beforeNodeLeft(ProcessNodeLeftEvent processNodeLeftEvent) {

    }

    @Override
    public void afterNodeLeft(ProcessNodeLeftEvent processNodeLeftEvent) {

    }

    @Override
    public void beforeVariableChanged(ProcessVariableChangedEvent processVariableChangedEvent) {

    }

    @Override
    public void afterVariableChanged(ProcessVariableChangedEvent processVariableChangedEvent) {

    }
}