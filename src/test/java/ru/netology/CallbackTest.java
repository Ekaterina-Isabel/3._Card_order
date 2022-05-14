package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
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
        WebDriverManager.chromedriver().setup();
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
    public void shouldTestCorrectData() {       //корректные данные
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

    @Test
    public void shouldTestInvalidFieldName() {     //проверка поля имя
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Ivan Ivanov");
        elements.get(1).sendKeys("+79194885321");

        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void shouldTestEmptyFieldName() {     //проверка пустого поля имя
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("");
        elements.get(1).sendKeys("+79194885321");

        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();

        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    public void shouldTestInvalidPhoneField() {     //проверка поля телефон
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Иванов Иван");
        elements.get(1).sendKeys("+7919488532");

        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldTestEmptyPhoneField() {     //проверка пустого поля телефон
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Иванов Иван");
        elements.get(1).sendKeys("");

        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();

        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    public void shouldTestCheckboxIsNotClicked() {     //проверка не нажатого чекбокса
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Иванов Иван");
        elements.get(1).sendKeys("+79194885321");

        //driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .checkbox__text")).getText();

        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }
}
