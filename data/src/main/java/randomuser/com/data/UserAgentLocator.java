package randomuser.com.data;

import android.content.Context;
import android.support.annotation.NonNull;
import com.domain.usecases.DeleteUserAgent;
import com.domain.usecases.FindUsersAgent;
import com.domain.usecases.GetUserDetailAgent;
import com.domain.usecases.GetUsersAgent;
import randomuser.com.data.agent.DeleteUserAgentImp;
import randomuser.com.data.agent.FindUsersAgentImp;
import randomuser.com.data.agent.GetUserDetailAgentImp;
import randomuser.com.data.agent.GetUsersAgentImp;
import randomuser.com.data.model.mapper.UserDataModelMapper;
import randomuser.com.data.repository.UserRepository;
import randomuser.com.data.repository.datasource.api.RandomUserApi;
import randomuser.com.data.repository.datasource.cache.FileManager;
import randomuser.com.data.repository.datasource.cache.RandomUserCache;
import randomuser.com.data.repository.datasource.sharedPreferences.RandomUserPreferences;

public class UserAgentLocator {
  public GetUsersAgent getUsersAgent(Context context) {
    return new GetUsersAgentImp(provideUserRepository(context), provideUserDataModelMapper());
  }

  public GetUserDetailAgent getUserDetailAgent(Context context) {
    return new GetUserDetailAgentImp(provideUserRepository(context), provideUserDataModelMapper());
  }

  public DeleteUserAgent deleteUserAgent(Context context) {
    return new DeleteUserAgentImp(provideUserRepository(context));
  }

  public FindUsersAgent findUsersAgent(Context context) {
    return new FindUsersAgentImp(provideUserRepository(context), provideUserDataModelMapper());
  }

  @NonNull
  private UserRepository provideUserRepository(Context context) {
    return new UserRepository(provideRandomUserApi(), provideRandomUserCache(context),
        provideRandomUserPreferences(context));
  }

  @NonNull
  private RandomUserPreferences provideRandomUserPreferences(Context context) {
    return RandomUserPreferences.getInstance(context);
  }

  @NonNull
  private UserDataModelMapper provideUserDataModelMapper() {
    return new UserDataModelMapper();
  }

  @NonNull
  private RandomUserApi provideRandomUserApi() {
    return new RandomUserApi();
  }

  @NonNull
  private RandomUserCache provideRandomUserCache(Context context) {
    return new RandomUserCache(provideFileManager(context));
  }

  @NonNull
  private FileManager provideFileManager(Context context) {
    return FileManager.getInstance(context);
  }
}
