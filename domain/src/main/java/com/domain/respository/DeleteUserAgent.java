package com.domain.respository;

import rx.Observable;

public interface DeleteUserAgent {
  Observable<Boolean> deleteUser(String nameUser, String surname, String email);
}
