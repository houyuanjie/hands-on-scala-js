package icu.harx.p01_hands_on.p01_making_a_canvas_app

import org.scalajs.dom
import org.scalajs.dom.html

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("clock")
object Clock {

  @JSExport
  def use(canvas: html.Canvas): Int = {
    def render(): Unit = {
      lazy val rect = canvas.getBoundingClientRect()

      lazy val renderer: dom.CanvasRenderingContext2D =
        canvas
          .getContext("2d")
          .asInstanceOf[dom.CanvasRenderingContext2D]

      lazy val gradient = {
        lazy val linearGradient = renderer.createLinearGradient(
          x0 = canvas.width / 2 - 100,
          y0 = 0,
          x1 = canvas.width / 2 + 100,
          y1 = 0
        )

        linearGradient.addColorStop(0, "red")
        linearGradient.addColorStop(0.5, "green")
        linearGradient.addColorStop(1, "blue")

        linearGradient
      }

      lazy val date = new js.Date()

      canvas.width = dom.window.innerWidth.floor.toInt
      canvas.height = dom.window.innerHeight.floor.toInt

      renderer.fillStyle = gradient
      renderer.textAlign = "center"
      renderer.textBaseline = "middle"

      renderer.clearRect(rect.left, rect.top, rect.width, rect.height)
      renderer.font = "75px sans-serif"
      renderer.fillText(
        text = Seq(
          date.getHours(),
          date.getMinutes(),
          date.getSeconds()
        ).mkString(":"),
        canvas.width / 2,
        canvas.height / 2
      )
    }

    dom.window.setInterval(
      handler = render _,
      timeout = 1000
    )
  }
}
