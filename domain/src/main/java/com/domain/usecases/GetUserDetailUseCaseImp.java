package com.domain.usecases;

import com.domain.model.UserModel;
import com.domain.respository.GetUserDetailAgent;
import rx.Observable;

public class GetUserDetailUseCaseImp implements GetUserDetailUseCase {

  private final GetUserDetailAgent getUserDetailAgent;

  public GetUserDetailUseCaseImp(GetUserDetailAgent getUserDetailAgent) {
    this.getUserDetailAgent = getUserDetailAgent;
  }

  public Observable<UserModel> getUserDetail(String name, String surname, String email) {
    return getUserDetailAgent.getUserDetail(name, surname, email);
  }
}
