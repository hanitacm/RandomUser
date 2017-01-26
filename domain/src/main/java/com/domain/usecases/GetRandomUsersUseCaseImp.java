package com.domain.usecases;

import com.domain.model.UserModel;
import com.domain.respository.GetRemoteUsersAgent;
import java.util.List;
import rx.Observable;

public class GetRandomUsersUseCaseImp implements GetRandomUsersUseCase {
  private final GetRemoteUsersAgent getUsersAgent;

  public GetRandomUsersUseCaseImp(GetRemoteUsersAgent getUsersAgent) {

    this.getUsersAgent = getUsersAgent;
  }

  public Observable<List<UserModel>> getRandomUsers() {
    return getUsersAgent.getUsers()
        .flatMapIterable(userModels -> userModels)
        .toSortedList(
            (userModel, userModel2) -> userModel.getFirsName().compareTo(userModel2.getFirsName()));
  }
}

