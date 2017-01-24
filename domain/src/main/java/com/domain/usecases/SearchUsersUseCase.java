package com.domain.usecases;

import com.domain.model.UserModelCollection;

public class SearchUsersUseCase {
  private final UserRepository userRepository;

  public SearchUsersUseCase(UserRepository userRepository) {

    this.userRepository = userRepository;
  }

  public rx.Observable<UserModelCollection> searchUsers(String queryText) {
    return userRepository.searchUsers(queryText);
  }
}
