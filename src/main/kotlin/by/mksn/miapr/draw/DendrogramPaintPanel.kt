package by.mksn.miapr.draw

import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Point
import javax.swing.JPanel

class DendrogramPaintPanel<T> internal constructor(private val root: Node<T>) : JPanel() {
    private val margin = 25
    private var heightPerLeaf: Int = 0
    private var widthPerLevel: Int = 0
    private var currentY: Int = 0


    override fun paintComponent(gr: Graphics) {
        super.paintComponent(gr)
        val g = gr as Graphics2D

        val leaves = countLeaves(root)
        val levels = countLevels(root)
        heightPerLeaf = (height - margin - margin) / leaves
        widthPerLevel = (width - margin - margin) / levels
        currentY = 0

        g.translate(margin, margin)
        draw(g, root, 0)
    }


    private fun <G> draw(g: Graphics, node: Node<G>, y: Int): Point {
        val children = node.children
        if (children == null) {
            val x = width - widthPerLevel - 2 * margin
            g.drawString(node.contents.toString(), x + 8, currentY + 8)
            val resultY = currentY
            currentY += heightPerLeaf
            return Point(x, resultY)
        } else {
            val (child0, child1) = children
            val p0 = draw(g, child0, y)
            val p1 = draw(g, child1, y + heightPerLeaf)

            g.fillRect(p0.x - 2, p0.y - 2, 4, 4)
            g.fillRect(p1.x - 2, p1.y - 2, 4, 4)
            val dx = widthPerLevel
            val vx = Math.min(p0.x - dx, p1.x - dx)
            g.drawLine(vx, p0.y, p0.x, p0.y)
            g.drawLine(vx, p1.y, p1.x, p1.y)
            g.drawLine(vx, p0.y, vx, p1.y)
            return Point(vx, p0.y + (p1.y - p0.y) / 2)
        }
    }

    companion object {
        private fun <T> countLeaves(node: Node<T>): Int {
            val children = node.children ?: return 1
            val (child0, child1) = children
            return countLeaves(child0) + countLeaves(child1)
        }

        private fun <T> countLevels(node: Node<T>): Int {
            val children = node.children ?: return 1
            val (child0, child1) = children
            return 1 + Math.max(countLevels(child0), countLevels(child1))
        }
    }
}