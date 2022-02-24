# Tronapi java client.

这个项目以 `java springboot` 为例，演示如何接入 `tronapi` 的接口服务。

用户可直接引入 `lib` 文件夹下的源码文件，快速进行后续开发。

## 链接

- [接口文档](https://doc.tronapi.com)
- [商户登录](https://pro.tronapi.com)

## 安装

项目直接引入 `lib` 文件夹下源码文件。

注意，`lib` 目录存在以下三方包依赖：

```xml
  <dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp</artifactId>
    <version>4.8.1</version>
  </dependency>

  <dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.11.2</version>
  </dependency>   
```

## 使用

```java
  String publicKey = "your public key";
  String privateKey = "your private key";
  Client client = new Client(publicKey, privateKey);
```

### 订单

- 订单创建

> 接口文档：https://doc.tronapi.com/api/transaction/create.html

```java
  String amount = "100";
  String currency = "CNY"; // CNY or USD
  String coinCode = "FAU"; // FAU or USDT
  String orderId = "your order id";
  String productName = "your product name";
  String customerId = "your customer id";
  String notifyUrl = "your notify url";
  String redirectUrl = "your redirect url";

  JsonNode res = client.transaction.create(amount, currency, coinCode, orderId, productName, customerId, notifyUrl, redirectUrl);
```

- 订单查询

> 接口文档：https://doc.tronapi.com/api/transaction/query.html

```java
  String token = "your transaction token";
	JsonNode res = client.transaction.query(token);
```

### 账户

- 账户查询

> 接口文档：https://doc.tronapi.com/api/wallet/query.html

```java
  JsonNode res = client.account.query();
```

- 提现申请

> 接口文档：https://doc.tronapi.com/api/wallet/withdrawal_create.html

```java
  String amount = "200";
  String coinCode = "FAU"; // FAU or USDT
  String address = "your withdrawal address";
  String notifyUrl = "your withdrawal notify url";

  JsonNode res = client.account.withdrawal(amount, coinCode, address, notifyUrl);
```

- 提现查询

> 接口文档：https://doc.tronapi.com/api/wallet/withdrawal_query.html

```java
  String token = "your withdrawal token";
	JsonNode res = client.account.withdrawal_query(token);
```

## 测试

用户也可直接下载本项目进行接口调试，方法如下：

- 下载项目源代码
- 修改 `application.properties` 配置文件
- 启动项目，浏览器访问相关路由即可

> 详细路由及接口调用方法，参考 `controller/TransactionController.java` & `controller/AccountController.java`

## 联系

可通过 [官网](https://doc.tronapi.com) `右下角` 反馈功能和我们取得联系。