package com.pragma.plazoleta.user.domain.usecase;

import com.pragma.plazoleta.user.domain.api.IUserServicePort;
import com.pragma.plazoleta.user.domain.exception.InvalidFormatException;
import com.pragma.plazoleta.user.domain.exception.MissingDataException;
import com.pragma.plazoleta.user.domain.model.User;
import com.pragma.plazoleta.user.domain.model.Role;
import com.pragma.plazoleta.user.domain.spi.IPasswordEncoderPort;
import com.pragma.plazoleta.user.domain.spi.IUserPersistencePort;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class UserUseCase implements IUserServicePort {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?\\d{1,12}$");
    private static final String REQUIRED_FIELD_EXCEPTION = "One or more required fields are missing";
    private static final String EMAIL_FORMAT_EXCEPTION = "Invalid email format";
    private static final String PHONE_NUMBER_FORMAT_EXCEPTION = "Invalid phone number format";
    private static final String USER_AGE_EXCEPTION = "User age must be 18 or more";

    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;

    public UserUseCase(
            IUserPersistencePort userPersistencePort,
            IPasswordEncoderPort passwordEncoderPort
    ) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public void saveOwner(User user) {
        validateUser(user, Role.OWNER);

        String encodedPassword = passwordEncoderPort.encode(user.getPassword());

        user.setRole(Role.OWNER);
        user.setPassword(encodedPassword);

        userPersistencePort.saveUser(user);
    }

    @Override
    public void saveEmployee(User user) {
        validateUser(user, Role.EMPLOYEE);

        String encodedPassword = passwordEncoderPort.encode(user.getPassword());

        user.setRole(Role.EMPLOYEE);
        user.setPassword(encodedPassword);

        userPersistencePort.saveUser(user);
    }

    @Override
    public User getUser(Long id) {
        return userPersistencePort.getUser(id);
    }

    private void validateUser(User user, String role) {
        if (role.equals(Role.OWNER)) {
            if (!haveOwnerRequiredData(user)) {
                throw new MissingDataException(REQUIRED_FIELD_EXCEPTION);
            }

            if (!isLegalAge(user.getAge())) {
                throw new InvalidFormatException(USER_AGE_EXCEPTION);
            }
        } else {
            if (!haveUserRequiredData(user)) {
                throw new MissingDataException(REQUIRED_FIELD_EXCEPTION);
            }
        }

        if (!isValidEmail(user.getEmail())) {
            throw new InvalidFormatException(EMAIL_FORMAT_EXCEPTION);
        }

        if (!isValidPhoneNumber(user.getPhoneNumber())) {
            throw new InvalidFormatException(PHONE_NUMBER_FORMAT_EXCEPTION);
        }
    }

    private boolean haveOwnerRequiredData(User user) {
        return haveUserRequiredData(user) && !isNull(user.getBirthdate());
    }

    private boolean haveUserRequiredData(User user) {
        return !isNull(user.getName()) && !isNull(user.getLastName()) && !isNull(user.getIdNumber())
                && !isNull(user.getPhoneNumber()) && !isNull(user.getEmail()) && !isNull(user.getPassword());
    }

    private boolean isValidEmail(String email) {
        return email.contains("@");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    private boolean isLegalAge(int age) {
        return age >= 18;
    }
}