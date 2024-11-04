package driver_utils;

import com.codeborne.selenide.Selenide;

public class FrameManager {

    public void switchToFrame(String frameName) {
        Selenide.switchTo().frame(frameName);
    }

    public void switchToParentFrame() {
        Selenide.switchTo().parentFrame();
    }
}