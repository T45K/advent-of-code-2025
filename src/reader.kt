import kotlin.io.path.Path
import kotlin.io.path.readLines

fun readFile(name: String): List<String> = Path("input", name).readLines()
