package com.motorcorp.util;

public class RegexConstants {

    public static final String REGEX_NAME = "[A-Za-z0-9 ,]{3,45}";

    public static final String REGEX_NAME_MESSAGE = "Should contain 3-45 characters. allowed characters are A-Z,0-9 and <space>";

    public static final String REGEX_PHONE = "[+0-9 ]{6,15}";

    public static final String REGEX_EMAIL = "^(.+)@(.+)$";

}
