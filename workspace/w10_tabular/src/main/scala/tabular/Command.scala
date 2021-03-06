package tabular

import scala.util.Try
import introprog.IO

object Command {
  var currentSeparator: Char = ','

  var currentTable: Option[Table] = None

  val shortHelpText = "Type 'help' for help on commands and 'quit' to exit app."
  val longHelpText = """Commands:
    |help                 Print this list of commands
    |ls                   List files in current directory
    |load                 Load a file using introprog.Dialog.file
    |load filename|url    Load a table from <location> using current separator
    |save                 Save
    |save filename        Save current table to <filepath>
    |sep                  Show current column separator
    |sep c                Change current column separator to char c
    |show                 Print current table to the console
    |sort h               Sort current table on heading h
    |filter h a b c ...   Keep rows where heading h has values a b c ...
    |pie h                Draw a pie chart of current table's heading h
    |bar h                Draw a bar chart of current table's heading h
    |quit, Ctrl+D         Terminate app
    |""".stripMargin

   def fromUser(): Vector[String] = {
     // readLine can give null if Ctrl+D is typed by user (Linux, MacOS)
     val cmdOpt = Option(scala.io.StdIn.readLine("> ")) // None if null
     cmdOpt.map(_.split(' ').toVector.map(_.trim)).getOrElse(Vector("quit"))
   }

   def TODO = "Command not yet implemented."

   def load(fileOrUrl: String, separator: Char): String = TODO

   def save(file: String, separator: Char): String = {
     Table.save(file, separator)
     s"Table saved to file: $file"
   }

   def doCommand(cmd: Vector[String]): String  = cmd match {
     case Vector("") | Vector()            => shortHelpText
     case Vector("help")                   => longHelpText
     case Vector("ls")                     => IO.list(IO.currentDir).mkString(" ")
     case Vector("load", fileOrUrl)        => load(fileOrUrl, currentSeparator)
     case Vector("quit")                   => "quit"
     case _ => s"""Invalid command: ${cmd.mkString(" ")} \n$shortHelpText\n"""
   }

   def loopUntilQuit(): Unit = {
     println(shortHelpText)
     var quit = false
     while (!quit) {
       val result = doCommand(fromUser())
       if (result == "quit") quit = true else println(result)
     }
     println("Goodbye!")
   }
}
