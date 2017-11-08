package com.domain.respository;

import com.domain.model.UserModel;
import java.util.List;
import rx.Observable;

public interface FindUsersAgent {
  Observable<List<UserModel>> searchUsers(String queryText);
}
