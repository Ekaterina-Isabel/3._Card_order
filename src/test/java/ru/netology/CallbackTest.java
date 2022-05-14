package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CallbackTest {
    private WebDriver driver;       //создание сущности драйвер
    private ChromeOptions options = new ChromeOptions();

    //метод, в котором прописан путь до драйвера
    @BeforeAll      //будет запускаться перед всеми тестами
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "driver/win/chromedriver.exe");     //"название драйвера", "путь до драйвера из папки driver"
    }

    //метод создания поля для WebDriver
    @BeforeEach     //будет запускаться перед каждым тестом
    void setUp() {
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    //метод закрытия всех окон браузера
    @AfterEach
    public void close() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldTestSomething() {
        driver.get("http://localhost:9999");        //открывает браузер по URL
        List<WebElement> elements = driver.findElements(By.className("input__control"));        //найти все элементы с атрибутом класса "input__control"
        elements.get(0).sendKeys("Иванов Иван");     //передать значение первому элементу списка, т.е. к полю для имени
        elements.get(1).sendKeys("+79194885321");        //передать значение второму элементу списка, т.е. к полю для телефона

        driver.findElement(By.className("checkbox__box")).click();        //нажатие на чекбокс, обращаемся по атрибуту "checkbox__box"
        driver.findElement(By.tagName("button")).click();        //нажатие на кнопку Отправить, обращаемся по имени тега
        //создание переменной, куда положим текст элемента (открывшегося окна), поиск по части атрибута "alert-success"
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());     //сравнение, проверка фактического и ожидаемого результата
    }
}
