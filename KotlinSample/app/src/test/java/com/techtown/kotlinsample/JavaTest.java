package com.techtown.kotlinsample;

import org.junit.Assert;
import org.junit.Test;

public class JavaTest {
    @Test
    public void test1() {
        Assert.assertEquals(4, 2 + 2);
    }

    @Test
    public void testGetterSetter() {
        PersonJava personJava = new PersonJava("john");
        personJava.setAge(20);

        Assert.assertEquals(20, personJava.getAge());
        Assert.assertEquals("john", personJava.getName());

        Person person = new Person("john");
        person.setAge(20);

        Assert.assertEquals(20, person.getAge());
        Assert.assertEquals("john", person.getName());
    }

    @Test
    public void testSetNickname() {
        PersonJava personJava = new PersonJava("john");

        personJava.setNickname("Apple");

        Assert.assertEquals("apple", personJava.getNickname());

        Person person = new Person("john");

        person.setNickname("Apple");

        Assert.assertEquals("apple", person.getNickname());
    }

    @Test
    public void testSingleTonJava() {
        // 생성자를 private 로 감췄기 때문에 new 로 생성 불가 - 주석을 풀면 에러 발생
        // SingletonJava singletonJava = new SingletonJava();

        // 외부에 공개된 getInstance() 메소드로 객체에 접근
        SingletonJava singletonJava = SingletonJava.getInstance();

        // 객체의 메소드 사용가능
        singletonJava.log("hi, singleton");
    }
}
