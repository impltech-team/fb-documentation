package io.limeup.flexbets.sport.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class StatScoreDateTimeUtilsTest {

    @Test
    void formatDateTimeShouldFormatCorrectly() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 4, 27, 15, 45, 30);
        String result = StatScoreDateTimeUtils.formatDateTime(dateTime);
        assertThat(result).isEqualTo("2025-04-27 15:45:30");
    }

    @Test
    void formatDateTimeShouldReturnNullWhenInputIsNull() {
        String result = StatScoreDateTimeUtils.formatDateTime(null);
        assertThat(result).isNull();
    }
}

