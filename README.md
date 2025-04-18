# API для получения валютных курсов

Этот API предоставляет информацию о валютных курсах на указанную дату, используя данные с сайта Центрального банка России. API позволяет получить информацию о валютных курсах для разных валют на конкретную дату.

## Структура проекта

1. **Контроллер** (`CurrencyController`): Обрабатывает запросы на получение данных о валютных курсах.
2. **Сервис** (`CurrencyService`): Отвечает за логику взаимодействия с сервисом SOAP Центрального банка России.
3. **Модели** (`CurrencyRate`, `RequestLog`): Содержат информацию о валютных курсах и логах запросов.
4. **Обработчик ошибок** (`GlobalExceptionHandler`): Обрабатывает ошибки, такие как некорректный формат даты или запрос в будущем.

## Основной эндпоинт

### Получение валютных курсов на дату

**Метод**: `GET`

**URL**: `/api`

**Параметры запроса**:
- `date` (обязательный) — Дата, на которую нужно получить информацию о валютных курсах, в формате `YYYY-MM-DD`.

**Пример запроса**:

`GET /api?date=2025-04-18`


### Ответ

Если запрос прошел успешно, API вернет данные в формате JSON с информацией о курсах валют для указанной даты.

**Пример успешного ответа**:

```json
{
  "id": 6,
  "requestDate": "1998-01-01",
  "timestamp": "2025-04-18T10:04:56.211+00:00",
  "currencies": [
    {
      "id": 142,
      "charCode": "AUD",
      "nominal": "1",
      "valueRate": "3.9127",
      "name": "Австралийский доллар"
    },
    {
      "id": 143,
      "charCode": "ATS",
      "nominal": "10",
      "valueRate": "4.7756",
      "name": "Австрийский шиллинг"
    },
    {
      "id": 144,
      "charCode": "GBP",
      "nominal": "1",
      "valueRate": "9.9913",
      "name": "Фунт стерлингов"
    },
    {
      "id": 145,
      "charCode": "BYB",
      "nominal": "10000",
      "valueRate": "1.44",
      "name": "Белoрусский рубль"
    },
    {
      "id": 146,
      "charCode": "BEF",
      "nominal": "10",
      "valueRate": "1.6289",
      "name": "Бельгийский франк"
    },
    {
      "id": 147,
      "charCode": "BGN",
      "nominal": "1000",
      "valueRate": "3.379",
      "name": "Болгарский лев"
    },
    {
      "id": 148,
      "charCode": "HUF",
      "nominal": "100",
      "valueRate": "2.9447",
      "name": "Форинт"
    },
    {
      "id": 149,
      "charCode": "GRD",
      "nominal": "100",
      "valueRate": "2.1296",
      "name": "Греческая драхма"
    },
    {
      "id": 150,
      "charCode": "DKK",
      "nominal": "10",
      "valueRate": "8.8193",
      "name": "Датская крона"
    },
    {
      "id": 151,
      "charCode": "USD",
      "nominal": "1",
      "valueRate": "5.96",
      "name": "Доллар США"
    },
    {
      "id": 152,
      "charCode": "INR",
      "nominal": "10",
      "valueRate": "1.5183",
      "name": "Индийская рупия"
    },
    {
      "id": 153,
      "charCode": "IEP",
      "nominal": "1",
      "valueRate": "8.6283",
      "name": "Ирландский фунт"
    },
    {
      "id": 154,
      "charCode": "ISK",
      "nominal": "100",
      "valueRate": "8.324",
      "name": "Исландская крона"
    },
    {
      "id": 155,
      "charCode": "ESP",
      "nominal": "100",
      "valueRate": "3.9688",
      "name": "Испанская песета"
    },
    {
      "id": 156,
      "charCode": "ITL",
      "nominal": "1000",
      "valueRate": "3.4216",
      "name": "Итальянская лира"
    },
    {
      "id": 157,
      "charCode": "KZT",
      "nominal": "100",
      "valueRate": "7.843",
      "name": "Тенге"
    },
    {
      "id": 158,
      "charCode": "CAD",
      "nominal": "1",
      "valueRate": "4.1504",
      "name": "Канадский доллар"
    },
    {
      "id": 159,
      "charCode": "DEM",
      "nominal": "1",
      "valueRate": "3.36",
      "name": "Немецкая марка"
    },
    {
      "id": 160,
      "charCode": "NLG",
      "nominal": "1",
      "valueRate": "2.9821",
      "name": "Нидерландский гульден"
    },
    {
      "id": 161,
      "charCode": "NOK",
      "nominal": "10",
      "valueRate": "8.1682",
      "name": "Норвежская крона"
    },
    {
      "id": 162,
      "charCode": "PTE",
      "nominal": "100",
      "valueRate": "3.2857",
      "name": "Португальский эскудо"
    },
    {
      "id": 163,
      "charCode": "SGD",
      "nominal": "1",
      "valueRate": "3.5678",
      "name": "Сингапурский доллар"
    },
    {
      "id": 164,
      "charCode": "TRL",
      "nominal": "100000",
      "valueRate": "2.9",
      "name": "Турецкая лира"
    },
    {
      "id": 165,
      "charCode": "UAH",
      "nominal": "1",
      "valueRate": "3.1158",
      "name": "Гривна"
    },
    {
      "id": 166,
      "charCode": "FIM",
      "nominal": "1",
      "valueRate": "1.1098",
      "name": "Финляндская марка"
    },
    {
      "id": 167,
      "charCode": "FRF",
      "nominal": "1",
      "valueRate": "1.0041",
      "name": "Французский франк"
    },
    {
      "id": 168,
      "charCode": "CZK",
      "nominal": "10",
      "valueRate": "1.7403",
      "name": "Чешская крона"
    },
    {
      "id": 169,
      "charCode": "SEK",
      "nominal": "10",
      "valueRate": "7.6598",
      "name": "Шведская крона"
    },
    {
      "id": 170,
      "charCode": "CHF",
      "nominal": "1",
      "valueRate": "4.1585",
      "name": "Швейцарский франк"
    },
    {
      "id": 171,
      "charCode": "XEU",
      "nominal": "1",
      "valueRate": "6.6442",
      "name": "ЭКЮ"
    },
    {
      "id": 172,
      "charCode": "JPY",
      "nominal": "100",
      "valueRate": "4.5821",
      "name": "Иена"
    }
  ]
}
```

### Как запустить проект

Клонируйте репозиторий:

```bash
git clone https://github.com/dellup/cbrSpringAPI.git
```
Перейдите в директорию проекта:

```bash
cd cbrSpringAPI
```
Скачайте зависимости и соберите проект с помощью Gradle:

```bash
./gradlew build
```
Запустите приложение с помощью Gradle:

```bash
./gradlew bootRun
```
API будет доступен по адресу http://localhost:8080/api.
