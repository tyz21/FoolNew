package com.example.googleMapApplicationTracker.appUser.utility;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse<T> {
    private T result;
    private boolean error;

    public ApiResponse(T result, boolean error) {
        this.result = result;
        this.error = error;
    }
}
