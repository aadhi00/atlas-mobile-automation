package com.atlasmobile.pages;


import com.google.common.collect.ImmutableList;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * @author aadhithyan.nagarajan
 */
public class BasePage extends MobilePageObject {

    protected WebDriver mDriver;

    protected List<Boolean> result;

    private Dimension windowSize;

    private static final Duration SCROLL_DUR = Duration.ofMillis(1000);

    private static final double SCROLL_RATIO = 0.8;

    private static final int ANDROID_SCROLL_DIVISOR = 3;

    public BasePage(WebDriver mDriver) {
        super(mDriver);
        this.mDriver = mDriver;
        PageFactory.initElements(new AppiumFieldDecorator(getDriver(), Duration.ofSeconds(15)), this);
    }

    public WebDriver getmDriver(){
        return this.mDriver;
    }

    protected void tap(final WebElement element){
        TapOptions tapOptions = new TapOptions();
        tapOptions.withTapsCount(1).withElement(ElementOption.element(element));
    }

    protected void doubleTap(final WebElement element) {
        TapOptions tapOptions = new TapOptions();
        tapOptions.withTapsCount(2).withElement(ElementOption.element(element));
    }

    public enum ScrollDirection {
        UP, DOWN, LEFT, RIGHT
    }

    private Dimension getWindowSize() {
        if (windowSize == null) {
            windowSize = getmDriver().manage().window().getSize();
        }
        return windowSize;
    }

    protected void swipe(Point start, Point end, Duration duration) {
        AppiumDriver d = (AppiumDriver) getmDriver();
        boolean isAndroid = d instanceof AndroidDriver;

        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence swipe = new Sequence(input, 0);
        swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y));
        swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        if (isAndroid) {
            duration = duration.dividedBy(ANDROID_SCROLL_DIVISOR);
        } else {
            swipe.addAction(new Pause(input, duration));
            duration = Duration.ZERO;
        }
        swipe.addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y));
        swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        d.perform(ImmutableList.of(swipe));
    }

    protected void swipe(double startXPct, double startYPct, double endXPct, double endYPct, Duration duration) {
        Dimension size = getWindowSize();
        Point start = new Point((int)(size.width * startXPct), (int)(size.height * startYPct));
        Point end = new Point((int)(size.width * endXPct), (int)(size.height * endYPct));
        swipe(start, end, duration);
    }

    protected void scroll(ScrollDirection dir, double distance){
        if (distance < 0 || distance > 1) {
            throw new Error("Scroll distance must be between 0 and 1");
        }
        Dimension size = getWindowSize();
        Point midPoint = new Point((int)(size.width * 0.5), (int)(size.height * 0.5));
        int top = midPoint.y - (int)((size.height * distance) * 0.5);
        int bottom = midPoint.y + (int)((size.height * distance) * 0.5);
        int left = midPoint.x - (int)((size.width * distance) * 0.5);
        int right = midPoint.x + (int)((size.width * distance) * 0.5);
        if (dir == ScrollDirection.UP) {
            swipe(new Point(midPoint.x, top), new Point(midPoint.x, bottom), SCROLL_DUR);
        } else if (dir == ScrollDirection.DOWN) {
            swipe(new Point(midPoint.x, bottom), new Point(midPoint.x, top), SCROLL_DUR);
        } else if (dir == ScrollDirection.LEFT) {
            swipe(new Point(left, midPoint.y), new Point(right, midPoint.y), SCROLL_DUR);
        } else if(dir == ScrollDirection.RIGHT){
            swipe(new Point(right, midPoint.y), new Point(left, midPoint.y), SCROLL_DUR);
        } else{
            try {
                throw new Exception("invalid scroll direction");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void scroll(ScrollDirection dir) {
        scroll(dir, SCROLL_RATIO);
    }

    protected void scrollDown() {
        scroll(ScrollDirection.DOWN);
    }

    protected void scrollUp() {
        scroll(ScrollDirection.UP);
    }

    protected void tapAtPoint(Point point) {
        AppiumDriver d = (AppiumDriver) getmDriver();  // assuming here a getDriver method
        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence tap = new Sequence(input, 0);
        tap.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), point.x, point.y));
        tap.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(input, Duration.ofMillis(200)));
        tap.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        d.perform(ImmutableList.of(tap));
    }

    protected void tapElementAt(WebElement el, double xPct, double yPct) {
        Rectangle elRect = el.getRect();
        Point point = new Point(
                elRect.x + (int)(elRect.getWidth() * xPct),
                elRect.y + (int)(elRect.getHeight() * yPct)
        );
        tapAtPoint(point);
    }

    protected void type(WebElement element, String text){
        //tap(element);
        element.clear();
        element.sendKeys(text);
        //((AndroidDriver)getmDriver()).hideKeyboard();
    }

    public void waitForElement(AppiumBy locator){
        new WebDriverWait(getmDriver(), Duration.ofSeconds(20)).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

}
