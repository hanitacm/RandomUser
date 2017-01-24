package randomuser.com.presentation.screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.domain.usecases.GetRandomUsersUseCase;
import java.util.List;
import randomuser.com.data.model.mapper.UserDataModelMapper;
import randomuser.com.data.repository.UserRepository;
import randomuser.com.data.repository.datasource.api.RandomUserApi;
import randomuser.com.presentation.R;
import randomuser.com.presentation.adapter.UserListAdapter;
import randomuser.com.presentation.model.UserViewModel;
import randomuser.com.presentation.model.mapper.UserViewModelMapper;
import randomuser.com.presentation.presenter.UserListPresenter;
import randomuser.com.presentation.ui.EndlessRecyclerViewScrollListener;

public class UserList extends AppCompatActivity implements UserListPresenter.UserListView {
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
    presenter = new UserListPresenter(new GetRandomUsersUseCase(
        new UserRepository(new RandomUserApi(), new UserDataModelMapper())),
        new UserViewModelMapper());
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
}
