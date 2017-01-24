package randomuser.com.presentation.screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.List;
import randomuser.com.presentation.R;
import randomuser.com.presentation.UserServiceLocator;
import randomuser.com.presentation.adapter.UserListAdapter;
import randomuser.com.presentation.model.UserViewModel;
import randomuser.com.presentation.presenter.UserListPresenter;
import randomuser.com.presentation.ui.EndlessRecyclerViewScrollListener;

public class UserList extends AppCompatActivity
    implements UserListPresenter.UserListView, UserListAdapter.OnItemClickListener {
  @Bind(R.id.user_list) RecyclerView userList;
  private UserListPresenter presenter;
  private UserListAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initUI();
    createPresenter();
  }

  private void initUI() {
    setContentView(R.layout.activity_user_list);
    ButterKnife.bind(this);

    adapter = new UserListAdapter(this);
    adapter.setHasStableIds(true);
    adapter.setOnItemClickListener(this);

    userList.setAdapter(adapter);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    userList.setLayoutManager(layoutManager);
    userList.setItemAnimator(new DefaultItemAnimator());
    userList.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
      @Override
      public void onLoadMore(int totalItemCount) {
        presenter.getMoreUsers();
      }
    });
  }

  private void createPresenter() {
    UserServiceLocator userServiceLocator = new UserServiceLocator(getApplicationContext());
    presenter = userServiceLocator.getUserListPresenter();
  }

  @Override
  protected void onStart() {
    super.onStart();
    presenter.onStart(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    presenter.getRandomUsers();
  }

  @Override
  protected void onStop() {
    super.onStop();
    presenter.onStop();
  }

  @Override
  public void renderUserList(List<UserViewModel> users) {

    adapter.setUsers(users);
  }

  @Override
  public void navigateToUserDetail(String userId) {
    UserDetail.open(this, userId);
  }

  @Override
  public void deleteUserList(UserViewModel userSelected) {
    adapter.deleteItem(userSelected);
  }

  @Override
  public void onClickUser(UserViewModel userSelected) {
    presenter.onClickUser(userSelected);
  }

  @Override
  public void onDeleteUser(UserViewModel userSelected) {
    presenter.onClickDeleteUser(userSelected);
  }
}
