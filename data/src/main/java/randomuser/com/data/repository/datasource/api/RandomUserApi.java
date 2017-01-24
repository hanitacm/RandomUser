package randomuser.com.data.repository.datasource.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import randomuser.com.data.model.UserDataModelCollection;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class RandomUserApi {
  private static final int RESULTS = 40;
  private static final String API_URL = "http://api.randomuser.me/";

  public Observable<UserDataModelCollection> getRandomUsers() {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    //// set your desired log level
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    //
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    //// add your other interceptors …
    //
    //// add logging as last interceptor
    httpClient.addInterceptor(logging);
    //Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    Retrofit retrofit = new Retrofit.Builder().baseUrl(API_URL)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build();

    UserService userService = retrofit.create(UserService.class);

    return userService.getRandomUsers(RESULTS);
  }
}


