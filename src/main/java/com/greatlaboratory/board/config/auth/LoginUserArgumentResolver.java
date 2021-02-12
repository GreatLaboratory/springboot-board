package com.greatlaboratory.board.config.auth;

import com.greatlaboratory.board.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

// 개발자가 필요에 의해서 만든 어노테이션이 어떤 상황에서 충족되고 어떤 값을 파라미터로 넘기는지 정의하는 곳
// HandlerMethodArgumentResolver을 구현하여 2개의 메소드에 조건과 값을 정의한다.
// 구현된 이 클래스는 이후 WebMvcConfigurer의 addArgumentResolvers메소드를 구현한 클래스에서 해당 메소드의 인자값으로 반드시 넘겨줘야 한다.
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    // 1-1. 메소드 파라미터에 LoginUser.class의 어노테이션이 존재하고
    // 1-2. 그 어노테이션에 들어와있는 타입이 SessionUser.class와 일치한다면
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isSessionUserClass = SessionUser.class.equals(parameter.getParameterType());

        return isLoginUserAnnotation && isSessionUserClass;
    }

    // 2. 여기서 지정한 httpSession.getAttribute("user")을 지정한 값으로 해당 메소드의 파라미티로 넘길 수 있게 된다.
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    }
}
