package com.randomuser.presentation;

import android.support.annotation.NonNull;
import com.domain.model.UserModel;
import com.domain.usecases.DeleteUserUseCase;
import com.domain.usecases.GetRandomUsersUseCase;
import com.domain.usecases.GetUsersUseCase;
import com.domain.usecases.SearchUsersUseCase;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import com.randomuser.presentation.model.UserViewModel;
import com.randomuser.presentation.model.mapper.UserViewModelMapper;
import com.randomuser.presentation.presenter.UserListPresenter;
import rx.Observable;
import rx.schedulers.Schedulers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserListPresenterShould {
  private static final String EMPTY_QUERY_TEXT = "";
  private static final String QUERY_TEXT = "jenny";
  private UserListPresenter presenter;
  @Mock private UserListPresenter.UserListView view;
  @Mock private GetRandomUsersUseCase getRandomUsersUseCase;
  @Mock private UserViewModelMapper userViewModelMapper;
  @Mock private DeleteUserUseCase deleteUserUseCase;
  @Mock private SearchUsersUseCase searchUsersUseCase;
  @Mock private GetUsersUseCase getUsersUseCase;

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    this.presenter =
        new UserListPresenter(getRandomUsersUseCase, userViewModelMapper, deleteUserUseCase,
            searchUsersUseCase, getUsersUseCase, Schedulers.immediate(), Schedulers.immediate());

    presenter.onStart(view);
  }

  @Test
  public void get_users_and_render_on_view() {
    rx.Observable<List<UserModel>> userModels = givenAUserModelListOf(1);
    List<UserViewModel> userViewModels = new ArrayList<>();

    given(getUsersUseCase.getUsers()).willReturn(userModels);
    given(userViewModelMapper.call(userModels.toBlocking().first())).willReturn(userViewModels);

    presenter.getUsers();

    verify(getUsersUseCase, times(1)).getUsers();

    verifyRenderUserListResults(userModels, userViewModels);
  }

  @Test
  public void search_users_and_render_results_on_view() {
    rx.Observable<List<UserModel>> userModels = givenAUserModelListOf(1);
    List<UserViewModel> userViewModels = new ArrayList<>();

    given(searchUsersUseCase.searchUsers(QUERY_TEXT)).willReturn(userModels);

    presenter.onQueryTextChange(QUERY_TEXT);

    verify(searchUsersUseCase, times(1)).searchUsers(QUERY_TEXT);
    verifyRenderUserListResults(userModels, userViewModels);
  }

  @Test
  public void search_all_users_if_text_query_is_empty() throws Exception {
    rx.Observable<List<UserModel>> userModels = givenAUserModelListOf(1);
    List<UserViewModel> userViewModels = new ArrayList<>();

    given(getUsersUseCase.getUsers()).willReturn(userModels);
    given(userViewModelMapper.call(userModels.toBlocking().first())).willReturn(userViewModels);

    presenter.onQueryTextChange(EMPTY_QUERY_TEXT);

    verify(getUsersUseCase, times(1)).getUsers();
    verifyZeroInteractions(searchUsersUseCase);
    verifyRenderUserListResults(userModels, userViewModels);
  }

  @Test
  public void show_message_no_results_when_there_are_not_users_to_show() {

    rx.Observable<List<UserModel>> userModels = givenAUserModelListOf(0);

    given(getUsersUseCase.getUsers()).willReturn(userModels);

    presenter.getUsers();

    verify(view, times(1)).showLoading();
    verifyZeroInteractions(userViewModelMapper);
    verify(view, times(1)).renderNoResults();
    verify(view, times(1)).hideLoading();
  }

  @Test
  public void navigate_to_user_detail_when_user_click_on_photo() {
    UserViewModel userViewModel = givenAUserViewModel();

    presenter.onClickUser(userViewModel);

    verify(view, times(1)).navigateToUserDetail(userViewModel);
  }

  @Test
  public void delete_user_when_user_click_on_delete_icon() {
    UserViewModel userViewModel = givenAUserViewModel();

    given(deleteUserUseCase.deleteUser(userViewModel.getName(), userViewModel.getSurname(),
        userViewModel.getEmail())).willReturn(Observable.just(true));

    presenter.onClickDeleteUser(userViewModel);

    verify(deleteUserUseCase, times(1)).deleteUser(userViewModel.getName(),
        userViewModel.getSurname(), userViewModel.getEmail());
    verify(view, times(1)).deleteUserList(userViewModel);
  }

  @Test
  public void get_remote_users_when_user_ask_for_more_results() {
    rx.Observable<List<UserModel>> userModels = givenAUserModelListOf(1);
    List<UserViewModel> userViewModels = new ArrayList<>();

    given(getRandomUsersUseCase.getRandomUsers()).willReturn(userModels);
    given(userViewModelMapper.call(userModels.toBlocking().first())).willReturn(userViewModels);

    presenter.getMoreUsers();

    verify(getRandomUsersUseCase, times(1)).getRandomUsers();

    verifyRenderUserListResults(userModels, userViewModels);
  }

  @NonNull
  private UserModel givenAUserModel() {
    return new UserModel();
  }

  @NonNull
  private UserViewModel givenAUserViewModel() {
    return new UserViewModel();
  }

  private void verifyRenderUserListResults(Observable<List<UserModel>> userModels,
      List<UserViewModel> userViewModels) {
    verify(view, times(1)).showLoading();
    verify(userViewModelMapper, times(1)).call(userModels.toBlocking().first());
    verify(view, times(1)).renderUserList(userViewModels);
    verify(view, times(1)).hideLoading();
  }

  @NonNull
  private rx.Observable<List<UserModel>> givenAUserModelListOf(int elements) {
    List<UserModel> userModelList = new ArrayList<>();

    for (int i = 0; i < elements; i++) {
      userModelList.add(givenAUserModel());
    }
    return rx.Observable.just(userModelList);
  }
}