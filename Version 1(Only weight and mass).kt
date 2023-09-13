package converter

fun main() {
    while (true) {
        print("Enter what you want to convert (or exit): ")
        val input = readln().lowercase()
        if (input == "exit") break
        val list = input.split(' ').toMutableList()
        calculate(list)
    }
}

fun calculate(list: MutableList<String>) {
    val sourceUnit = list[1].lowercase()
    val targetUnit = list[3].lowercase()

    val sourceType = Unit.values().find { sourceUnit.lowercase() in it.list }
    val targetType = Unit.values().find { targetUnit.lowercase() in it.list }

    val value = list[0].toDouble()

    if (sourceType == null || targetType == null) {
        val sourceSuffix2 = if (Unit.values().contains(sourceType)) sourceType!!.list[2].lowercase() else "???"
        val targetSuffix2 = if (Unit.values().contains(targetType)) targetType!!.list[2].lowercase() else "???"
        println("Conversion from $sourceSuffix2 to $targetSuffix2 is impossible")
    } else if (sourceType.unitType != targetType.unitType) {
        val sourceSuffix1 = if (sourceType.list.contains(list[1])) sourceType.list[2].lowercase() else "???"
        val targetSuffix1 = if (targetType.list.contains(list[3])) targetType.list[2].lowercase() else "???"
        println("Conversion from $sourceSuffix1 to $targetSuffix1 is impossible")
    } else if (targetType != null && sourceType != null) {
        val resultOfConversion = value * sourceType.multiplier / targetType.multiplier
        val sourceSuffix = if (value != 1.0) sourceType.list[2] else sourceType.list[1]
        val targetSuffix = if (resultOfConversion != 1.0) targetType.list[2] else targetType.list[1]
        println("$value $sourceSuffix is $resultOfConversion $targetSuffix")
    }
}

enum class Unit(
    val list: List<String>,
    val multiplier: Double,
    val unitType: String
) {
    // Units of Length
    METER(listOf("m", "meter", "meters"), 1.0, "length"),
    KILOMETER(listOf("km", "kilometer", "kilometers"), 1000.0, "length"),
    CENTIMETERS(listOf("cm", "centimeter", "centimeters"), 0.01, "length"),
    MILLIMETERS(listOf("mm", "millimeter", "millimeters"), 0.001, "length"),
    MILES(listOf("mi", "mile", "miles"), 1609.35, "length"),
    YARDS(listOf("yd", "yard", "yards"), 0.9144, "length"),
    FEET(listOf("ft", "foot", "feet"), 0.3048, "length"),
    INCHES(listOf("in", "inch", "inches"), 0.0254, "length"),

    // Units of weight
    GRAM(listOf("g", "gram", "grams"), 1.0, "weight"),
    KILOGRAMS(listOf("kg", "kilogram", "kilograms"), 1000.0, "weight"),
    MILLIGRAMS(listOf("mg", "milligram", "milligrams"), 0.001, "weight"),
    POUNDS(listOf("lb", "pound", "pounds"), 453.592, "weight"),
    OUNCE(listOf("oz", "ounce", "ounces"), 28.3495, "weight"),
}
