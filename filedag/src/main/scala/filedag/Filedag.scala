package filedag

import java.nio.file.Path
import scala.meta.cli.Metac

object Filedag {
  def main(args: Array[String]): Unit = {
    Metac.process(args)
  }
}