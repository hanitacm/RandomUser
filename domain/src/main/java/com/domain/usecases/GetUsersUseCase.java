package com.domain.usecases;

import com.domain.model.UserModel;
import java.util.List;
import rx.Observable;

public interface GetUsersUseCase {
  Observable<List<UserModel>> getUsers();
}
