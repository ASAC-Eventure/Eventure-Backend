package com.LTUC.Eventure.services.user;

import com.LTUC.Eventure.bo.CreateUserRequest;

public interface AppUserService {
    void createPreValidation(CreateUserRequest createDto);
}
