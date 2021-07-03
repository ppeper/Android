package com.techtown.kotlinsample

import org.junit.Assert
import org.junit.Test

class KotlinTest {
    @Test
    fun test1() {
        Assert.assertEquals(4, 2 + 2)
    }

    @Test
    fun testGetterSetter() {

        val person = Person("john")

        person.age = 20

        Assert.assertEquals(20, person.age)
        Assert.assertEquals("john", person.name)
    }

    @Test
    fun testUser() {
        val user = User()
        // 닉네임을 대소문자가 섞인 문자열로 설정
        user.nickname = "death Note"
        // 닉네임은 모두 대문자로 변경되어야 한다. 예상대로 변경되었는지 확인
        Assert.assertEquals("DEATH NOTE", user.nickname)
    }

    @Test
    fun testUserNameObservable() {
        val user = User()
        user.name = "john"
        user.name = "james"
    }

    @Test
    fun testAnimalMap() {
        // Animal 객체를 생성할때 맵 객체를 넘긴다.
        val animal = Animal(mutableMapOf(
            "name" to "cat",
            "age" to 20)
        )
        // name 속성이 map 객체에 정상적으로 위임되었는지 확인
        Assert.assertEquals("cat", animal.name)
        // age 속성이 map 객체에 정상적으로 위임되었는지 확인
        Assert.assertEquals(20, animal.age)

        // 프로퍼티의 값을 변경한다.
        animal.age = 21
        animal.name = "dog"

        // map 의 값들이 바꼈는지 확인
        Assert.assertEquals("dog", animal.name)
        Assert.assertEquals(21, animal.age)
    }

    @Test
    fun testCollectionApi() {
        val list = listOf(1, "2", 3, 4, 5.7, 1, 2)

        println(list.filter { item -> item is Int })

        println(list.map { "value: $it" })

        println(list.filterIsInstance<Int>().map { "value: $it" })

        println(list.find { it is Double })

        val map = list.groupBy { it.javaClass }
        println(map)

        val list2 = listOf(listOf(1,2), listOf(3,4))
        println(list2)

        println(list2.map { "value: $it" })

        println(list2.flatMap { it.toList() })

    }

}