package com.domain.usecases;

import com.domain.model.UserModelCollection;

public class GetRandomUsersUseCase {
  private final UserRepository userRepository;

  public GetRandomUsersUseCase(UserRepository userRepository) {

    this.userRepository = userRepository;
  }

  public rx.Observable<UserModelCollection> getRandomUsers() {
    return userRepository.getRandomUsers();
  }
}
