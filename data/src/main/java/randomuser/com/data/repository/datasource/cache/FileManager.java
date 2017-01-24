package randomuser.com.data.repository.datasource.cache;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import randomuser.com.data.model.UserDataModel;
import randomuser.com.data.model.UserDataModelCollection;
import rx.Observable;

public class FileManager {
  private final Context context;

  private FileManager(Context context) {
    this.context = context;
  }

  public static FileManager getInstance(Context context) {
    return new FileManager(context);
  }

  public void write(String fileName, Object fileContent) {
    FileOutputStream outputStream = null;
    ObjectOutputStream objectOutputStream = null;

    try {
      outputStream = new FileOutputStream(new File(context.getFilesDir() + "/" + fileName));
      objectOutputStream = new ObjectOutputStream(outputStream);
      objectOutputStream.writeObject(fileContent);
      objectOutputStream.close();
      outputStream.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        objectOutputStream.close();
        outputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public Object read(String fileName) {
    return getObjectFromFile(new File(context.getFilesDir() + "/" + fileName));
  }

  @Nullable
  private Object getObjectFromFile(File file) {
    FileInputStream inputStream = null;
    ObjectInputStream objectInputStream = null;
    Object outObject = null;
    try {
      inputStream = new FileInputStream(file);
      objectInputStream = new ObjectInputStream(inputStream);
      outObject = objectInputStream.readObject();
      objectInputStream.close();
      inputStream.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        objectInputStream.close();
        inputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return outObject;
  }

  public Observable<UserDataModelCollection> readAllUsers() {
    File[] files = context.getFilesDir().listFiles();

   return  getUserDataModelCollectionFromDir(files);
  }

  public Observable<Boolean> delete(String name) {

    File[] files = context.getFilesDir().listFiles((file, s) -> {
      return s.equals(name);
    });

    boolean result = false;

    if (files.length > 0) {
      result = files[0].delete();
    }

    return Observable.just(result);
  }

  public Observable<UserDataModelCollection> findUsers(String queryText) {
    File[] files = context.getFilesDir().listFiles((file, s) -> {
      return s.contains(queryText);
    });

    return getUserDataModelCollectionFromDir(files);
  }

  @NonNull
  private Observable<UserDataModelCollection> getUserDataModelCollectionFromDir(File[] files) {
    List<UserDataModel> list = new ArrayList<>();
    for (File file : files) {
      list.add((UserDataModel) getObjectFromFile(file));
    }
    UserDataModelCollection users = new UserDataModelCollection();
    users.setResults(list);

    return Observable.just(users);
  }
}



