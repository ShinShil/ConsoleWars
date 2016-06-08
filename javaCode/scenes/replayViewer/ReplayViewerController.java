package scenes.replayViewer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ReplayViewerController {
  VBox replayVerticalBox;
  VBox replayVerticalBoxGameLength;

  public ReplayViewerController(VBox replayVerticalBox, VBox replayVerticalBoxGameLength) {
    this.replayVerticalBox = replayVerticalBox;
    this.replayVerticalBoxGameLength = replayVerticalBoxGameLength;
  }

  public void refreshReplays(File[] replayFiles) {
    for (int i = 0; i < replayFiles.length; ++i) {
      ((Label) replayVerticalBox.getChildren().get(i)).setText(replayFiles[i].getName());
    }
  }

  public void refreshLengths(File[] replayFiles) throws IOException {
    for (int i = 0; i < replayFiles.length; ++i) {
      ((Label) replayVerticalBoxGameLength.getChildren().get(i))
          .setText(getGameLength(replayFiles[i]) + "");
    }
  }

  public void sortLengths(ArrayList<File> files) {
    Collections.sort(files, new Comparator<File>() {

      @Override
      public int compare(File arg0, File arg1) {
        long l1;
        long l2;
        try {
          l1 = getGameLength(arg0);
          l2 = getGameLength(arg1);
          if(l1 < l2)
            return -1;
          else if(l1 == l2)
            return 0;
          else
            return 1;
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
          return 0;
        }
      }
      
    });
  }

  public static Long getGameLength(File file) throws IOException {
    FileReader fr = new FileReader(file);
    char[] buffer = new char[(int) file.length()];
    fr.read(buffer);
    fr.close();
    int i = 0;
    String tmp = "";
    while (buffer[i] != '\n') {
      tmp += buffer[i];
      ++i;
    }
    String[] tmpList = tmp.split("/");
    long begin = Long.parseLong(tmpList[tmpList.length - 1]);
    i = buffer.length - 2;
    while (buffer[i] != '\n') {
      --i;
    }
    ++i;
    tmp = "";
    while (buffer[i] != '\n') {
      tmp += buffer[i];
      ++i;
    }
    tmpList = tmp.split("/");
    long end = Long.parseLong(tmpList[tmpList.length - 1]);


    return (end - begin);
  }

  public void sortByDate(ArrayList<File> files) {
    Collections.sort(files, new Comparator<File>() {

      @Override
      public int compare(File arg0, File arg1) {
        try {
          long time1 = getCreationTime(arg0).toMillis();
          long time2 = getCreationTime(arg1).toMillis();
          if (time1 < time2)
            return -1;
          else if (time1 > time2)
            return 1;
          else
            return 0;
        } catch (IOException e) {
          e.printStackTrace();
        }
        return 0;
      }

    });
  }

  public FileTime getCreationTime(File file) throws IOException {
    /*
     * val path:Path = Paths.get(file.getAbsolutePath) var basicView:BasicFileAttributeView =
     * Files.getFileAttributeView(path, classOf[BasicFileAttributeView]); var
     * basicAttr:BasicFileAttributes = basicView.readAttributes basicAttr.creationTime
     */
    Path path = Paths.get(file.getAbsolutePath());
    BasicFileAttributeView basicView =
        Files.getFileAttributeView(path, BasicFileAttributeView.class);
    BasicFileAttributes basicAttr = basicView.readAttributes();
    return basicAttr.creationTime();
  }

  public void swapFiles(File file1, File file2) {
    File tmp = file1;
    file1 = file2;
    file2 = tmp;
  }
}
