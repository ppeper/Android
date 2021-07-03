package com.techtown.kotlinsample

import java.util.*

class Person(val name: String) {
    var age: Int = 0

    var nickname: String = ""
    set(value) {
        // field 는 Setter 의 대상이 되는 field 를 의미
        field = value.lowercase(Locale.getDefault())
    }

}