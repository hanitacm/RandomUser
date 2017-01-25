package com.domain.usecases;

import rx.Observable;

public class DeleteUserUseCase {

  private final DeleteUserAgent deleteUserAgent;

  public DeleteUserUseCase(DeleteUserAgent deleteUserAgent) {

    this.deleteUserAgent = deleteUserAgent;
  }

  public Observable<Boolean> deleteUser(String nameUser, String surname, String email) {
    return deleteUserAgent.deleteUser(nameUser, surname, email);
  }
}
