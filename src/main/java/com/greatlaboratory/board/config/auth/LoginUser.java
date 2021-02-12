package com.greatlaboratory.board.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 만들고 있는 이 LoginUser 어노테이션이 위치할 수 있는 곳을 지정해준다. 여기선 파라미터에만
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}
