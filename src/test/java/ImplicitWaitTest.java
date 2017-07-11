import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImplicitWaitTest {

    public static void main(String args[]) throws Exception {
        System.setProperty("webdriver.chrome.driver", "/Applications/Google Chrome.app/Contents/MacOS/chromedriver");
        String url2 = "http://localhost:8080/login.jsp";

        Map<String, Object> chromeOptions = new HashMap<String, Object>();
        chromeOptions.put("binary", "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        WebDriver driver = new ChromeDriver(capabilities);
        driver.get(url2);


        WebElement gt_popup_wrap_e = driver.findElement(By.id("popup-submit"));
        Thread.sleep(2000);
        gt_popup_wrap_e.click();


        (new WebDriverWait(driver, 3))
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver d) {
                        return d.findElement(By.xpath("//div[@class='gt_slider_knob gt_show']")).isDisplayed();
                    }
                });

        (new WebDriverWait(driver, 3))
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver d) {
                        return d.findElement(By.xpath("//div[@class='gt_cut_bg gt_show']")).isDisplayed();
                    }
                });

        (new WebDriverWait(driver, 3))
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver d) {
                        return d.findElement(By.xpath("//div[@class='gt_cut_fullbg gt_show']")).isDisplayed();
                    }
                });

        BufferedImage bufferedImage1 = Utils.getImages(driver, "//div[@class='gt_cut_bg gt_show']/div");
        BufferedImage bufferedImage2 = Utils.getImages(driver, "//div[@class='gt_cut_fullbg gt_show']/div");

        //找到滑动的圆球
        WebElement element = driver.findElement(By.xpath("//div[@class='gt_slider_knob gt_show']"));
        //获取缺口位置
        int index = Utils.get_diff_location(bufferedImage1, bufferedImage2);
        index=index-5;
        List<Integer> track_list = Utils.get_diff_location(index);
        //生成轨迹
        Point point = element.getLocation();
        int y = point.getY();
        //模拟鼠标的移动
        System.out.println("第一步,点击元素");
        Actions action = new Actions(driver);
        action.clickAndHold(element).perform();
        Thread.sleep(150);

        for (int i = 0; i < track_list.size(); i++) {
            int track = track_list.get(i);
            new Actions(driver).moveToElement(element, track + 22, y - RandomUtils.nextInt(442, 445)).perform();
            Thread.sleep(RandomUtils.nextInt(10, 50));
        }
//        new Actions(driver).moveToElement(element, 21, y - 445).perform();
//        Thread.sleep(100);
//        new Actions(driver).moveToElement(element, 21, y - 445).perform();
//        Thread.sleep(100);
//        new Actions(driver).moveToElement(element, 21, y - 445).perform();
//        new Actions(driver).moveToElement(element, 21, y - 445).perform();
//        Thread.sleep(100);
//        new Actions(driver).moveToElement(element, 21, y - 445).perform();

        Thread.sleep(100);
        new Actions(driver).release(element).perform();


//        driver.quit();
    }




}