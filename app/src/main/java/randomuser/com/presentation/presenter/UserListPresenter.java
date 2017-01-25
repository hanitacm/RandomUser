package randomuser.com.presentation.presenter;

import android.util.Log;
import com.domain.model.UserModel;
import com.domain.usecases.DeleteUserUseCase;
import com.domain.usecases.GetRandomUsersUseCase;
import com.domain.usecases.SearchUsersUseCase;
import java.util.List;
import randomuser.com.presentation.model.UserViewModel;
import randomuser.com.presentation.model.mapper.UserViewModelMapper;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserListPresenter {

  private final GetRandomUsersUseCase getRandomUsersUseCase;
  private final UserViewModelMapper userViewModelMapper;
  private UserListView view;
  private Scheduler schedulerSubscribe = Schedulers.io();
  private Scheduler scheduler = AndroidSchedulers.mainThread();
  private Subscription getRandomUserSubscription;
  private DeleteUserUseCase deleteUserUseCase;
  private Subscription deleteUserSubscription;
  private SearchUsersUseCase searchUsersUseCase;
  private Subscription searchUserSubscription;

  public UserListPresenter(GetRandomUsersUseCase getRandomUsersUseCase,
      UserViewModelMapper userViewModelMapper, DeleteUserUseCase deleteUserUseCase,
      SearchUsersUseCase searchUsersUseCase) {
    this.getRandomUsersUseCase = getRandomUsersUseCase;
    this.userViewModelMapper = userViewModelMapper;
    this.deleteUserUseCase = deleteUserUseCase;
    this.searchUsersUseCase = searchUsersUseCase;
  }

  public void onStart(UserListView view) {
    this.view = view;
  }

  public void getRandomUsers() {
    getRandomUserSubscription = getRandomUsersUseCase.getRandomUsers()
        .subscribeOn(schedulerSubscribe)
        .observeOn(scheduler)
        .subscribe(new Subscriber<List<UserModel>>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {
            Log.e("RandomUser", e.getMessage());
          }

          @Override
          public void onNext(List<UserModel> userModelCollection) {
            showUsers(userModelCollection);
          }
        });
  }

  public void onStop() {
    this.view = null;
    if (getRandomUserSubscription != null) getRandomUserSubscription.unsubscribe();
    if (deleteUserSubscription != null) deleteUserSubscription.unsubscribe();
    if (searchUserSubscription != null) searchUserSubscription.unsubscribe();
  }

  public void onClickUser(UserViewModel selectedUser) {
    view.navigateToUserDetail(
        selectedUser.getName().replace(" ", "_") + "_" + selectedUser.getEmail());
  }

  public void onClickDeleteUser(UserViewModel userSelected) {
    deleteUserSubscription = deleteUserUseCase.deleteUser(
        userSelected.getName().replace(" ", "_") + "_" + userSelected.getEmail())
        .subscribeOn(schedulerSubscribe)
        .observeOn(scheduler)
        .subscribe(aBoolean -> {
          if (aBoolean) {
            view.deleteUserList(userSelected);
          }
        });
  }

  public void onQueryTextChange(String queryText) {
    if (!queryText.equals("")) {
      searchUserSubscription = searchUsersUseCase.searchUsers(queryText)
          .subscribeOn(schedulerSubscribe)
          .observeOn(scheduler)
          .subscribe(this::showUsers);
    } else {
      getRandomUsers();
    }
  }

  private void showUsers(List<UserModel> userModelCollection) {
    if (userModelCollection.size() > 0) {
      view.renderUserList(userViewModelMapper.call(userModelCollection));
    }
  }

  public interface UserListView {

    void renderUserList(List<UserViewModel> users);

    void navigateToUserDetail(String userId);

    void deleteUserList(UserViewModel userSelected);
  }
}
