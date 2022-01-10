package icu.harx.p01_hands_on.p01_making_a_canvas_app

import org.scalajs.dom
import org.scalajs.dom.html

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("sketchpad")
object Sketchpad {

  @JSExport
  def use(canvas: html.Canvas): Unit = {
    val renderer =
      canvas
        .getContext("2d")
        .asInstanceOf[dom.CanvasRenderingContext2D]

    canvas.width = canvas.parentElement.clientWidth
    canvas.height = canvas.parentElement.clientHeight

    renderer.fillStyle = "#f8f8f8"
    renderer.fillRect(0, 0, canvas.width, canvas.height)

    renderer.fillStyle = "black"

    var down = false

    canvas.onmouseup = _ => down = false
    canvas.onmousedown = _ => down = true
    canvas.onmousemove = e => {
      val rect = canvas.getBoundingClientRect()

      if (down) {
        renderer.fillRect(
          x = e.clientX - rect.left,
          y = e.clientY - rect.top,
          w = 5,
          h = 5
        )
      }
    }
  }
}
