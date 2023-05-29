[![Build status](https://ci.appveyor.com/api/projects/status/qgqjj63nwjdnhuh4?svg=true)](https://ci.appveyor.com/project/Ekaterina-Isabel/3-card-order)

# Домашнее задание к занятию «2.1. Тестирование веб-интерфейсов»

## Задача №1: заказ карты

**Задача:** автоматизировать тестирование формы заказа карты:

![](https://raw.githubusercontent.com/netology-code/aqa-homeworks/master/web/pic/order.png)

Целевой сервис (SUT) расположен в файле [`app-order.jar`](https://github.com/Ekaterina-Isabel/3._Card_order/blob/master/artifacts/app-order.jar) в каталоге `artifacts` проекта.

**Требования к содержимому полей:**
1. В поле фамилии и имени разрешены только русские буквы, дефисы и пробелы.
2. В поле телефона — только 11 цифр, символ + на первом месте.
3. Флажок согласия должен быть выставлен.

**Тестируемая функциональность:** отправка формы.

**Условия:** если все поля заполнены корректно, то вы получаете сообщение об успешно отправленной заявке:

![](https://raw.githubusercontent.com/netology-code/aqa-homeworks/master/web/pic/success.jpg))

Проект с автотестами должен быть выполнен на базе Gradle с использованием Selenide или Selenium по выбору студента.

Для запуска тестируемого приложения скачайте JAR-файл из текущего каталога и запускайте его командой:
`java -jar app-order.jar`.

Приложение будет запущено на порту 9999.

Если по каким-то причинам порт 9999 на вашей машине используется другим приложением, используйте:

`java -jar app-order.jar -port=7777`

Убедиться, что приложение работает, вы можете, открыв в браузере страницу: http://localhost:9999.  

**Результат выполнения работы:** [ссылка на текущий репозиторий](https://github.com/Ekaterina-Isabel/3._Card_order/tree/master)
