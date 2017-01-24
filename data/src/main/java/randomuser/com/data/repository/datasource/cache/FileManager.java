package randomuser.com.data.repository.datasource.cache;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileManager {
  private final Context context;

  public FileManager(Context context) {
    this.context = context;
  }

  public static FileManager getInstance(Context context) {
    return new FileManager(context);
  }

  //public void write(File file, String fileContent) {
  //  if (!file.exists()) {
  //    try {
  //      FileWriter writer = new FileWriter(file);
  //      writer.write(fileContent);
  //      writer.close();
  //    } catch (IOException e) {
  //      e.printStackTrace();
  //    }
  //  }
  //
  //
  //}
  public void write(String fileName, Object fileContent) {
    FileOutputStream outputStream = null;
    ObjectOutputStream objectOutputStream = null;

    try {
      outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
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
      inputStream = context.openFileInput(fileName);
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

  //public String read(File file) {
  //  StringBuilder fileContentBuilder = new StringBuilder();
  //  if (file.exists()) {
  //    String stringLine;
  //    try {
  //      FileReader fileReader = new FileReader(file);
  //      BufferedReader bufferedReader = new BufferedReader(fileReader);
  //      while ((stringLine = bufferedReader.readLine()) != null) {
  //        fileContentBuilder.append(stringLine + "\n");
  //      }
  //      bufferedReader.close();
  //      fileReader.close();
  //    } catch (IOException e) {
  //      e.printStackTrace();
  //    }
  //  }
  //
  //  return fileContentBuilder.toString();
  //}
}
