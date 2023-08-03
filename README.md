# attestation_finale_work
Мобильное приложение-аналог Reddit.
Это приложение, которое позволяет зарегистрированным пользователям размещать ссылки на понравившуюся информацию в интернете, находить интересный контент и обсуждать его.
API сервиса reddit.

## Сценарии использования:

Пользователь знакомится с основными функциями приложения на экране онбординга.
Онбординг отображается сразу при первом запуске приложения.
Авторизация
А) Экран авторизации отображается всегда следующим экраном после онбординга.
Б) Пользоваться приложением без авторизации нельзя.

## Приступая к работе

Для запуска приложения нужно установить на свой ПК среду разработки Android studio. 
### Предварительные требования

Приложение разработано и запущено на Android studio версии:  

```
Android studio Dolphin|2021.3.1 
```

### Установка

Что бы запустить приложение нужно :

```
Клонировать репозиторий на свой ПК
```

Открыть в Android studio клонированный проект "attestation_finale-work"

```
Open-> attestation_finale-work
```

## Использовано: 

При реализации этого приложения были использованы следующие технологии и библиотеки:

Android SDK и Kotlin: Приложение разработано с использованием комплекта разработки программного обеспечения для Android, предназначенного для минимальной версии SDK Android 7.0 (Nougat).
Retrofit: Retrofit используется для сетевого взаимодействия, упрощая извлечение данных из Reddit API.
Glide: Glide используется для эффективной загрузки изображений и кэширования, обеспечивая плавный и отзывчивый интерфейс при отображении изображений публикаций.
Библиотека RecyclerView и Paging3: Приложение использует RecyclerView для эффективного отображения списка записей, обеспечивая плавную прокрутку и оптимизированное использование памяти.
Компоненты архитектуры Android: Это приложение соответствует рекомендуемым принципам архитектуры Android, включая ViewModel, LiveData, Flow.
