package com.randomuser.presentation.presenter;

import com.domain.model.UserModel;
import com.domain.usecases.DeleteUserUseCase;
import com.domain.usecases.GetRandomUsersUseCase;
import com.domain.usecases.GetUsersUseCase;
import com.domain.usecases.SearchUsersUseCase;
import java.net.UnknownHostException;
import java.util.List;
import com.randomuser.presentation.model.UserViewModel;
import com.randomuser.presentation.model.mapper.UserViewModelMapper;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserListPresenter {

  private static final String EMPTY_STRING = "";
  private final GetRandomUsersUseCase getRandomUsersUseCase;
  private final UserViewModelMapper userViewModelMapper;
  private final DeleteUserUseCase deleteUserUseCase;
  private SearchUsersUseCase searchUsersUseCase;
  private UserListView view;
  private Scheduler schedulerSubscribe;
  private Scheduler scheduler;
  private Subscription getRandomUserSubscription;
  private Subscription deleteUserSubscription;
  private Subscription searchUserSubscription;
  private GetUsersUseCase getUsersUseCase;
  private Subscription getUserSubscription;

  public UserListPresenter(GetRandomUsersUseCase getRandomUsersUseCase,
      UserViewModelMapper userViewModelMapper, DeleteUserUseCase deleteUserUseCase,
      SearchUsersUseCase searchUsersUseCase, GetUsersUseCase getUsersUseCase) {
    this.getRandomUsersUseCase = getRandomUsersUseCase;
    this.userViewModelMapper = userViewModelMapper;
    this.deleteUserUseCase = deleteUserUseCase;
    this.searchUsersUseCase = searchUsersUseCase;
    this.getUsersUseCase = getUsersUseCase;
    this.schedulerSubscribe = Schedulers.io();
    this.scheduler = AndroidSchedulers.mainThread();
  }

  public UserListPresenter(GetRandomUsersUseCase getRandomUsersUseCase,
      UserViewModelMapper userViewModelMapper, DeleteUserUseCase deleteUserUseCase,
      SearchUsersUseCase searchUsersUseCase, GetUsersUseCase getUsersUseCase,
      Scheduler schedulerSubscribe, Scheduler schedulerObserve) {
    this.getRandomUsersUseCase = getRandomUsersUseCase;
    this.userViewModelMapper = userViewModelMapper;
    this.deleteUserUseCase = deleteUserUseCase;
    this.searchUsersUseCase = searchUsersUseCase;
    this.getUsersUseCase = getUsersUseCase;
    this.schedulerSubscribe = schedulerSubscribe;
    this.scheduler = schedulerObserve;
  }

  public void onStart(UserListView view) {
    this.view = view;
  }

  public void getUsers() {
    view.showLoading();
    getUserSubscription = getUsersUseCase.getUsers()
        .subscribeOn(schedulerSubscribe)
        .observeOn(scheduler)
        .subscribe(new Subscriber<List<UserModel>>() {
                     @Override
                     public void onCompleted() {

                     }

                     @Override
                     public void onError(Throwable e) {

                       view.hideLoading();
                       if (e instanceof UnknownHostException) {
                         view.showNetworkConnectionError();
                       }
                     }

                     @Override
                     public void onNext(List<UserModel> userModelCollection) {
                       view.hideLoading();
                       showUsers(userModelCollection);
                     }
                   }

        );
  }

  public void getMoreUsers() {
    view.showLoading();
    getRandomUserSubscription = getRandomUsersUseCase.getRandomUsers()
        .subscribeOn(schedulerSubscribe)
        .observeOn(scheduler)
        .subscribe(new Subscriber<List<UserModel>>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {

            view.hideLoading();
            if (e instanceof UnknownHostException) {
              view.showNetworkConnectionError();
            }
          }

          @Override
          public void onNext(List<UserModel> userModelCollection) {
            view.hideLoading();
            showUsers(userModelCollection);
          }
        });
  }

  public void onStop() {
    this.view = null;
    if (getRandomUserSubscription != null) getRandomUserSubscription.unsubscribe();
    if (deleteUserSubscription != null) deleteUserSubscription.unsubscribe();
    if (searchUserSubscription != null) searchUserSubscription.unsubscribe();
    if (getUserSubscription != null) getUserSubscription.unsubscribe();
  }

  public void onClickUser(UserViewModel selectedUser) {
    view.navigateToUserDetail(selectedUser);
  }

  public void onClickDeleteUser(UserViewModel userSelected) {

    deleteUserSubscription =
        deleteUserUseCase.deleteUser(userSelected.getName(), userSelected.getSurname(),
            userSelected.getEmail())
            .subscribeOn(schedulerSubscribe)
            .observeOn(scheduler)
            .subscribe(aBoolean -> {
              if (aBoolean) {
                view.deleteUserList(userSelected);
              }
            });
  }

  public void onQueryTextChange(String queryText) {
    if (!queryText.equals(EMPTY_STRING)) {
      view.showLoading();
      searchUserSubscription = searchUsersUseCase.searchUsers(queryText)
          .subscribeOn(schedulerSubscribe)
          .observeOn(scheduler)
          .subscribe(this::showUsers);
      view.hideLoading();
    } else {
      getUsers();
    }
  }

  private void showUsers(List<UserModel> userModelCollection) {
    if (userModelCollection.size() > 0) {
      view.renderUserList(userViewModelMapper.call(userModelCollection));
    } else {
      view.renderNoResults();
    }
  }

  public interface UserListView {

    void renderUserList(List<UserViewModel> users);

    void navigateToUserDetail(UserViewModel user);

    void deleteUserList(UserViewModel userSelected);

    void showLoading();

    void hideLoading();

    void renderNoResults();

    void showNetworkConnectionError();
  }
}
