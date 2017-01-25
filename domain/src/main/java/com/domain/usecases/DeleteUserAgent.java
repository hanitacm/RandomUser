package com.domain.usecases;

import rx.Observable;

public interface DeleteUserAgent {
  Observable<Boolean> deleteUser(String nameUser, String surname, String email);
}
