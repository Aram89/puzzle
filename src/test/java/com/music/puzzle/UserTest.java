package com.music.puzzle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.puzzle.domain.User;
import org.junit.Test;

import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.Assert.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserTest extends BaseTest {

    @Test
    public void signUp() throws Exception {
        User user = new User();
        user.setEmail("test-user@gmail111.com");
        user.setUserName("test-u1ser");
        user.setPassword("pass");
        String jsonContent = convertObjectToJsonString(user);
        mockMvc.perform(post("/user/signup")
                .content(jsonContent)
                .header("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0LXUxc2VyIiwiZW1haWwiOiJ0ZXN0LXVzZXJAZ21haWwuY29tIn0.eKicPJxKmZ0gQ8uXCbXXoyG5iuOsrgU0BZK9N5CBrCQ")
                .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk()).andReturn();
    }

}
