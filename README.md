[![Build status](https://ci.appveyor.com/api/projects/status/qgqjj63nwjdnhuh4?svg=true)](https://ci.appveyor.com/project/Ekaterina-Isabel/3-card-order)

# Домашнее задание к занятию «2.1. Тестирование веб-интерфейсов»

1. Все задачи этого занятия нужно делать в одном репозитории.
1. Добавьте в каталог `artifacts` целевой сервис ([`app-order.jar`](app-order.jar))
1. Удостоверьтесь, что на Appveyor сборка зелёная
1. Поставьте бейджик сборки вашего проекта в файл README.md

<details>
   <summary>Настройка</summary>
   
[Краткое руководство по работе с селекторами](selectors.md).

### 1. Целевой сервис

Ваш целевой сервис (SUT - System under test), расположен в файле `app-order.jar` (в этом репозитории). Вам нужно его скачать и положить в каталог `artifacts` вашего проекта.

Поскольку файлы с расширением `.jar` находят в списках `.gitignore` вам нужно принудительно заставить git следить за ним: `git add -f artifacts/app-order.jar`.

После чего сделать `git push`. Обязательно удостоверьтесь, что файл попал в репозиторий.

### 2. `build.gradle`

Ваш `build.gradle` должен выглядеть следующим образом:

```groovy
plugins {
    id 'java'
}

group 'ru.netology'
version '1.0-SNAPSHOT'

sourceCompatibility = 11

// кодировка файлов (если используете русский язык в файлах)
compileJava.options.encoding = "UTF-8"
compileTestJava.options.encoding = "UTF-8"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter:5.6.1'
    testImplementation 'com.codeborne:selenide:5.19.0'
}

test {
    useJUnitPlatform()
    // в тестах, вызывая `gradlew test -Dselenide.headless=true` будем передавать этот параметр в JVM (где его подтянет Selenide)
    systemProperty 'selenide.headless', System.getProperty('selenide.headless')
}
```

#### **HEADLESS режим браузера**

На серверах сборки чаще всего нет графического интерфейса, поэтому запуская браузер в режиме **headless** мы отключаем графический интерфейс (при этот все процессы браузера продолжают работать так же).
При использовании **selenium** данный режим настраивается непосредственно в коде во время инициализации драйвера, примеры инициализации ниже.

Детальнее можете почитать про Headless:
- [Chrome](https://www.chromestatus.com/features/5678767817097216)
- [Gecko (Firefox)](https://developer.mozilla.org/en-US/docs/Mozilla/Firefox/Headless_mode)

Включение **headless** режима при использовании **selenium** необходимо реализовать в коде во время создания экземпляра webdriver:  

```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--disable-dev-shm-usage");
options.addArguments("--no-sandbox");
options.addArguments("--headless");
driver = new ChromeDriver(options);
```

Для **selenide** **headless** режим активируется при запуске тестов с определенным параметром:  
```
gradlew test -Dselenide.headless=true
```

#### **Webdriver для разных операционных систем**

Если вы выполняете работу с использованием **selenium**, то будьте готовы, что ваша сборка может упасть из-за того, что у вас в репозитории webdriver для одной ОС (например, для Windows), а в CI используется Linux. Для решения этой проблемы можно использовать библиотеку [webdriver manager](https://github.com/bonigarcia/webdrivermanager). Она автоматически определяет ОС и версию браузера, скачивает и устанавливает подходящий файл драйвера. Кстати, в Selenide используется именно эта библиотека.

### 3. `.appveyor.yml`

AppVeyor настраивается аналогично предыдущей лекции за исключением того, что тесты нужно запускать так, чтобы **selenide** запускался в headless-режиме (читайте выше про передачу специального флага при вводе команды запуска). Если тесты написаны с использованием **selenium**, то после включения headless режима в коде никаких дополнительных флагов в командной строке передавать не нужно.
</details>

## Задача №1 - Заказ карты

Вам необходимо автоматизировать тестирование формы заказа карты:

![image](https://user-images.githubusercontent.com/79922872/172034954-25ddc7a8-fdb6-4ee9-9ce3-d80da9723a23.png)

Требования к содержимому полей:
1. Поле Фамилия и имя - разрешены только русские буквы, дефисы и пробелы.
2. Поле телефон - только цифры (11 цифр), символ + (на первом месте).
3. Флажок согласия должен быть выставлен.

Тестируемая функциональность: отправка формы.

Условия: если все поля заполнены корректно, то вы получаете сообщение об успешно отправленной заявке:

![image](https://user-images.githubusercontent.com/79922872/172034961-3fa89d94-8e72-417e-ada4-2fa2e33feb25.png)

Вам необходимо самостоятельно изучить элементы на странице, чтобы подобрать правильные селекторы.

<details>
    <summary>Подсказка</summary>

    Смотрите на `data-test-id` и внутри него ищите нужный вам `input` - используйте вложенность для селекторов.
</details>

Проект с авто-тестами должен быть выполнен на базе Gradle, с использованием Selenide/Selenium (по выбору студента).

Для запуска тестируемого приложения скачайте jar-файл из текущего каталога и запускайте его командой:
`java -jar app-order.jar`

Приложение будет запущено на порту 9999.

Если по каким-то причинам порт 9999 на вашей машине используется другим приложением, используйте:

`java -jar app-order.jar -port=7777`

Убедиться, что приложение работает, вы можете открыв в браузере страницу: http://localhost:9999

## Задача №2 - Проверка валидации (необязательная)

После того, как вы протестировали Happy Path, необходимо протестировать остальные варианты.

Тестируемая функциональность: валидация полей перед отправкой.

Условия: если какое-то поле не заполнено, или заполнено неверно, то при нажатии на кнопку "Продолжить" должны появляться сообщения об ошибке (будет подсвечено только первое неправильно заполненное поле):

![](pic/error.png)![image](https://user-images.githubusercontent.com/79922872/172034969-3acd09ab-2128-4a09-a9fd-a1886d1a78f2.png)
<details>
    <summary>Подсказка</summary>

    У некоторых элементов на странице появится css-класс `input_invalid`.
</details>
