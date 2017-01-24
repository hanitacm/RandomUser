package randomuser.com.data.repository.datasource.api;

import randomuser.com.data.model.UserDataModelCollection;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface UserService {
  @GET("?")
  Observable<UserDataModelCollection> getRandomUsers(
      @Query("results") Integer results);
}
