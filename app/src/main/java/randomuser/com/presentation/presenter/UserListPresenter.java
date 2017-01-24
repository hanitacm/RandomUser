package randomuser.com.presentation.presenter;

import android.util.Log;
import com.domain.model.UserModelCollection;
import com.domain.usecases.GetRandomUsersUseCase;
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

  public UserListPresenter(GetRandomUsersUseCase getRandomUsersUseCase,
      UserViewModelMapper userViewModelMapper) {
    this.getRandomUsersUseCase = getRandomUsersUseCase;
    this.userViewModelMapper = userViewModelMapper;
  }

  public void onStart(UserListView view) {
    this.view = view;
  }

  public void getRandomUsers() {
    getRandomUserSubscription = getRandomUsersUseCase.getRandomUsers()
        .subscribeOn(schedulerSubscribe)
        .observeOn(scheduler)
        .subscribe(new Subscriber<UserModelCollection>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {
            Log.e("RandomUser", e.getMessage());
          }

          @Override
          public void onNext(UserModelCollection userModelCollection) {
            if (userModelCollection.getUsers().size() > 0) {
              view.renderUserList(userViewModelMapper.call(userModelCollection));
            }
          }
        });
  }

  public void getMoreUsers() {

  }

  public void onStop() {
    this.view = null;
    if (getRandomUserSubscription != null) getRandomUserSubscription.unsubscribe();

  }

  public void onClickUser(UserViewModel selectedUser) {
    view.navigateToUserDetail(selectedUser.getEmail());
  }

  public interface UserListView {

    void renderUserList(List<UserViewModel> users);

    void navigateToUserDetail(String userId);
  }
}
