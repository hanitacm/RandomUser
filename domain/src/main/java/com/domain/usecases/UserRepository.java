package com.domain.usecases;

import com.domain.model.UserModelCollection;

public interface UserRepository {
  rx.Observable<UserModelCollection> getRandomUsers();
}
