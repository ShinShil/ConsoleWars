package scenes.symphony;


public interface ISizeSymphonyDraw {

  int dummyAmount = 9;
  int dummyW = 80;
  int dummyH = 80;
  int dummyFont = 24;
  int dummyInsetsPadding = 4;
  int canvasH = dummyH * 8;
  int infoW = 200;
  int infoH = dummyH * 2 + canvasH;
  int rootW = dummyW * dummyAmount + infoW;
  int canvasW = dummyW * dummyAmount;
  int clickH = dummyH;
  int clickW = infoW / 4;
  int towerAmount = 9;
  int inputH = 25;
  int rootH = canvasH + dummyH + inputH;

  String[] clickImg =
      new String[] {"file:resources/theme/fireClick.jpg", "file:resources/theme/waterClick.jpg",
          "file:resources/theme/earthClick.jpg", "file:resources/theme/windClick.jpg"};
}
