package io.limeup.flexbets.sport.batch.event;

public class BatchJobEvent {
    private final String jobName;

    public BatchJobEvent(String jobName) {
        this.jobName = jobName;
    }

    public String getJobName() {
        return jobName;
    }
}
