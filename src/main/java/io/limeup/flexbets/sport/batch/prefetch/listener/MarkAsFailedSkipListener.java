package io.limeup.flexbets.sport.batch.prefetch.listener;

import io.limeup.flexbets.sport.model.PrefetchLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.batch.core.SkipListener;

@RequiredArgsConstructor
@Slf4j
public class MarkAsFailedSkipListener implements SkipListener<PrefetchLog, PrefetchLog> {

    @Override
    public void onSkipInRead(Throwable t) {
        log.error("Error while reading on stat score data pre fetch step: {}", ExceptionUtils.getStackTrace(t));
    }

    @Override
    public void onSkipInWrite(PrefetchLog item, Throwable t) {
        String error = ExceptionUtils.getStackTrace(t);
        item.setErrorMessage(error);
        item.setStatus(PrefetchLog.Status.FAILED);
        log.error("Error while writing on stat score data pre fetch step: {}", error);
    }

    @Override
    public void onSkipInProcess(PrefetchLog item, Throwable t) {
        String error = ExceptionUtils.getStackTrace(t);
        item.setErrorMessage(error);
        item.setStatus(PrefetchLog.Status.FAILED);
        log.error("Error while processing on stat score data pre fetch step: {}", error);
    }
}
