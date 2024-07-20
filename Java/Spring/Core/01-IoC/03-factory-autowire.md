# Spring IoC 工厂方法 + 自动装载

- [工厂方法](#工厂方法)
    - [静态工厂方法](#静态工厂方法)
    - [实例工厂方法](#实例工厂方法)
- [自动装载（autowire）](#自动装载autowire)
    - [通过名称（byName）](#通过名称byname)
    - [通过类型（byType）](#通过类型bytype)

## 工厂方法

### 静态工厂方法

```java
public class Car {
    private int num;
    private String brand;
    public Car(int num, String brand) {
        super();
        this.num = num;
        this.brand = brand;
    }
}
```

```java
public class StaticCarFactory {
    private static Map<Integer,Car> cars;
    static{
        cars = new HashMap<Integer,Car>();
        cars.put(1, new Car(1,"奥迪"));
        cars.put(2, new Car(2,"奥拓"));
    }
    public static Car getCar(int num){
        return cars.get(num);
    }
}
```

```xml
<!-- 配置静态工厂创建 car 对象 -->
<bean id="car1" class="com.southwind.entity.StaticCarFactory" factory-method="getCar">
   <constructor-arg value="1"></constructor-arg>
</bean>
```

### 实例工厂方法

```java
public class InstanceCarFactory {
    private Map<Integer,Car> cars;
    public InstanceCarFactory() {
        cars = new HashMap<Integer,Car>();
        cars.put(1, new Car(1,"奥迪"));
        cars.put(2, new Car(2,"奥拓"));
    }
    public Car getCar(int num){
        return cars.get(num);
    }
}
```

```xml
<!-- 配置实例工厂对象 -->
<bean id="carFactory" class="com.southwind.entity.InstanceCarFactory"></bean>
<!-- 通过实例工厂对象创建 car 对象 -->
<bean id="car2" factory-bean="carFactory" factory-method="getCar">
    <constructor-arg value="2"></constructor-arg>
</bean> 
```

## 自动装载（autowire）

* 通过配置 property 的 ref 属性，可将 bean 进行依赖注入。
* 但还有更简单的方式：自动装载，不需要手动配置 property，IoC 容器会自动选择 bean 完成依赖注入。

### 通过名称（byName）

```java
public class Person {
    private int id;
    private String name;
    private Car car;
}
```

```xml
<!-- 当创建 person 对象时，没有在 property 中配置 car 属性，因此 IoC 容器会自动进行装载，autowire="byName" 表示通过匹配属性名的方式去装载对应的 bean，Person 实体类中有 car 属性，所以就将 id="car" 的 bean 注入到 Person 中。 -->

<bean id="person" class="com.southwind.entity.Person" autowire="byName"> 
   <property name="id" value="1"></property>
   <property name="name" value="张三"></property>
</bean>
<bean id="car" class="com.southwind.entity.StaticCarFactory" factory-method="getCar">
   <constructor-arg value="2"></constructor-arg>
</bean>

<!-- 这时仍然可以通过 property 标签手动进行 car 的注入，其优先级更高，若两种方式同时配置，以 property 的配置为准。 -->
```

```
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
Person person = (Person) applicationContext.getBean("person");
System.out.println(person);
```

```console
Person [id=1, name=张三, car=Car [num=2, brand=奥拓]]
```

### 通过类型（byType）

```xml
<beans>
  <bean id="person" class="com.southwind.entity.Person" autowire="byType">
    <property name="id" value="1"></property>
    <property name="name" value="张三"></property>
  </bean>
  <bean id="car" class="com.southwind.entity.StaticCarFactory" factory-method="getCar">
    <constructor-arg value="1"></constructor-arg>
  </bean>
  <bean id="car2" class="com.southwind.entity.StaticCarFactory" factory-method="getCar">
    <constructor-arg value="2"></constructor-arg>
  </bean>
</beans>
```

```
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
Person person = (Person) applicationContext.getBean("person");
System.out.println(person);
// 此时会报错，因为多个Car对象，无法根据类型自动装载，删除一个即可。
```
