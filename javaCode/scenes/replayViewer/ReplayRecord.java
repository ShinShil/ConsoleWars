package scenes.replayViewer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReplayRecord {
  protected String name;
  protected long duration;
  
  public ReplayRecord(File file) throws IOException {
    name = file.getName();
    FileReader reader = new FileReader(file);
   
    reader.close();
  }
  
  public int compareNames(ReplayRecord other) {
    return name.compareToIgnoreCase(name);
  }
  
  public boolean compareDurations(ReplayRecord other) {
    return duration > other.duration ? true : false;
  }
}