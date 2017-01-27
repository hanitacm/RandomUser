package com.randomuser.data;

import android.content.Context;
import android.support.annotation.NonNull;
import com.domain.respository.DeleteUserAgent;
import com.domain.respository.FindUsersAgent;
import com.domain.respository.GetUserDetailAgent;
import com.domain.respository.GetRemoteUsersAgent;
import com.domain.respository.GetUsersAgent;
import com.randomuser.data.agent.DeleteUserAgentImp;
import com.randomuser.data.agent.FindUsersAgentImp;
import com.randomuser.data.agent.GetUserDetailAgentImp;
import com.randomuser.data.agent.GetRemoteUsersAgentImp;
import com.randomuser.data.agent.GetUsersAgentImp;
import com.randomuser.data.model.mapper.UserDataModelMapper;
import com.randomuser.data.repository.UserRepository;
import com.randomuser.data.repository.datasource.api.RandomUserApi;
import com.randomuser.data.repository.datasource.cache.FileManager;
import com.randomuser.data.repository.datasource.cache.RandomUserCache;
import com.randomuser.data.repository.datasource.sharedPreferences.RandomUserPreferences;

public class UserAgentLocator {
  public GetRemoteUsersAgent getRemoteUsersAgent(Context context) {
    return new GetRemoteUsersAgentImp(provideUserRepository(context), provideUserDataModelMapper());
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

  public GetUsersAgent getUsersAgent(Context context) {
    return new GetUsersAgentImp(provideUserRepository(context), provideUserDataModelMapper());
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
