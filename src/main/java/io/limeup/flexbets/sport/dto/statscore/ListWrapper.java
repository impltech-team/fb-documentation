package io.limeup.flexbets.sport.dto.statscore;

import lombok.Data;

import java.util.List;

@Data
public class ListWrapper<T> {
    private List<T> items;
}
