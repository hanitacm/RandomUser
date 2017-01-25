package com.domain.usecases;

import com.domain.model.UserModel;
import java.util.List;

public class SearchUsersUseCase {
  private final FindUsersAgent findUsersAgent;

  public SearchUsersUseCase(FindUsersAgent findUsersAgent) {

    this.findUsersAgent = findUsersAgent;
  }

  public rx.Observable<List<UserModel>> searchUsers(String queryText) {
    return findUsersAgent.searchUsers(queryText);
  }
}
