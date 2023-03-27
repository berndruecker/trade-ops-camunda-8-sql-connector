package io.camunda.connector.runtime;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class NoopJobWorker {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @JobWorker
    public void noop(ActivatedJob job) {
        LOG.info("Passing through NOOP activity: " + job.getElementId());
    }
}
