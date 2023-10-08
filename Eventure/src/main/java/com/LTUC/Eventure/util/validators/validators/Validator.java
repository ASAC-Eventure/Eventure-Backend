package com.LTUC.Eventure.util.validators.validators;

public interface Validator <P, R>{
    R validate(P input);
}
