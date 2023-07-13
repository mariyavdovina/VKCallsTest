package vkcalls.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OutputPage extends Page{
    private WebDriver driver;
    public By searchField = By.cssSelector("input[name ='text']"); //поле поиска

    public By refResult = By.xpath("//a[@href='https://calls.vk.com/' and @accesskey='1']"); // "VK Звонки: приложение для групповых видеоконференций" - тестовый результат в выдаче
    public By resultCard = By.xpath("//span[@class='OrganicTextContentSpan']"); //карточка результата в выдаче Яндекса
    public By page5 = By.xpath("//a[@aria-label='Страница 5']"); //ссылка на 5ую страницу выдачи
    public By page7 = By.xpath("//a[@aria-label='Страница 7']"); //ссылка на 7ую страницу выдачи


    public OutputPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }
}
