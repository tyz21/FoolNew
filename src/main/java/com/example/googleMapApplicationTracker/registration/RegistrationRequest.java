package com.example.googleMapApplicationTracker.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class RegistrationRequest {
    private final String userName;
    private final String password;
}
