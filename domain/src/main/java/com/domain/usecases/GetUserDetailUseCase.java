package com.domain.usecases;

import com.domain.model.UserModel;
import rx.Observable;

public class GetUserDetailUseCase {

  private final UserRepository userRepository;

  public GetUserDetailUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Observable<UserModel> getUserDetail(String name) {
    return userRepository.getUserDetail(name);
  }
}
