package com.randomuser.data.agent;

import com.domain.model.UserModel;
import com.domain.repository.GetUserDetailAgent;
import com.randomuser.data.model.mapper.UserDataModelMapper;
import com.randomuser.data.repository.UserRepository;
import rx.Observable;

public class GetUserDetailAgentImp implements GetUserDetailAgent {
  private final UserRepository userRepository;
  private final UserDataModelMapper userDataModelMapper;

  public GetUserDetailAgentImp(UserRepository userRepository,
      UserDataModelMapper userDataModelMapper) {
    this.userRepository = userRepository;
    this.userDataModelMapper = userDataModelMapper;
  }

  @Override
  public Observable<UserModel> getUserDetail(String name, String surname, String email) {
    return userRepository.getUserDetail(name, surname, email)
        .map(userDataModelMapper::mapUserProperties);
  }
}
