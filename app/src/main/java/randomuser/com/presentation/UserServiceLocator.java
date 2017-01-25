package randomuser.com.presentation;

import android.content.Context;
import android.support.annotation.NonNull;
import com.domain.usecases.DeleteUserUseCase;
import com.domain.usecases.GetRandomUsersUseCase;
import com.domain.usecases.GetUserDetailUseCase;
import com.domain.usecases.SearchUsersUseCase;
import randomuser.com.data.UserAgentLocator;
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
        provideDeleteUserUseCase(), provideSearchUsersUseCase());
  }

  @NonNull
  private SearchUsersUseCase provideSearchUsersUseCase() {
    return new SearchUsersUseCase(provideUserAgentLocator().findUsersAgent(context));
  }

  @NonNull
  private UserAgentLocator provideUserAgentLocator() {
    return new UserAgentLocator();
  }

  @NonNull
  private DeleteUserUseCase provideDeleteUserUseCase() {
    return new DeleteUserUseCase(provideUserAgentLocator().deleteUserAgent(context));
  }

  public UserDetailPresenter getUserDetailPresenter() {
    return new UserDetailPresenter(
        new GetUserDetailUseCase(provideUserAgentLocator().getUserDetailAgent(context)),
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
    return new GetRandomUsersUseCase(provideUserAgentLocator().getUsersAgent(context));
  }
}
