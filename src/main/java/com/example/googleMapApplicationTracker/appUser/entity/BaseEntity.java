package com.example.googleMapApplicationTracker.appUser.entity;

import java.io.Serializable;

public interface BaseEntity<T extends Serializable> {
    T getId();
}
