package com.domain.repository;

import com.domain.model.UserModel;
import java.util.List;

public interface GetUsersAgent {
  rx.Observable<List<UserModel>> getUsers();
}
