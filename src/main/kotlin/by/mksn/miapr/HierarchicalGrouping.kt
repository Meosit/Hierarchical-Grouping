package by.mksn.miapr

fun <T> combinations(arr: Array<T>): List<Pair<T, T>> {
    val numCombinations = Math.pow(arr.size.toDouble(), 2.0).toInt()
    val list = mutableListOf<Pair<T, T>>()
    for (i in 0..numCombinations - 1) {
        val first = arr[i / arr.size % arr.size]
        val second = arr[i % arr.size]
        if (first != second && !list.contains(second to first))
            list.add(first to second)
    }
    return list
}
