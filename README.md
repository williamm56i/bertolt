# Bertolt Gateway
Gateway架構專案，作為微服務框架下的單一介接入口，再根據API的context path導轉到對應的服務
Gateway架構下統一在Bertolt進行合法性驗證，呼叫端需通過驗證取得jwt後，再進行API呼叫
* 解決微服務在不適合開放多入口時，僅需一個對外domain即能操作多個API服務
* 與業務邏輯無關的機制(如安全性驗證、log紀錄等)於Bertolt完成，其他API服務可專注於邏輯開發

### 技術框架
* Java version 18
* Spring boot 3.3.13
* Spring Cloud 2023.0.6
* Spring Scheduling
* Maven
* Mybatis
* h2 (視專案情況替換成任何RDB

### 開發工具
* IntelliJ

### 版本資訊
* 0.0.1-SNAPSHOT
    * 初版

### 執行
* 打包jar
```
mvn install
```
* build image
```
docker build -t bertolt:latest .
```
* run
```
docker run --name BERTOLT -p 8000:8000 -d bertolt:latest
```
