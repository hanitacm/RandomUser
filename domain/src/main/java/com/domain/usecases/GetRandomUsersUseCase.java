package com.domain.usecases;

import com.domain.model.UserModel;
import java.util.List;
import rx.Observable;

public interface GetRandomUsersUseCase {
  Observable<List<UserModel>> getRandomUsers();
}
