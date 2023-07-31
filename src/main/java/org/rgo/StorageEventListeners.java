package org.rgo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import javax.enterprise.context.ApplicationScoped;

import org.kie.api.event.process.ProcessCompletedEvent;
import org.kie.api.event.process.ProcessNodeLeftEvent;
import org.kie.api.event.process.ProcessNodeTriggeredEvent;
import org.kie.api.event.process.ProcessStartedEvent;
import org.kie.api.event.process.ProcessVariableChangedEvent;
import org.kie.kogito.internal.process.event.KogitoProcessEventListener;
import org.kie.kogito.internal.process.event.ProcessWorkItemTransitionEvent;
import org.kie.kogito.internal.process.runtime.KogitoProcessInstance;


/**
 * Event listeners to handle storage per execution, where each process
 * will have a scratch space mounted under /var/run/sonataflow/${instanceId}/ on start
 * and will be removed on end, either complete or error state.
 */
@ApplicationScoped
public class StorageEventListeners implements KogitoProcessEventListener {


    @Override
    public void beforeProcessStarted(ProcessStartedEvent processStartedEvent) {
        // TODO find out in what cases we might have the folder already exists and handle that
        try {
            String instanceID = ((KogitoProcessInstance) processStartedEvent.getProcessInstance()).getStringId();
            Path of = Path.of("/tmp/", instanceID);
            if (of.toFile().exists()) {
                // just continue for now
                return;
            }
            System.out.println("creating " + of);
            Path tempFile = Files.createDirectory(of);
            System.out.println("created directory " + tempFile.toAbsolutePath());
            System.out.println("Instance id  " + instanceID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void afterWorkItemTransition(ProcessWorkItemTransitionEvent event) {
        if (event.getProcessInstance().getState() == KogitoProcessInstance.STATE_ABORTED
                ||  event.getProcessInstance().getState() == KogitoProcessInstance.STATE_ERROR ) {
            // aborting or in error state -> clean up after you
            System.out.println("this flow is in error or aborted state");
            String instanceID = ((KogitoProcessInstance) event.getProcessInstance()).getStringId();
            cleanup(instanceID);
        }
        System.out.println("Transition to ->" + event.getProcessInstance().getState());
    }

    @Override
    public void afterProcessStarted(ProcessStartedEvent processStartedEvent) {

    }



    @Override
    public void beforeProcessCompleted(ProcessCompletedEvent processCompletedEvent) {
        System.out.println("process completed event");
        String instanceID = ((KogitoProcessInstance) processCompletedEvent.getProcessInstance()).getStringId();

        cleanup(instanceID);

    }

    private static void cleanup(String instanceID) {
        if (instanceID == null || instanceID.isBlank()) {
            return;
        }
        try {
            Path of = Path.of("/tmp/", instanceID);
            if (!of.toFile().exists() || !of.toFile().isDirectory()) {
                return;
            }
            // TODO replace with logger calls
            System.out.println("Removing directory " + of);
            Files.walk(of)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
            // TODO replace with logger calls
            System.out.println("Removed directory" + of);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public void onError() {
//        cleanup(null);
//    }

    @Override
    public void afterProcessCompleted(ProcessCompletedEvent processCompletedEvent) {
        System.out.println("after process completed event");
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