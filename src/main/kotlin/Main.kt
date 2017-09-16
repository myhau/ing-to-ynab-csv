fun main(args: Array<String>) {
  val lines = System.`in`.bufferedReader().lineSequence().joinToString()

  println(Converter(Config(emptyMap(), ",")).convert(lines))
}
