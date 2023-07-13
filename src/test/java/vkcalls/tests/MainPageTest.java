package vkcalls.tests;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import vkcalls.pageobjects.MainPage;
import vkcalls.pageobjects.OutputPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;
    private OutputPage outputPage;
    private static List<WebElement> results = new ArrayList<WebElement>();
    private List<String> textResults;
    private String query = "ВК Звонки";



    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://yandex.ru/search/?text=%D0%B2%D0%BA+%D0%B7%D0%B2%D0%BE%D0%BD%D0%BA%D0%B8&search_source=dzen_desktop_safe&src=suggest_B&lr=2");
        //driver.get("https://yandex.ru/"); //не получается напрямую запихнуть поисковый запрос в поисковую строку с помощью Selenium, хотя локатор и правильный, он не обнаруживается
        outputPage = new OutputPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }


    @Test
    public void outputPageSearchFieldContainsQuery(){
        //В поле поиска содержится наш исходный запрос
        String value = driver.findElement(outputPage.searchField).getAttribute("value").toLowerCase();
        System.out.println(value);
        Assert.assertEquals(query.toLowerCase(),value);
    }

    @Test
    public void testSearchResultsCount() throws InterruptedException {
        //Проверяем, что на первой странице выдачи всего 14 результатов (посмотрели вручную заранее)
        Thread.sleep(3000);
        results = driver.findElements(outputPage.resultCard);
/*        System.out.println(results.size());
        for (WebElement result : results){
            System.out.println(result.getText());
        }*/
        Assert.assertEquals(14, results.size());
    }
    @Test
    public void testSearchCanBeReRun() throws InterruptedException {
        //Проверяем, что поле поиска можно очистить и снова вввести наш поисковый запрос. При этом сет результатов будет тот же
        results = driver.findElements(outputPage.resultCard);
        driver.findElement(outputPage.searchField).clear();
        //Thread.sleep(3000);
        driver.findElement(outputPage.searchField).sendKeys(query);
        //Thread.sleep(3000);
        driver.findElement(outputPage.searchField).sendKeys(Keys.ENTER);
        //Thread.sleep(3000);
        List<WebElement> resultsNew = driver.findElements(By.xpath("//span[@class='OrganicTextContentSpan']"));
        assertEquals(results.size(), resultsNew.size());
    }
    @Test
    public void testRefResultIsShown() throws InterruptedException {
        //Проверяем, что конкретный результат есть в выдаче
        Assert.assertTrue(driver.findElement(outputPage.refResult).isDisplayed());
    }

    @Test
    public void testNextOutputPagesAvailable() throws InterruptedException {
        //Проверяем, что внизу страницы выдачи видны ссылки на продолжение списка результатов (всего 5) и они рабочие. Проверяем на ссылке 5
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(outputPage.page5));
        driver.findElement(outputPage.page5).click();
        Thread.sleep(5000);
        //outputPage.wait(outputPage.page7); //должна загрузиться 5ая страница выдачи и появиться ссылка на 7ую, НО! спотыкаюсь о капчу. Как пройти ее - не знаю
        //дальше должен был бы быть тест на конкретный результат с 5ой страницы выдачи
        //добавить тест на смену поисковой выдачи
    }

}
