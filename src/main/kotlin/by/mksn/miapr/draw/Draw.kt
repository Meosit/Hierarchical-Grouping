package by.mksn.miapr.draw

import javax.swing.JFrame
import javax.swing.WindowConstants

data class Node<T>(
        val contents: T? = null,
        val children: Pair<Node<T>, Node<T>>? = null
)

fun <T> drawDendrogram(root: Node<T>, width: Int = 1000, height: Int = 800) {
    val f = JFrame()
    f.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

    val panel = DendrogramPaintPanel(root)
    f.contentPane.add(panel)

    f.setSize(width, height)
    f.setLocationRelativeTo(null)
    f.isVisible = true
}

fun <T> create(contents: T): Node<T> {
    return Node(contents)
}

fun <T> create(child0: Node<T>, child1: Node<T>): Node<T> {
    return Node(children = child0 to child1)
}