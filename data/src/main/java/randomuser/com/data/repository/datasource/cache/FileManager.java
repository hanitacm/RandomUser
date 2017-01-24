package randomuser.com.data.repository.datasource.cache;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    FileInputStream inputStream = null;
    ObjectInputStream objectInputStream = null;
    Object outObject = null;

    try {
      inputStream = new FileInputStream(new File(context.getFilesDir() + "/" + fileName));
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
}



