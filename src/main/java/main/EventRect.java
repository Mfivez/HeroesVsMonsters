package main;

import java.awt.*;

public class EventRect extends Rectangle {

    // region ATTRIBUTS
    int eventRectDefaultX, eventRectDefaultY;
    boolean eventDone = false;

    // endregion


    public EventRect(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
}
