package com.music.puzzle;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.Assert.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Created by Aram on 7/12/18.
 */
public class AuthorizationTest extends BaseTest {

    String VALID_JWT = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0LXVzZXIifQ.RoH6NSkVJxlKqjRVqj3TsEW3OfyfSgQ3lL9PlCspHwQ";

    /**
     * Test case for authorization ok.
     * Will send request to protected path with valid jwt in Authorization header.
     */
    @Test
    public void authorizationOkTest () throws Exception {
        mockMvc.perform(get("/user/test")
                .header("AUTHORIZATION", VALID_JWT)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());
    }
}
