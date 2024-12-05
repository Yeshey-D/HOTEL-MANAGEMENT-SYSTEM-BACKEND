package com.Java.hotelmanagementsystem.util;

import java.util.Map;

public interface IAuthenticatedUserService {
    Long getUserId();

    Map<String, Object> getAttributes();
}