# Spring IoC

* IoC 是典型的工厂模式，通过工厂去注入对象
* AOP 是代理模式的体现

## IoC实际例子

```java
public class Student {
    private int id;
    private String name;
    private int age;
}
```

### 1.无参构造

```xml
<!-- Spring 通过调用每个属性的setter方法完成属性赋值 -->
<bean id="stu" class="com.southwind.entity.Student">
    <property name="id" value="1"></property>
    <property name="name" value="张三"></property>
    <property name="age" value="23"></property>
</bean>
```

```java
// 获取对象方法1：通过ID获取对象
//1.加载 spring.xml 配置文件
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
//2.通过 id 值获取对象
Student stu = (Student) applicationContext.getBean("stu");
System.out.println(stu);

// 获取对象方法2：通过运行时类获取对象
// 但是如果存在同一个类的多个bean，则出错
//1.加载 spring.xml 配置文件
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
//2.通过运行时类获取对象
Student stu = applicationContext.getBean(Student.class);
System.out.println(stu);

```

### 2.有参构造

```java
// 实体类中创建有参构造方法
public Student(int id, String name, int age) {
    super();
    this.id = id;
    this.name = name;
    this.age = age;
}
```

```xml
<!-- spring.xml 中进行配置 -->
<!-- 通过有参构造函数创建对象 -->
<bean id="stu3" class="com.hzit.entity.Student">
   <constructor-arg name="id" value="3"></constructor-arg>
   <constructor-arg name="name" value="小明"></constructor-arg>
   <constructor-arg name="age" value="22"></constructor-arg>
</bean>
<!-- 或者通过下标index对应 -->
<bean id="stu3" class="com.hzit.entity.Student">
   <constructor-arg index="0" value="3"></constructor-arg>
   <constructor-arg index="1" value="小明"></constructor-arg>
   <constructor-arg index="2" value="22"></constructor-arg>
</bean>
```

### 3.对象之间有关联（通过`ref`属性）

```java
public class Classes {
    private int id;
    private String name;
}

public class Student {
    private int id;
    private String name;
    private int age;
    private Classes classes;
}
```

```xml
<!-- 创建 classes 对象 -->
<bean id="classes" class="com.hzit.entity.Classes">
   <property name="id" value="1"></property>
   <property name="name" value="Java班"></property>
</bean>
<!-- 创建 stu 对象 -->
<bean id="stu" class="com.hzit.entity.Student">
   <property name="id" value="1"></property>
   <property name="name">
       <value><![CDATA[<张三>]]></value>
   </property>
   <property name="age" value="23"></property>
   <!-- 将 classes 对象赋给 stu 对象 -->
   <property name="classes" ref="classes"></property>
</bean>
```

### 4.集合属性的依赖注入（通过`list`标签）

```java
public class Classes {
    private int id;
    private String name;
    private List<Student> students;
}
```

```xml
<!-- 配置 classes 对象 -->
<bean id="classes" class="com.hzit.entity.Classes">
   <property name="id" value="1"></property>
   <property name="name" value="Java班"></property>
   <property name="students">
       <!-- 注入 student 对象 -->
       <list>
           <ref bean="stu"/>
           <ref bean="stu2"/>
       </list>
   </property>
</bean>
<bean id="stu" class="com.hzit.entity.Student">
   <property name="id" value="1"></property>
   <property name="name">
        <value><![CDATA[<张三>]]></value>
   </property>
   <property name="age" value="23"></property>
</bean>
<bean id="stu2" class="com.hzit.entity.Student">
   <property name="id" value="2"></property>
   <property name="name" value="李四"></property>
   <property name="age" value="23"></property>
</bean>
```
