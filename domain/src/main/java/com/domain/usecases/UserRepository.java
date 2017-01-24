package com.domain.usecases;

import com.domain.model.UserModel;
import com.domain.model.UserModelCollection;
import rx.Observable;

public interface UserRepository {
  rx.Observable<UserModelCollection> getRandomUsers();

  Observable<UserModel> getUserDetail(String name);

  Observable<Boolean> deleteUser(String name);

  Observable<UserModelCollection> searchUsers(String queryText);
}

