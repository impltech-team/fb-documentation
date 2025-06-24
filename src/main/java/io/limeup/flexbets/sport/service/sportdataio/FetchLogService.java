package io.limeup.flexbets.sport.service.sportdataio;


import io.limeup.flexbets.sport.dto.statscore.prams.FetchIoStatus;
import io.limeup.flexbets.sport.dto.statscore.prams.FetchIoType;
import io.limeup.flexbets.sport.dto.statscore.prams.SportIoType;
import io.limeup.flexbets.sport.model.IoFetchLog;
import io.limeup.flexbets.sport.repository.sportsdataio.FetchIoLogRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class FetchLogService {

    private final FetchIoLogRepository repo;

    public <T> T run(FetchIoType type, SportIoType sport, IoFetchLog parent, Supplier<T> supplier) {
        IoFetchLog log = start(type, sport, parent);
        try {
            T result = supplier.get();
            finishSuccess(log);
            return result;
        } catch (Exception ex) {
            finishError(log, ex);
            throw ex;
        }
    }

    public void runVoid(FetchIoType type, SportIoType sport, IoFetchLog parent, Runnable action) {
        run(type, sport, parent, () -> {
            action.run();
            return null;
        });
    }

    public boolean wasRunRecently(FetchIoType type, SportIoType sport, Duration window) {
        return repo
                .existsRecently(
                        type, sport, LocalDateTime.now().minus(window));
    }
    public IoFetchLog start(FetchIoType type, SportIoType sport, IoFetchLog parent) {
        return repo.save(
                IoFetchLog.builder()
                        .fetchType(type)
                        .sportType(sport)
                        .status(FetchIoStatus.STARTED)
                        .startedAt(LocalDateTime.now())
                        .build());
    }

    public void finishSuccess(IoFetchLog log) {
        finish(log, FetchIoStatus.SUCCESS, null);
    }

    public void finishError(IoFetchLog log, Exception ex) {
        finish(log, FetchIoStatus.ERROR, ExceptionUtils.getStackTrace(ex));
    }

    public void finish(IoFetchLog log, FetchIoStatus status, String details) {
        log.setFinishedAt(LocalDateTime.now());
        log.setDurationMs(Duration.between(
                log.getStartedAt(), log.getFinishedAt()).toMillis());
        log.setStatus(status);
        log.setDetails(details);
        repo.save(log);
    }

}
