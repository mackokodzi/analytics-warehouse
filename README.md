# Analytics-warehouse
API for retrieving campaign statistics

# Running the app
```aidl
./gradlew bootRun
```

Then go to ```localhost:8080/campaign-statistics``` to see default query with total clicks, impressions and CTR.

Response:

```
[
  {
    "clicks": 487743,
    "impressions": 34049327,
    "ctr": 0.014324600307078023
  }
]
```

# Deployment
App is deployed on heroku server here: ```https://analytics-warehouse-mp.herokuapp.com/campaign-statistics```

Be patient because server will need some time to wake up

# API for querying campaign statistics
The API allows to return multiple variants depending on input parameters. None of the parameters are required.

| Param        | Type | Default Value | Description  |
| -------------:| -----:|-----:|-----:|
| metrics       | ```List<String>``` | "clicks,impressions,ctr" | List of metrics to be returned. Allowed values are: clicks(Total Clicks),impressions(Total Impressions),ctr(Click-Through Rate) |
| groups       | ```List<String>``` | empty list |  List of dimensions to be grouped. Allowed values are: datasource, campaign, date |
| datasources  |    ```List<String>``` | empty list | List of datasources to be filtered |
| campaigns  |    ```List<String>``` | empty list | List of campaigns to be filtered |
| dateFrom  |    ```Date``` | null | Date from to be filtered. ISO Date Format {yyyy-MM-dd}  |
| dateTo  |    ```Date``` | null | "Date to" to be filtered. ISO Date Format {yyyy-MM-dd} |
| sorts  |    ```List<String>``` | empty list | Enables sorting fields from metrics and groups therefore given sort field needs to be either in metrics or groups. Sorting may be descending or ascending (default). You may achieve this by adding ```desc``` or ```asc``` after sort field name prefixing it with dot: ```ctr.desc``` or ```clicks.asc```|

## Sample queries
Hint:
Sample queries may be found in requests.http file. You may run them from intellij.

## Total Clicks for a given Datasource for a given Date range
```
GET {{host}}/campaign-statistics
   ?metrics=clicks
   &groups=datasource
   &datasources=Google Ads
   &dateFrom=2019-11-12
   &dateTo=2019-11-20
```

Response:
```
[
  {
    "datasource": "Google Ads",
    "clicks": 1624
  }
]
```


## Click-Through Rate (CTR) per Datasource and Campaign sorted by CTR in descending order
```
GET {{host}}/campaign-statistics
   ?metrics=ctr
   &groups=datasource,campaign
   &sorts=ctr.desc
```
   
Response:
```
[
  {
    "datasource": "Twitter Ads",
    "campaign_name": "AT|SN|Brand",
    "ctr": 0.41847451790633608815
  },
  {
    "datasource": "Twitter Ads",
    "campaign_name": "DE|SN|Brand",
    "ctr": 0.40226720647773279352
  },
  {
    "datasource": "Twitter Ads",
    "campaign_name": "ÖAMTC App|Smartphones iOS",
    "ctr": 0.11848164860213052984
  },
  ...
```

## Impressions over time (daily) sorted by Date (DESC)
```
GET {{host}}/campaign-statistics
   ?metrics=impressions
   &groups=date
   &sorts=date.desc
```

Response:
```
[
  {
    "date": "2020-02-14T01:00:00Z",
    "impressions": 23738
  },
  {
    "date": "2020-02-13T01:00:00Z",
    "impressions": 46445
  },
  {
    "date": "2020-02-12T01:00:00Z",
    "impressions": 45910
  },
  {
    "date": "2020-02-11T01:00:00Z",
    "impressions": 51102
  },
  ..
```  

# Architecture

Following technology stack has been used:
* programming language - Kotlin
* db - postgreSQL (due to limitations on heroku server only 10k records can be stored)
* http client - Ktor
* ORM - jOOQ

Application on its startup retrieves CSV file with data via http request (Ktor). Then it populates data to postgreSQL database only if there are no records stored.
There is only one table in postgreSQL: `campaign_statistics`. After this operation application is ready to be queried.

