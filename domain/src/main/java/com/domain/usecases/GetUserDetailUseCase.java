package com.domain.usecases;

import com.domain.model.UserModel;
import rx.Observable;

public class GetUserDetailUseCase {

  private final GetUserDetailAgent getUserDetailAgent;

  public GetUserDetailUseCase(GetUserDetailAgent getUserDetailAgent) {
    this.getUserDetailAgent = getUserDetailAgent;
  }

  public Observable<UserModel> getUserDetail(String name) {
    return getUserDetailAgent.getUserDetail(name);
  }
}
