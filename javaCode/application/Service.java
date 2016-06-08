package application;

import java.util.ArrayList;
import java.util.Random;


public class Service {
  public static int findByName(ArrayList<? extends INamedObject> names, String name) {
    for (int i = 0; i < names.size(); ++i) {
      if (names.get(i).getName().equals(name)) {
        return i;
      }
    }
    return -1;
  }

  public static INamedObject getINamedObject(ArrayList<? extends INamedObject> objects,
      String name) {
    for (int i = 0; i < objects.size(); ++i) {
      if (objects.get(i).getName().equals(name)) {
        return objects.get(i);
      }
    }
    return null;
  }

  public static int getRandomInt(int max) {
    Random rnd = new Random();
    return rnd.nextInt(max);
  }

  /**
   * i = 0, max = 3, return 1
   * i = 2, max = 3, return 0
   * @param i
   * @param max
   * @return
   */
  public static int cycleIncrement(int i, int max) {
    if (i + 1 == max) {
      i = 0;
    } else {
      i += 1;
    }
    return i;
  }

  public static void out(String str) {
    System.out.println(str);
  }

}
