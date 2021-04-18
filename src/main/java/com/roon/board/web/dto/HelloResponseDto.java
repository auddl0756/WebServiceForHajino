package com.roon.board.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

//@RequiredArgsConstructor
// Generates a constructor with required arguments.
// Required arguments are final fields and fields with constraints such as @NonNull.

@RequiredArgsConstructor
@Getter
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
