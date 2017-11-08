package com.domain.respository;

import com.domain.model.UserModel;
import java.util.List;

public interface GetUsersAgent {
  rx.Observable<List<UserModel>> getUsers();
}
