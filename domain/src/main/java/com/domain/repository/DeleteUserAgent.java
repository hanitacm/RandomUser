package com.domain.repository;

import rx.Observable;

public interface DeleteUserAgent {
  Observable<Boolean> deleteUser(String nameUser, String surname, String email);
}
