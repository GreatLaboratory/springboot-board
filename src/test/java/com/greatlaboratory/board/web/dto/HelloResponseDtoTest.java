package com.greatlaboratory.board.web.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {
    @Test
    public void lombok_test(){
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount); // 생성자가 자동으로 만들어졌는지 검증

        //then
        assertThat(dto.getName()).isEqualTo(name); // getter 함수가 자동으로 만들어졌는지 검증
        assertThat(dto.getAmount()).isEqualTo(amount); // getter 함수가 자동으로 만들어졌는지 검증
    }

}
