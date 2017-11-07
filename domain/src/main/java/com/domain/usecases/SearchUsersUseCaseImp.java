package com.domain.usecases;

import com.domain.model.UserModel;
import com.domain.repository.FindUsersAgent;
import java.util.List;

public class SearchUsersUseCaseImp implements SearchUsersUseCase {
  private final FindUsersAgent findUsersAgent;

  public SearchUsersUseCaseImp(FindUsersAgent findUsersAgent) {

    this.findUsersAgent = findUsersAgent;
  }

  public rx.Observable<List<UserModel>> searchUsers(String queryText) {
    return findUsersAgent.searchUsers(queryText);
  }
}
