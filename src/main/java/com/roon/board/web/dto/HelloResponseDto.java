package com.roon.board.web.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

//@RequiredArgsConstructor
// Generates a constructor with required arguments.
// Required arguments are final fields and fields with constraints such as @NonNull.

@Getter
@RequiredArgsConstructor
public class HelloResponseDto {
    @NonNull
    private final String name;
    @NonNull
    private final int amount;
}
