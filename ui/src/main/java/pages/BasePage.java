package pages;

import driver_utils.BrowserManager;
import driver_utils.FrameManager;
import element_utils.SelenideElementActions;
import lombok.Getter;


public abstract class BasePage {
    @Getter
    public SelenideElementActions elementActions = new SelenideElementActions();
    @Getter
    public BrowserManager browserManager = new BrowserManager();
    @Getter
    public FrameManager frameManager = new FrameManager();
    private String url;

}
