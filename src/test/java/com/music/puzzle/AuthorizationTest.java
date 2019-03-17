//package com.music.puzzle;
//
//import org.junit.Test;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MvcResult;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//import static org.junit.Assert.assertEquals;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class AuthorizationTest {
//
//    String VALID_JWT = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0LXVzZXIifQ.RoH6NSkVJxlKqjRVqj3TsEW3OfyfSgQ3lL9PlCspHwQ";
//
//    /**
//     * Test case for authorization ok.
//     * Will send request to protected path with valid jwt in Authorization header.
////     */
////    @Test
////    public void authorizationOkTest () throws Exception {
////        mockMvc.perform(get("/user/test")
////                .header("AUTHORIZATION", VALID_JWT)
////                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
////                .andExpect(status().isOk());
////    }
//
//    @Test
//    public void test() {
//        duplicate("aabaaaaaAba");
//    }
//
//    private String reverse(String string) {
//        StringBuilder reversed = new StringBuilder();
//        for(int i = string.length() -1 ; i >= 0; --i) {
//            reversed.append(string.charAt(i));
//        }
//        return reversed.toString();
//    }
//
//    private void duplicate(String string) {
//        Map<Character, Boolean> uniques = new HashMap<>();
//        for(int i = 0; i < string.length(); ++i) {
//            Character c = string.charAt(i);
//            if (uniques.containsKey(c)) {
//                if(!uniques.get(c)) {
//                    System.out.println(c);
//                    uniques.put(c, true);
//                }
//
//            } else {
//                uniques.put(c, false);
//            }
//        }
//    }
//
//    private void firstNonRepeated(String string) {
//        for(int i = 0; i < string.length(); ++i) {
//            Character c = string.charAt(i);
//            for(int j = 0; j < string.)
//        }
//    }
//}
