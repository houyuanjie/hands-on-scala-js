package icu.harx.p01_hands_on.p02_interactive_web_page

import org.scalajs.dom.Node
import org.scalajs.dom.html.*
import scalatags.JsDom.all.*
import sttp.client3.{FetchBackend, UriContext, basicRequest}

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("weatherSearch")
object WeatherSearch {
  // 如果这段代码是你从互联网上找到, 请在 http://openweathermap.org 注册一个新的 key
  val key = "2c222f5b164e9931e3d9844bb9bf275e"

  lazy val box: Input =
    input(
      `type`      := "text",
      placeholder := "Type here!"
    ).render

  lazy val output: Div =
    div(
      height    := "400px",
      overflowY := "scroll"
    ).render

  box.onkeydown = _ => {
    output.innerHTML = "Enter to search..."
    fetchWeather(box.value)
  }

  def celsius(kelvins: String): Int =
    (kelvins.toDouble - 273.15).toInt

  def showResults(jsonlist: js.Array[js.Dynamic], query: String): Unit = {
    for (json <- jsonlist) {
      val name    = json.name.toString
      val country = json.sys.country.toString
      val weather = json.weather.pop().main.toString

      val min           = celsius(json.main.temp_min.toString)
      val max           = celsius(json.main.temp_max.toString)
      val humid         = json.main.humidity.toString
      val (first, last) = name.splitAt(query.length)

      output.innerHTML = ""
      output.appendChild(
        div(
          b(
            span(first, backgroundColor := "yellow"),
            last,
            ", ",
            country
          ),
          ul(
            li(b("Weather "), weather),
            li(b("Temp "), min, " - ", max),
            li(b("Humidity "), humid, "%")
          )
        ).render
      )
    }
  }

  def fetchWeather(query: String): Unit = {
    val searchUrl =
      s"http://api.openweathermap.org/data/2.5/find?mode=json&q=$query&appid=$key"

    val backend = FetchBackend()

    if (query == box.value && query.length > 3) {
      for (response <- backend.send(basicRequest.get(uri"$searchUrl"))) {
        response.body match {
          case Left(err) =>
            output.innerHTML = err
          case Right(json) =>
            showResults(
              js.JSON.parse(json).list.asInstanceOf[js.Array[js.Dynamic]],
              query
            )
        }
      }
    }
  }

  @JSExport
  def use(target: Div): Node = {
    target.appendChild(
      div(
        h1("Weather Search"),
        p(
          "Enter the name of a city to pull the ",
          "latest weather data from api.openweathermap.com!"
        ),
        p(box),
        hr,
        output,
        hr
      ).render
    )
  }
}
