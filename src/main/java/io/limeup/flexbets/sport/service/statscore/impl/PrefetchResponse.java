package io.limeup.flexbets.sport.service.statscore.impl;

import java.time.Instant;

public record PrefetchResponse(String task, Instant startedAt) { }