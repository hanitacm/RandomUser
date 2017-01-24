package com.domain.usecases;

import rx.Observable;

public class DeleteUserUseCase {
  private UserRepository userRepository;

  public DeleteUserUseCase(UserRepository userRepository) {

    this.userRepository = userRepository;
  }

  public Observable<Boolean> deleteUser(String email) {
    return userRepository.deleteUser(email);
  }
}
