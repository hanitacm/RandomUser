package com.domain.respository;

import com.domain.model.UserModel;
import com.domain.model.UserModelCollection;
import java.util.List;
import java.util.Map;
import rx.Observable;

public interface GetRemoteUsersAgent {
  Observable<List<UserModel>> getUsers();
}

