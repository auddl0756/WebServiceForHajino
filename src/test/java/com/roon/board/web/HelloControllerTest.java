package com.roon.board.web;

import com.roon.board.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
})
public class HelloControllerTest {
    @Autowired
    private MockMvc mvc;    // Main entry point for server-side Spring MVC test support.

    @WithMockUser(roles="USER")
    @Test
    public void hello_리턴() throws Exception{
        String hello ="hello";

        //Perform a request and return a type that allows chaining further actions,
        // such as asserting expectations, on the result.
        mvc.perform(get("/hello"))  // Create a MockHttpServletRequestBuilder for a GET request.
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @WithMockUser(roles = "USER")
    @Test
    public void helloDto_리턴() throws Exception{
        String name="leemr";
        int amount =1111;

        mvc.perform(get("/hello/dto")
                    .param("name",name)
                    .param("amount",String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(name)))
                .andExpect(jsonPath("$.amount",is(amount)));

        //org.springframework.test.web.servlet.result.MockMvcResultMatchers
        // public static <T> o.s.t.w.s.ResultMatcher jsonPath(@NotNull String expression,org.hamcrest.Matcher<T> matcher)
        //Access to response body assertions using a JsonPath  expression

        //public static <T> org.hamcrest.Matcher<T> is(T value)
        //A shortcut to the frequently used is(equalTo(x)).

    }
}
