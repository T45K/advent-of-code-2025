fun <T> Iterable<T>.chunkedBy(predicate: (T) -> Boolean): List<List<T>> {
    val acc = mutableListOf<List<T>>()
    val tmp = mutableListOf<T>()
    forEach {
        if (predicate(it)) {
            if (tmp.isNotEmpty()) acc += tmp.toList()
            tmp.clear()
        } else {
            tmp += it
        }
    }
    if (tmp.isNotEmpty()) acc.add(tmp)
    return acc
}
