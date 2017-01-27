package com.randomuser.presentation.screen;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.List;
import com.randomuser.presentation.R;
import com.randomuser.presentation.UserServiceLocator;
import com.randomuser.presentation.adapter.UserListAdapter;
import com.randomuser.presentation.model.UserViewModel;
import com.randomuser.presentation.presenter.UserListPresenter;
import com.randomuser.presentation.ui.EndlessRecyclerViewScrollListener;

public class UserList extends AppCompatActivity
    implements UserListPresenter.UserListView, UserListAdapter.OnItemClickListener,
    SearchView.OnQueryTextListener {

  @Bind(R.id.user_list) RecyclerView userList;
  @Bind(R.id.progress) FrameLayout loading;

  private UserListPresenter presenter;
  private UserListAdapter adapter;
  private EndlessRecyclerViewScrollListener endlessListener;

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
    adapter.setOnItemClickListener(this);

    userList.setAdapter(adapter);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    userList.setLayoutManager(layoutManager);
    userList.setItemAnimator(new DefaultItemAnimator());
    endlessListener = new EndlessRecyclerViewScrollListener(layoutManager) {
      @Override
      public void onLoadMore(int totalItemCount) {
        presenter.getMoreUsers();
      }
    };
    userList.addOnScrollListener(endlessListener);

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
    presenter.getUsers();
  }

  @Override
  protected void onDestroy() {
    super.onStop();
    adapter.clear();
    presenter.onStop();
  }

  @Override
  public void renderUserList(List<UserViewModel> users) {

    adapter.setUsers(users);
  }

  @Override
  public void navigateToUserDetail(UserViewModel user) {
    UserDetail.open(this, user.getName(), user.getSurname(), user.getEmail());
  }

  @Override
  public void deleteUserList(UserViewModel userSelected) {
    adapter.deleteItem(userSelected);
  }

  @Override
  public void showLoading() {

    loading.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoading() {

    loading.setVisibility(View.GONE);
  }

  @Override
  public void renderNoResults() {
    adapter.clear();
    Toast.makeText(this, R.string.No_users,Toast.LENGTH_LONG).show();
  }

  @Override
  public void showNetworkConnectionError() {
    Toast.makeText(this, R.string.No_connection,Toast.LENGTH_LONG).show();
  }

  @Override
  public void onClickUser(UserViewModel userSelected) {
    presenter.onClickUser(userSelected);
  }

  @Override
  public void onDeleteUser(UserViewModel userSelected) {
    presenter.onClickDeleteUser(userSelected);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);

    final MenuItem searchItem = menu.findItem(R.id.action_search);
    final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
    searchView.setOnQueryTextListener(this);
    searchView.clearFocus();
    searchView.setOnCloseListener(() -> {
      userList.addOnScrollListener(endlessListener);
      presenter.getUsers();
      return false;
    });

    return true;
  }

  @Override
  public boolean onQueryTextSubmit(String query) {
    return false;
  }

  @Override
  public boolean onQueryTextChange(String newText) {
    userList.removeOnScrollListener(endlessListener);
    presenter.onQueryTextChange(newText);

    return true;
  }
}
