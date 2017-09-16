
enum class Columns {
  Date, Payee, Category, Memo, Outflow, Inflow
}

data class Config(
    val colNames: Map<Columns, String>,
    val commaReplacement: String
)

class Converter(
    private val config: Config
) {

  fun convert(string: String): String {
    return string
  }

}