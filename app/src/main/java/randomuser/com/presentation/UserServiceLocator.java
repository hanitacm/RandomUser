package randomuser.com.presentation;

import android.content.Context;
import android.support.annotation.NonNull;
import com.domain.usecases.DeleteUserUseCase;
import com.domain.usecases.GetRandomUsersUseCase;
import com.domain.usecases.GetUserDetailUseCase;
import randomuser.com.data.model.mapper.UserDataModelMapper;
import randomuser.com.data.repository.UserRepository;
import randomuser.com.data.repository.datasource.api.RandomUserApi;
import randomuser.com.data.repository.datasource.cache.FileManager;
import randomuser.com.data.repository.datasource.cache.RandomUserCache;
import randomuser.com.data.repository.datasource.sharedPreferences.RandomUserPreferences;
import randomuser.com.presentation.model.mapper.UserDetailViewModelMapper;
import randomuser.com.presentation.model.mapper.UserViewModelMapper;
import randomuser.com.presentation.presenter.UserDetailPresenter;
import randomuser.com.presentation.presenter.UserListPresenter;

public class UserServiceLocator {
  private final Context context;

  public UserServiceLocator(Context context) {
    this.context = context;
  }

  public UserListPresenter getUserListPresenter() {
    return new UserListPresenter(provideGetRandomUsersUseCase(), provideUserViewModelMapper(),
        provideDeleteUserUseCase());
  }

  @NonNull
  private DeleteUserUseCase provideDeleteUserUseCase() {
    return new DeleteUserUseCase(provideUserRepository());
  }

  public UserDetailPresenter getUserDetailPresenter() {
    return new UserDetailPresenter(new GetUserDetailUseCase(provideUserRepository()),
        provideUserDetailViewModelMapper());
  }

  @NonNull
  private UserDetailViewModelMapper provideUserDetailViewModelMapper() {
    return new UserDetailViewModelMapper();
  }

  @NonNull
  private UserViewModelMapper provideUserViewModelMapper() {
    return new UserViewModelMapper();
  }

  @NonNull
  private GetRandomUsersUseCase provideGetRandomUsersUseCase() {
    return new GetRandomUsersUseCase(provideUserRepository());
  }

  @NonNull
  private UserRepository provideUserRepository() {
    return new UserRepository(provideRandomUserApi(), provideUserDataModelMapper(),
        provideRandomUserCache(), provideRandomUserPreferences());
  }

  @NonNull
  private RandomUserPreferences provideRandomUserPreferences() {
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
  private RandomUserCache provideRandomUserCache() {
    return new RandomUserCache(provideFileManager());
  }

  @NonNull
  private FileManager provideFileManager() {
    return FileManager.getInstance(context);
  }
}
