package com.greatlaboratory.board.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor // 선언된 모든 final필드가 포함된 생성자를 자동 생성해준다.
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
