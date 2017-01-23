package randomuser.com.randomuser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;

public class UserList extends AppCompatActivity implements UserListPresenter.UserListView {
  @BindView(R.id.user_list) RecyclerView userList;
  private UserListPresenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_list);

    initUI();
    createPresenter();
  }

  private void initUI() {
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
    presenter = new UserListPresenter();
  }

  @Override
  protected void onStart() {
    super.onStart();
    presenter.onStart(this);
  }

  @Override
  protected void onResume() {
    presenter.getRandomUsers();
  }

  @Override
  protected void onStop() {
    super.onStop();
    presenter.onStop();
  }
}
