package icu.harx

import org.scalajs.dom
import org.scalajs.dom.html

import scala.math.{abs, sin}
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("splash")
object Splash {

  case class Graph(color: String, f: Double => Double)

  @JSExport
  def use(canvas: html.Canvas): Int = {

    def h: Int = canvas.height
    def w: Int = canvas.width

    def clear(): Unit = {
      canvas.width = canvas.parentElement.clientWidth
      canvas.height = canvas.parentElement.clientHeight
    }

    lazy val brush =
      canvas
        .getContext("2d")
        .asInstanceOf[dom.CanvasRenderingContext2D]

    val graphs = Seq(
      Graph("red", sin),
      Graph("green", x => abs(x % 4 - 2) - 1),
      Graph("blue", x => sin(x / 12) * sin(x))
    ).zipWithIndex

    clear()

    var x = 0.0

    dom.window.setInterval(
      handler = () => {
        x = (x + 1) % w
        if (x == 0) clear()

        for (case (Graph(c, f), i) <- graphs) {
          val offset = h / 3 * (i + 0.5)
          val y      = f(x / w * 75) * h / 30

          brush.fillStyle = c
          brush.fillRect(x, y + offset, 3, 3)
        }
      },
      timeout = 20
    )
  }
}
