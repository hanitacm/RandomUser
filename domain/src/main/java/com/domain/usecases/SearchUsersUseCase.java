package com.domain.usecases;

import com.domain.model.UserModel;
import java.util.List;

public interface SearchUsersUseCase {
  rx.Observable<List<UserModel>> searchUsers(String queryText);
}

