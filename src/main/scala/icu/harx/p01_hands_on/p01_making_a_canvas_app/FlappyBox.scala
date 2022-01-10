package icu.harx.p01_hands_on.p01_making_a_canvas_app

import org.scalajs.dom
import org.scalajs.dom.html

import scala.collection.mutable
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scala.util.Random

@JSExportTopLevel("flappyBox")
object FlappyBox {

  @JSExport
  def use(
      canvas: html.Canvas,
      playVelocity: Int = 4,
      obstacleVelocity: Int = 15
  ): Int = {
    val renderer =
      canvas
        .getContext("2d")
        .asInstanceOf[dom.CanvasRenderingContext2D]

    canvas.height = 400

    renderer.font = "50px sans-serif"
    renderer.textAlign = "center"
    renderer.textBaseline = "middle"

    // 间距
    val obstacleGap = 250
    // 孔洞
    val holeSize = 60
    // 重力
    val gravity = 0.08

    // 坐标 Y
    var playerY = canvas.height / 2.0
    // 速度 V
    var playerV = 0.0

    // 是否死亡
    var dead = 0
    // 定位
    var frame = -50
    // 障碍
    val obstacles = mutable.Queue.empty[Int]

    def run(): Unit = {

      //
      // 更新渲染环境
      //

      canvas.width = canvas.parentElement.clientWidth

      lazy val rect = canvas.getBoundingClientRect()

      //
      // 定义渲染子过程
      //

      def runLive(): Unit = {
        frame += 2

        // 生成障碍
        if (frame >= 0 && frame % obstacleGap == 0)
          obstacles.enqueue(
            Random.nextInt(rect.height.floor.toInt - 2 * holeSize) + holeSize
          )

        if (obstacles.length > 7) {
          obstacles.dequeue()
          frame -= obstacleGap
        }

        // 物理
        playerY = playerY + playerV
        playerV = playerV + gravity

        // 渲染障碍
        renderer.fillStyle = "darkblue"
        for ((holeY, i) <- obstacles.zipWithIndex) {
          // 障碍物向左移动
          val holeX = i * obstacleGap - frame + rect.width

          renderer.fillRect(
            x = holeX,
            y = 0,
            w = 5,
            h = holeY - holeSize
          )

          renderer.fillRect(
            x = holeX,
            y = holeY + holeSize,
            w = 5,
            h = rect.height - holeY - holeSize
          )

          // 死亡判定
          if (
            math.abs(holeX - rect.width / 2) < 5 &&
            math.abs(holeY - playerY) > holeSize
          ) {
            dead = 50
          }
        }

        // 渲染角色
        renderer.fillStyle = "darkgreen"
        renderer.fillRect(
          x = rect.width / 2 - 5,
          y = playerY - 5,
          w = 10,
          h = 10
        )

        // 出界判定死亡
        if (playerY < rect.top || playerY > rect.bottom) {
          dead = 50
        }
      }

      def runDead(): Unit = {
        playerY = rect.top + rect.height / 2
        playerV = 0
        frame = -50
        obstacles.clear()
        dead -= 1
        renderer.fillStyle = "darkred"
        renderer.fillText(
          text = "Game Over",
          x = rect.left + rect.width / 2,
          y = rect.top + rect.height / 2
        )
      }

      //
      // 清空渲染环境
      //

      renderer.clearRect(rect.left, rect.top, rect.width, rect.height)

      //
      // 判定选择哪个子过程
      //

      if (dead > 0) runDead()
      else runLive()
    }

    canvas.onclick = _ => {
      if (playerV > -1.5) {
        playerV -= playVelocity
      }
    }

    dom.window.setInterval(handler = run _, timeout = obstacleVelocity)
  }
}
