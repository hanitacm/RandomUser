package com.domain.usecases;

import com.domain.repository.DeleteUserAgent;
import rx.Observable;

public class DeleteUserUseCaseImp implements DeleteUserUseCase {

  private final DeleteUserAgent deleteUserAgent;

  public DeleteUserUseCaseImp(DeleteUserAgent deleteUserAgent) {

    this.deleteUserAgent = deleteUserAgent;
  }

  @Override
  public Observable<Boolean> deleteUser(String nameUser, String surname, String email) {
    return deleteUserAgent.deleteUser(nameUser, surname, email);
  }
}
