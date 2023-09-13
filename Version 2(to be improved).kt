fun main() {
    while (true) {
        print("Enter what you want to convert (or exit): ")
        val input = readln().lowercase()
        if (input == "exit") break
        val list = input.split(' ').toMutableList()
        calculate(list)
    }
}

private fun calculate(list: MutableList<String>) {
    val sourceUnit = list[1].lowercase()
    val targetUnit = list[3].lowercase()

    val sourceType = Unit.values().find { sourceUnit.lowercase() in it.list }
    val targetType = Unit.values().find { targetUnit.lowercase() in it.list }

    val value = list[0].toDouble()

    if (sourceType == null || targetType == null) {
        val sourceSuffix = if (sourceType == null) list[1] else "???"
        val targetSuffix = if (targetType == null) list[3] else "???"
        println("Conversion from ${sourceSuffix.toLowerCase()} to ${targetSuffix.toLowerCase()} is impossible")
    } else if (sourceType.unitType != targetType.unitType) {
        val sourceSuffix = sourceType.list.find { it in list[1] } ?: "???"
        val targetSuffix = targetType.list.find { it in list[3] } ?: "???"
        println("Conversion from ${sourceSuffix.toLowerCase()} to ${targetSuffix.toLowerCase()} is impossible")
    } else if (targetType != null && sourceType != null) {
        val resultOfConversion = if (sourceType.unitType == "temperature" && targetType.unitType == "temperature") {
            convertTemperature(value, sourceType, targetType)
        } else {
            value * sourceType.multiplier / targetType.multiplier
        }
        val sourceSuffix = if (value != 1.0) sourceType.list[2] else sourceType.list[1]
        val targetSuffix = if (resultOfConversion != 1.0) targetType.list[2] else targetType.list[1]
        println("$value ${sourceSuffix.toLowerCase()} is $resultOfConversion ${targetSuffix.toLowerCase()}")
    }
}
private fun convertTemperature(value: Double, sourceType: Unit, targetType: Unit): Double {
    return when {
        sourceType == Unit.CELSIUS && targetType == Unit.FAHRENHEIT ->
            value * 9 / 5 + 32
        sourceType == Unit.CELSIUS && targetType == Unit.KELVIN ->
            value + 273.15
        sourceType == Unit.FAHRENHEIT && targetType == Unit.CELSIUS ->
            (value - 32) * 5 / 9
        sourceType == Unit.FAHRENHEIT && targetType == Unit.KELVIN ->
            (value + 459.67) * 5 / 9
        sourceType == Unit.KELVIN && targetType == Unit.CELSIUS ->
            value - 273.15
        sourceType == Unit.KELVIN && targetType == Unit.FAHRENHEIT ->
            value * 9 / 5 - 459.67
        else -> value // Default case if units are the same
    }
}

private enum class Unit(
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
    // Units of temperature
    CELSIUS(listOf("c", "degree Celsius", "degrees Celsius"), 1.0, "temperature"),
    FAHRENHEIT(listOf("f", "degree Fahrenheit", "degrees Fahrenheit"), 1.0, "temperature"),
    KELVIN(listOf("k", "kelvin", "kelvins"), 1.0, "temperature")
}
