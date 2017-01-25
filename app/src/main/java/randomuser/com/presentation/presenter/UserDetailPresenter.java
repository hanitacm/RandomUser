package randomuser.com.presentation.presenter;

import com.domain.usecases.GetUserDetailUseCase;
import randomuser.com.presentation.model.UserDetailViewModel;
import randomuser.com.presentation.model.mapper.UserDetailViewModelMapper;
import randomuser.com.presentation.screen.UserDetail;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserDetailPresenter {
  private GetUserDetailUseCase getUserDetailUseCase;
  private Scheduler schedulerSubscribe = Schedulers.io();
  private Scheduler scheduler = AndroidSchedulers.mainThread();
  private UserDetail view;
  private Subscription getUserDetailSubscription;
  private UserDetailViewModelMapper userDetailViewModelMapper;

  public UserDetailPresenter(GetUserDetailUseCase getUserDetailUseCase,
      UserDetailViewModelMapper userDetailViewModelMapper) {
    this.getUserDetailUseCase = getUserDetailUseCase;
    this.userDetailViewModelMapper = userDetailViewModelMapper;
  }

  public void onStart(UserDetail view) {
    this.view = view;
  }

  public void onStop() {
    this.view = null;
    if (getUserDetailSubscription != null) getUserDetailSubscription.unsubscribe();
  }

  public void getUserDetail(String name, String surname, String email) {
    getUserDetailSubscription = getUserDetailUseCase.getUserDetail(name, surname, email)
        .map(userDetailViewModelMapper)
        .subscribeOn(schedulerSubscribe)
        .observeOn(scheduler)
        .subscribe(user -> {
          view.renderUserDetail(user);
        });
  }

  public interface UserDetailView {

    void renderUserDetail(UserDetailViewModel user);
  }
}
