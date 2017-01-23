package randomuser.com.randomuser;

public class UserListPresenter {

  private UserListView view;

  public UserListPresenter() {
  }

  void onStart(UserListView view) {
    this.view = view;
  }

  public void getRandomUsers() {

  }

  public void getMoreUsers() {

  }

  public void onStop() {
    this.view = null;
  }

  public interface UserListView {

  }
}
