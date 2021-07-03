package com.techtown.kotlinsample

// 코틀린 타입은 널(Null)을 허용 하지 않는다.
fun strLenNonNull(str: String): Int {
    return str.length
}

// 만일 널(Null) 가능성이 있다면 타입에 ?를 붙여야 한다.
fun strLenNullable(str: String?): Int {
//    return str.length

    if (str != null) {
        return str.length
    } else {
        return 0
    }
}

// 문자열에 끝 Char 를 반환한다.
fun strLastCharNullable(str: String?): Char? {
    // ?. 연산자는 str 이 Null 이면 null 이 반환된다
    return str?.get(str.length - 1)
}