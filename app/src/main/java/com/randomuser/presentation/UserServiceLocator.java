package com.randomuser.presentation;

import android.content.Context;
import android.support.annotation.NonNull;
import com.domain.usecases.DeleteUserUseCase;
import com.domain.usecases.DeleteUserUseCaseImp;
import com.domain.usecases.GetRandomUsersUseCase;
import com.domain.usecases.GetRandomUsersUseCaseImp;
import com.domain.usecases.GetUserDetailUseCaseImp;
import com.domain.usecases.GetUsersUseCase;
import com.domain.usecases.GetUsersUseCaseImp;
import com.domain.usecases.SearchUsersUseCase;
import com.domain.usecases.SearchUsersUseCaseImp;
import com.randomuser.data.UserAgentLocator;
import com.randomuser.presentation.model.mapper.UserDetailViewModelMapper;
import com.randomuser.presentation.model.mapper.UserViewModelMapper;
import com.randomuser.presentation.presenter.UserDetailPresenter;
import com.randomuser.presentation.presenter.UserListPresenter;

public class UserServiceLocator {
  private final Context context;

  public UserServiceLocator(Context context) {
    this.context = context;
  }

  public UserListPresenter getUserListPresenter() {
    return new UserListPresenter(provideGetRandomUsersUseCase(), provideUserViewModelMapper(),
        provideDeleteUserUseCase(), provideSearchUsersUseCase(), provideGetUsersUseCase());
  }

  @NonNull
  private GetUsersUseCase provideGetUsersUseCase() {
    return new GetUsersUseCaseImp(provideUserAgentLocator().getUsersAgent(context),
    provideUserAgentLocator().getRemoteUsersAgent(context));
  }

  @NonNull
  private SearchUsersUseCase provideSearchUsersUseCase() {
    return new SearchUsersUseCaseImp(provideUserAgentLocator().findUsersAgent(context));
  }

  @NonNull
  private UserAgentLocator provideUserAgentLocator() {
    return new UserAgentLocator();
  }

  @NonNull
  private DeleteUserUseCase provideDeleteUserUseCase() {
    return new DeleteUserUseCaseImp(provideUserAgentLocator().deleteUserAgent(context));
  }

  public UserDetailPresenter getUserDetailPresenter() {
    return new UserDetailPresenter(
        new GetUserDetailUseCaseImp(provideUserAgentLocator().getUserDetailAgent(context)),
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
    return new GetRandomUsersUseCaseImp(provideUserAgentLocator().getRemoteUsersAgent(context));
  }
}
