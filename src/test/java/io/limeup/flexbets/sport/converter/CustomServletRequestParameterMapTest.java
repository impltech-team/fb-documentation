package io.limeup.flexbets.sport.converter;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.WebDataBinder;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CustomServletRequestParameterMapTest {

    @Test
    void shouldPopulateSingleAndMultiValueParams() {
        Map<String, String[]> input = new HashMap<>();
        input.put("name", new String[]{"John"});
        input.put("roles", new String[]{"USER", "ADMIN"});
        input.put("empty", new String[]{});

        CustomServletRequestParameterMap paramMap = new CustomServletRequestParameterMap(input);

        assertThat(paramMap.getPropertyValue("name").getValue()).isEqualTo("John");
        assertThat(paramMap.getPropertyValue("roles").getValue()).isInstanceOf(String[].class);
        assertThat((String[]) paramMap.getPropertyValue("roles").getValue()).containsExactly("USER", "ADMIN");
        assertThat(paramMap.contains("empty")).isFalse();
    }

    static class SampleTarget {
        private String name;
        private String[] roles;

        public void setName(String name) {
            this.name = name;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }

        public String getName() {
            return name;
        }

        public String[] getRoles() {
            return roles;
        }
    }

    @Test
    void shouldBindToWebDataBinder() {
        Map<String, String[]> input = Map.of(
                "name", new String[]{"Alice"},
                "roles", new String[]{"ADMIN", "MODERATOR"}
        );

        CustomServletRequestParameterMap paramMap = new CustomServletRequestParameterMap(input);
        SampleTarget target = new SampleTarget();
        WebDataBinder binder = new WebDataBinder(target);
        paramMap.bind(binder);

        assertThat(target.getName()).isEqualTo("Alice");
        assertThat(target.getRoles()).containsExactly("ADMIN", "MODERATOR");
    }
}

