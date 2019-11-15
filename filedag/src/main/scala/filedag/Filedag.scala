package filedag

import java.nio.file.Path
import scala.meta.cli.Metac
import java.nio.file.Paths
import scala.meta.internal.io.FileIO
import scala.meta.io.AbsolutePath
import scala.collection.mutable
import scala.meta.internal.semanticdb.TextDocuments

object Filedag {
  def main(args: Array[String]): Unit = {
    if (args.length < 2) {
      sys.error("missing arguments")
    }
    val out = AbsolutePath(args(0))
    val sourceroot = AbsolutePath(args(1))
    val documents = for {
      semanticdb <- FileIO
        .listAllFilesRecursively(out)
        .filter(_.toNIO.getFileName().toString().endsWith(".semanticdb"))
      if semanticdb.toNIO.getFileName().toString().endsWith(".semanticdb")
      doc <- TextDocuments.parseFrom(semanticdb.readAllBytes).documents
    } yield doc

    val definitions: Map[String, String] = (for {
      doc <- documents
      occ <- doc.occurrences
      if occ.role.isDefinition
    } yield occ.symbol -> doc.uri).toMap
    val references = for {
      doc <- documents
      ref <- doc.occurrences.filter(_.role.isReference).map(_.symbol) ++
        doc.synthetics.flatMap(s => Synthetics.all(s))
      defn <- definitions.get(ref)
    } yield doc.uri -> defn
    val result = references
      .groupBy {
        case (from, _) => from
      }
      .mapValues(_.map(_._2).distinct)
    pprint.log(result)
  }
}
