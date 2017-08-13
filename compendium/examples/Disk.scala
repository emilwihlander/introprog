object Disk {
  def saveString(s: String, fileName: String, enc: String = "UTF-8"): Unit = {
    val f = new java.io.File(fileName)
    val pw = new java.io.PrintWriter(f, enc)
    try pw.write(s) finally pw.close()
  }

  def saveLines(lines: Seq[String], fileName: String, enc: String = "UTF-8"): Unit =
    saveString(lines.mkString("\n"), fileName, enc)

  def loadString(fileName: String, enc: String = "UTF-8"): String =
    scala.io.Source.fromFile(fileName, enc).mkString

  def loadLines(fileName: String, enc: String = "UTF-8"): Vector[String] =
    scala.io.Source.fromFile(fileName, enc).getLines.toVector

  def saveObject[T](obj: T, fileName: String): Unit = {
    val f = new java.io.File(fileName)
    val oos = new java.io.ObjectOutputStream(new java.io.FileOutputStream(f))
    try oos.writeObject(obj) finally oos.close()
  }

  def loadObject[T](fileName: String): T = {
    val f = new java.io.File(fileName)
    val ois = new java.io.ObjectInputStream(new java.io.FileInputStream(f))
    try { ois.readObject.asInstanceOf[T] } finally ois.close()
  }

  def isExisting(fileName: String): Boolean = new java.io.File(fileName).exists

  def createDirIfNotExist(dir: String): Boolean = new java.io.File(dir).mkdirs()

  def userDir: String = System.getProperty("user.home")

  def currentDir: String =
    java.nio.file.Paths.get(".").toAbsolutePath.normalize.toString
}
