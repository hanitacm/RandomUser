package com.domain.usecases;

import rx.Observable;

public interface DeleteUserUseCase {
  Observable<Boolean> deleteUser(String nameUser, String surname, String email);
}
