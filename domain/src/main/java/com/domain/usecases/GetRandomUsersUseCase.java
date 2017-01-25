package com.domain.usecases;

import com.domain.model.UserModel;
import java.util.List;
import rx.Observable;

public class GetRandomUsersUseCase {
  private final GetUsersAgent getUsersAgent;

  public GetRandomUsersUseCase(GetUsersAgent getUsersAgent) {

    this.getUsersAgent = getUsersAgent;
  }

  public Observable<List<UserModel>> getRandomUsers() {
    return getUsersAgent.getUsers()
        .flatMapIterable(userModels -> userModels)
        .toSortedList(
            (userModel, userModel2) -> userModel.getFirsName().compareTo(userModel2.getFirsName()));
  }
}

