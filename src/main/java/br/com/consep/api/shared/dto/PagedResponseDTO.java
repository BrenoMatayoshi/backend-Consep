package br.com.consep.api.shared.dto;

import java.util.List;

public record PagedResponseDTO<T>(
    List<T> data,
    MetaDTO meta) {
}