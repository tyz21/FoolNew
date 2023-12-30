package com.example.googleMapApplicationTracker.appUser.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private T result;
    private boolean error;
    private long id;
    private String userName;
    private byte[] image;

    public ApiResponse(T result, boolean error) {
        this.result = result;
        this.error = error;
    }

    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse<>(result, false, 0, null, null);
    }

    public static <T> ApiResponse<T> error(T result) {
        return new ApiResponse<>(result, true, 0, null, null);
    }
}

