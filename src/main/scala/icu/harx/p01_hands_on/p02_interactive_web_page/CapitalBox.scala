package icu.harx.p01_hands_on.p02_interactive_web_page

import org.scalajs.dom
import org.scalajs.dom.html.{Input, Span}
import scalatags.JsDom.all.*

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("capitalBox")
object CapitalBox {
  val box: Input =
    input(
      `type`      := "text",
      placeholder := "Type here!"
    ).render

  val output: Span =
    span.render

  box.onkeyup = _ => output.textContent = box.value.toUpperCase

  @JSExport
  def use(target: dom.html.Div): Unit = {
    target.appendChild(
      div(
        h1("Capitial Box"),
        p("Type here and have it capitalized!"),
        div(box),
        div(output)
      ).render
    )
  }
}
