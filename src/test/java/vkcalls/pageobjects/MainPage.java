package vkcalls.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class MainPage extends Page{
    private WebDriver driver;
    public By searchField = By.cssSelector("input[name ='text']"); //поле поиска
    public By resultCard = By.xpath("//li[contains (@class,'serp-item')]"); //карточка результата в выдаче Яндекса


    public MainPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }


}
