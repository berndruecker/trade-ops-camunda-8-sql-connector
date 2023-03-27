package io.camunda.connector.runtime;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.UUID;

/**
 * Simple JobWorker to send a message back to the workflow engine with
 * message name = current element id.
 *
 * A random `loopbackCorrelationKey` is created and sent.
 *
 * This allows to send your own process instance a message for a future message receive event in demo scenarios
 */
@Component
public class LoopbackJobWorker {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private ZeebeClient zeebeClient;

    @JobWorker
    public Map<String, Object> loopback(ActivatedJob job) {
        String correlationKey = UUID.randomUUID().toString();

        LOG.info("Creating loobback message '" + job.getElementId() + "' with loopbackCorrelationKey '" + correlationKey + "'");

        zeebeClient.newPublishMessageCommand()
                .messageName(job.getElementId())
                .correlationKey(correlationKey)
                .send().join();

        return Map.of("loopbackCorrelationKey", correlationKey);
    }
}
