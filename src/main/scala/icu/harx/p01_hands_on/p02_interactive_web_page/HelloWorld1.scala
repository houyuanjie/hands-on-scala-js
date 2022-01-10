package icu.harx.p01_hands_on.p02_interactive_web_page

import org.scalajs.dom.html
import scalatags.JsDom.all.*

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("helloWorld1")
object HelloWorld1 {

  @JSExport
  def use(target: html.Div): Unit = {
    val (animalA, animalB) = ("fox", "dog")

    target.appendChild(
      div(
        h1("Hello World!"),
        p(
          "The quick brown ",
          b(animalA),
          " jumps over the lazy ",
          i(animalB),
          "."
        )
      ).render
    )
  }
}
