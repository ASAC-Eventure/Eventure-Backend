package com.LTUC.Eventure.services.user;

import com.LTUC.Eventure.bo.user.CreateUserRequest;

public interface AppUserService {
    void createPreValidation(CreateUserRequest createDto);
}
