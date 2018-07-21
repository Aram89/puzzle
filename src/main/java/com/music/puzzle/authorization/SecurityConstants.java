package com.music.puzzle.authorization;


public class SecurityConstants {
        public static final String SECRET = "jW41ReS2uKo4K3ecN_B9IHSHfmG_NJGQ1NRNBSIGrpTJ4R6LmD4_V1CvUS4RY3Cb";
        public static final long EXPIRATION_TIME = 864_000_000; // 10 days
        public static final String TOKEN_PREFIX = "Bearer ";
        public static final String HEADER_STRING = "Authorization";
        public static final String SIGN_UP_URL = "/user/sign-up";

}
