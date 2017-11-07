package com.randomuser.data.repository.datasource.api;

import com.randomuser.data.model.UserDataModelCollection;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface UserService {
  @GET("?")
  Observable<UserDataModelCollection> getRandomUsers(
      @Query("results") Integer results);
}
