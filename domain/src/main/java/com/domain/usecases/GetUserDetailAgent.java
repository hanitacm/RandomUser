package com.domain.usecases;

import com.domain.model.UserModel;
import rx.Observable;

public interface GetUserDetailAgent {
  Observable<UserModel> getUserDetail(String name);
}
