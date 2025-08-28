package com.pragma.plazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.regex.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?\\d{1,12}$");

    private Long id;
    private Integer nit;
    private String name;
    private String address;
    private String phoneNumber;
    private String urlLogo;
    private Long ownerId;

    public boolean isValidName(){
        try {
            Integer.parseInt(name);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    public boolean isValidPhoneNumber() {
        return PHONE_PATTERN.matcher(this.phoneNumber).matches();
    }
}
