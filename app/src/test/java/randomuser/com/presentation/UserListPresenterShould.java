package randomuser.com.presentation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import randomuser.com.presentation.presenter.UserListPresenter;

import static org.mockito.MockitoAnnotations.initMocks;

public class UserListPresenterShould {
  private UserListPresenter presenter;
  @Mock private UserListPresenter.UserListView view;

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    this.presenter = new UserListPresenter();

    presenter.onStart(view);

  }

  @Test
  public void getRandomUsersList() {

    presenter.getRandomUsers();

  }

}