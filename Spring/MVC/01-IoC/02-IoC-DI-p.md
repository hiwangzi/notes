

## Spring中的bean

* bean 是根据 scope 来生成的，表示 bean 的作用域。
* scope 有 4 种类型，具体如下。
  * singleton：单例，表示通过 Spring 容器获取的该对象是唯一的。
  * prototype：原型，表示通过 Spring 容器获取的对象是不同的。
  * reqeust：请求，表示在一次 HTTP 请求内有效。（只适用于 Web 项目）
  * session：会话，表示在一个用户会话内有效。（只适用于 Web 项目）

### 默认情况下Spring容器创建的对象都是单例模式

```java
public class User {
    private int id;
    private String name;
    private int age;
    public User() {
         System.out.println("创建了 User 对象");
    }   
}
```

```xml
<bean id="user" class="com.southwind.entity.User">
   <property name="id" value="1"></property>
   <property name="name" value="张三"></property>
   <property name="age" value="23"></property>
</bean>
```

```java
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
User user = (User) applicationContext.getBean("user");
User user2 = (User) applicationContext.getBean("user");
System.out.println(user == user2);
```

```console
创建了User对象
true
```

### 将scope改为prototype

```xml
<bean id="user" class="com.southwind.entity.User" scope="prototype">
   <property name="id" value="1"></property>
   <property name="name" value="张三"></property>
   <property name="age" value="23"></property>
</bean>
```

```console
创建了User对象
创建了User对象
false
```

## Spring 的继承

```xml
<bean id="user" class="com.southwind.entity.User">
   <property name="id" value="1"></property>
   <property name="name" value="张三"></property>
   <property name="age" value="23"></property>
</bean>
<bean id="user2" class="com.southwind.entity.User" parent="user"></bean>
```

```java
User user2 = (User) applicationContext.getBean("user2");
System.out.println(user2);
```

```console
创建了User对象
创建了User对象
User [id=1, name=张三, age=23]
```

### 覆盖属性

```xml
<bean id="user" class="com.southwind.entity.User">
   <property name="id" value="1"></property>
   <property name="name" value="张三"></property>
   <property name="age" value="23"></property>
</bean>
<bean id="user2" class="com.southwind.entity.User" parent="user">
   <!-- 覆盖 name 属性 -->
   <property name="name" value="李四"></property>
</bean>
```

```console
创建了User对象
创建了User对象
User [id=1, name=李四, age=23]
```

* Spring 中的 bean 也能在不同类之间继承，但是需要这两个类的属性列表完全一致，否则会报错

## bean的创建顺序（depends-on属性）

* 依赖也是 bean 和 bean 之间的一种关联方式
  * 默认情况下，这是由 spring.xml 中 bean 的配置顺序来决定的。
  * 配置依赖关系后，被依赖的 bean 一定先创建，然后再创建依赖的 bean。

```xml
<bean id="user" class="com.southwind.entity.User" depends-on="car">
   <property name="id" value="1"></property>
   <property name="name" value="张三"></property>
   <property name="age" value="23"></property>
</bean>
<bean id="car" class="com.southwind.entity.Car">
   <property name="id" value="1"></property>
   <property name="brand" value="宝马"></property>
</bean>
```

```java
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
User user = (User) applicationContext.getBean("user");
Car car = (Car) applicationContext.getBean("car");
```

```console
创建了Car对象
创建了User对象
```

## Spring读取外部资源

```properties
# jdbc.properties
driverName = com.mysql.jdbc.Driver
url = jdbc:mysql://localhost:3306/myTest?useUnicode=true&characterEncoding=UTF-8
user = root
pwd = 123456
```

```xml
<!-- 导入外部的资源文件 -->
<context:property-placeholder location="classpath:jdbc.properties"></context:property-placeholder>
<!-- 创建 C3P0 数据源 -->
<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
   <property name="user" value="${user}"></property>
   <property name="password" value="${pwd}"></property>
   <property name="driverClass" value="${driverName}"></property>
   <property name="jdbcUrl" value="${url}"></property>
</bean>
```

```java
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
DataSource ds = (DataSource) applicationContext.getBean("dataSource");
Connection conn = null;
try {
     conn = ds.getConnection();
} catch (SQLException e) {
     e.printStackTrace();
}
System.out.println(conn);
```

## 使用p命名空间简化配置

```java
public class User {
    private int id;
    private String name;
    private int age;
    private Car car;
    public User() {
        System.out.println("创建了 User 对象");
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", age=" + age + ", car="
                + car + "]";
    }
}
```

```xml
<bean id="user" class="com.southwind.entity.User" p:id="1" p:name="张三" p:age="23" p:car-ref="car"></bean>
<bean id="car" class="com.southwind.entity.Car" p:id="1" p:brand="宝马"></bean>
```

```java
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
User user = (User) applicationContext.getBean("user");
System.out.println(user);
```

```console
创建了User对象
创建了Car对象
User [id=1, name=张三, age=23, car=Car [id=1, brand=宝马]]
```
