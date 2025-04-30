package io.limeup.flexbets.sport.converter;

import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;

import java.lang.reflect.Method;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SnakeCaseToCamelCaseArgumentResolverTest {

    private final SnakeCaseToCamelCaseArgumentResolver resolver = new SnakeCaseToCamelCaseArgumentResolver();

    @Mock
    private NativeWebRequest webRequest;

    @Mock
    private WebDataBinderFactory binderFactory;

    @Mock
    private WebDataBinder binder;

    @Test
    void supportsParameter_ShouldReturnTrueForSupportedDTO() throws NoSuchMethodException {
        Method method = TestController.class.getMethod("handle", RequestQueryDTO.class);
        MethodParameter parameter = new MethodParameter(method, 0);

        boolean result = resolver.supportsParameter(parameter);

        assertThat(result).isTrue();
    }

    @Test
    void supportsParameter_ShouldReturnFalseForUnsupportedDTO() throws NoSuchMethodException {
        Method method = TestController.class.getMethod("unsupported", String.class);
        MethodParameter parameter = new MethodParameter(method, 0);

        boolean result = resolver.supportsParameter(parameter);

        assertThat(result).isFalse();
    }

    @Test
    void resolveArgument_ShouldConvertSnakeToCamelAndBindValues() throws Exception {
        Method method = TestController.class.getMethod("handle", RequestQueryDTO.class);
        MethodParameter parameter = new MethodParameter(method, 0);

        Map<String, String[]> inputParams = Map.of(
                "sort_by", new String[]{"name"},
                "filter", new String[]{"admin"}
        );

        when(webRequest.getParameterMap()).thenReturn(inputParams);
        when(binderFactory.createBinder(eq(webRequest), any(), any())).thenReturn(binder);

        Object result = resolver.resolveArgument(parameter, null, webRequest, binderFactory);

        assertThat(result).isInstanceOf(RequestQueryDTO.class);
        verify(binderFactory).createBinder(any(), any(), any());
    }

    static class TestController {
        public void handle(RequestQueryDTO dto) {}
        public void unsupported(String value) {}
    }
}

