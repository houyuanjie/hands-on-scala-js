package icu.harx.p01_hands_on.p02_interactive_web_page

import org.scalajs.dom.html

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("helloWorld0")
object HelloWord0 {

  @JSExport
  def use(target: html.Div): Unit = {
    val (f, d) = ("fox", "dog")

    target.innerHTML = s"""
         |<div>
         |  <h1>Hello World!</h1>
         |  <p>
         |    The quick brown <b>$f</b>
         |    jumps over the lazy <i>$d</b>
         |  </p>
         |</div>
         |""".stripMargin
  }
}
