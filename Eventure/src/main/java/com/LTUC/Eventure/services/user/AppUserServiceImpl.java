package com.LTUC.Eventure.services.user;

import com.LTUC.Eventure.bo.user.CreateUserRequest;
import com.LTUC.Eventure.repositories.AppUserJPARepository;
import com.LTUC.Eventure.util.exception.BodyGuardException;
import com.LTUC.Eventure.util.validators.validators.CompositeValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

import static com.LTUC.Eventure.util.validators.validators.CompositeValidator.hasValue;
import static java.lang.String.join;
import static java.util.Objects.nonNull;

@Service
public class AppUserServiceImpl implements AppUserService{

    private final AppUserJPARepository appUserJPARepository;

    public AppUserServiceImpl(AppUserJPARepository appUserJPARepository) {
        this.appUserJPARepository = appUserJPARepository;
    }
    protected void validate(List<String> violations) {
        if (!violations.isEmpty()) {
            throw new BodyGuardException(join(",", violations));
        }
    }
    @Override
    public void createPreValidation(CreateUserRequest createDto) {
        List<String> violation = new CompositeValidator<CreateUserRequest, String>()
                .addValidator(r -> hasValue(r.getUsername()), "User Name Cannot Be Empty")
                .addValidator(r -> hasValue(r.getCountry()), "Country Cannot Be Empty")
                .addValidator(r -> hasValue(r.getEmail()), "Email Cannot Be Empty")
                .addValidator(r -> nonNull(r.getDateOfBirth()), "Date Of Birth Cannot Be Empty")
                .addValidator(r -> nonNull(r.getPassword()), "Password Cannot Be Empty")
                .addValidator(r -> r.getEmail() == null || Pattern.compile("^(.+)@(.+)\\.(.+)$").matcher(r.getEmail()).matches(), "Enter a valid email")
                .addValidator(r -> r.getEmail() == null || !appUserJPARepository.findByEmail(r.getEmail().toLowerCase()).isPresent(), "Email already exists")
                .addValidator(r -> Pattern.compile("^(?=.*[A-Z]).{8,20}$").matcher(r.getPassword()).matches(), "Password should be minimum 8 characters with 1 upper case letter")
                .validate(createDto);

        validate(violation);
    }
}
