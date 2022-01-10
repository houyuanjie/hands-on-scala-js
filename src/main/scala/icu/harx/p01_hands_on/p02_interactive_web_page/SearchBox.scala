package icu.harx.p01_hands_on.p02_interactive_web_page

import org.scalajs.dom.html
import org.scalajs.dom.html.{Div, Input, UList}
import scalatags.JsDom.all.*

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("searchBox")
object SearchBox {
  val fruits =
    Seq(
      "Apple",
      "Apricot",
      "Banana",
      "Cherry",
      "Mango",
      "Mangosteen",
      "Mandarin",
      "Grape",
      "Grapefruit",
      "Guava"
    )

  lazy val box: Input =
    input(
      `type`      := "text",
      placeholder := "Type here!"
    ).render

  def renderFruits: UList =
    ul(
      for {
        fruit <- fruits if fruit.toLowerCase.startsWith(
          box.value.toLowerCase
        )
      } yield {
        val (first, last) = fruit.splitAt(box.value.length)

        li(
          span(background := "yellow", first),
          last
        )
      }
    ).render

  lazy val output: Div =
    div(renderFruits).render

  box.onkeyup = _ => {
    output.innerHTML = ""
    output.appendChild(renderFruits)
  }

  @JSExport
  def use(target: html.Div): Unit = {
    target.appendChild(
      div(
        h1("Search Box"),
        p("Type here to search the list of fruits below!"),
        div(box),
        div(output)
      ).render
    )
  }
}
