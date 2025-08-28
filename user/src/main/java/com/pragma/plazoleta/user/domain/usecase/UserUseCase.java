package com.pragma.plazoleta.user.domain.usecase;

import com.pragma.plazoleta.user.domain.api.IUserServicePort;
import com.pragma.plazoleta.user.domain.exception.InvalidFormatException;
import com.pragma.plazoleta.user.domain.exception.MissingDataException;
import com.pragma.plazoleta.user.domain.model.User;
import com.pragma.plazoleta.user.domain.spi.IPasswordEncoderPort;
import com.pragma.plazoleta.user.domain.spi.IUserPersistencePort;
import java.util.List;
import java.util.regex.Pattern;

public class UserUseCase implements IUserServicePort {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?\\d{1,12}$");
    private static final String OWNER_ROLE = "propietario";

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
        validateOwnerUser(user);

        var encodedPassword = passwordEncoderPort.encode(user.getPassword());

        user.setRole(OWNER_ROLE);
        user.setPassword(encodedPassword);

        userPersistencePort.saveUser(user);
    }

    private void validateOwnerUser(User user) {
        if (!haveOwnerRequiredData(user)) {
            throw new MissingDataException("One or more required fields are missing");
        }

        if (!isValidEmail(user.getEmail())) {
            throw new InvalidFormatException("Invalid email format");
        }

        if (!isValidPhoneNumber(user.getPhoneNumber())) {
            throw new InvalidFormatException("Invalid phone number format");
        }

        if (!isLegalAge(user.getAge())) {
            throw new InvalidFormatException("User age must be 18 or more");
        }
    }

    private boolean haveOwnerRequiredData(User user) {
        return user.getName() != null && user.getLastName() != null && user.getIdNumber() != null
                && user.getPhoneNumber() != null && user.getBirthdate() != null
                && user.getEmail() != null && user.getPassword() != null;
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