package com.domain.usecases;

import com.domain.model.UserModel;
import rx.Observable;

public interface GetUserDetailUseCase {
  Observable<UserModel> getUserDetail(String name, String surname, String email);
}
