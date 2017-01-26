package com.domain.usecases;

import com.domain.model.UserModel;
import com.domain.respository.GetRemoteUsersAgent;
import com.domain.respository.GetUsersAgent;
import java.util.List;
import rx.Observable;

public class GetUsersUseCaseImp implements GetUsersUseCase {
  private final GetUsersAgent getUsersAgent;
  private GetRemoteUsersAgent getRemoteUsersAgent;

  public GetUsersUseCaseImp(GetUsersAgent getUsersAgent, GetRemoteUsersAgent getRemoteUsersAgent) {
    this.getUsersAgent = getUsersAgent;
    this.getRemoteUsersAgent = getRemoteUsersAgent;
  }

  public Observable<List<UserModel>> getUsers() {

    return Observable.concat(getUsersAgent.getUsers(), getRemoteUsersAgent.getUsers())
        .first(userModels -> userModels.size() > 0)
        .flatMapIterable(userModels -> userModels)
        .toSortedList(
            (userModel, userModel2) -> userModel.getFirsName().compareTo(userModel2.getFirsName()));
  }
}
