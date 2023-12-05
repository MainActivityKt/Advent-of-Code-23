class Almanac(private val input: List<String>, val plantMoreSeeds: Boolean = false) {
    private val seeds = mutableListOf<Long>()
    private val seedToSoil = mutableMapOf<Long, Long>()
    private val soilToFertilizer = mutableMapOf<Long, Long>()
    private val fertilizerToWater = mutableMapOf<Long, Long>()
    private val waterToLight = mutableMapOf<Long, Long>()
    private val lightToTemp = mutableMapOf<Long, Long>()
    private val tempToHumidity = mutableMapOf<Long, Long>()
    private val humToLocation = mutableMapOf<Long, Long>()

    private var destination = 0L
    private var source = 0L
    private var length = 0L
    private var range = 0L..1L

    init {
        getSeeds()
        updateSeedToSoil()
        updateSoilToFertilizer()
        updateFertilizerToWater()
        updateWaterToLight()
        updateLightToTemp()
        updateTempToHumidity()
        updateHumToLocation()
    }


    fun getMinLocation(): Long {
        seeds.forEach {
            val seed = it
            val soil = seedToSoil[seed]
            val fertilizer = soilToFertilizer[soil]
            val water = fertilizerToWater.get(fertilizer)
            val light = waterToLight.get(water)
            val temp = lightToTemp.get(light)
            val hum = tempToHumidity.get(temp)
            val loc = humToLocation.get(hum)
//            println("seed # $it: soil: $soil fertilizer: ${fertilizer} water: $water light: $light temp: $temp hum: $hum loc: $loc")  debug

        }
        return humToLocation.values.min()
    }
    private fun getSeeds() {
        input.first().split(":").last().trim().split(" ").forEach {
            seeds.add(it.toLong())
        }
    }

    private fun updateSeedToSoil() {
        val firstIndex = input.indexOf("seed-to-soil map:")
        var index = firstIndex + 1
        while (input[index].isNotEmpty()) {
            val currentLine = input[index]   //debugging
            updateValues(currentLine)

            seeds.forEach { seed ->
                val value = seed - source + destination
                if (!seedToSoil.containsKey(seed) || seedToSoil[seed] == seed) {
                    seedToSoil.put(seed, if (seed in range) value else seed)
                }
            }
            index += 1
        }

    }

    private fun updateSoilToFertilizer() {
        val firstIndex = input.indexOfFirst { it.contains("soil-to-fertilizer") }
        var index = firstIndex + 1
        while (input[index].isNotEmpty()) {
            val currentLine = input[index]   //debugging
            updateValues(currentLine)

            seedToSoil.values.forEach { soil ->
                val value = soil - source + destination
                if (!soilToFertilizer.containsKey(soil) || soilToFertilizer[soil] == soil) {
                    soilToFertilizer.put(soil, if (soil in range) value else soil)
                }
            }
            index += 1
        }

    }

    private fun updateFertilizerToWater() {
        val firstIndex = input.indexOfFirst { it.contains("fertilizer-to-water") }
        var index = firstIndex + 1
        while (input[index].isNotEmpty()) {
            val currentLine = input[index]   //debugging
            updateValues(currentLine)

            soilToFertilizer.values.forEach { fertilizer ->
                val value = fertilizer - source + destination
                if (!fertilizerToWater.containsKey(fertilizer) || fertilizerToWater[fertilizer] == fertilizer) {
                    fertilizerToWater.put(fertilizer, if (fertilizer in range) value else fertilizer)
                }
            }
            index += 1
        }
    }

    private fun updateWaterToLight() {
        val firstIndex = input.indexOfFirst { it.contains("water-to-light") }
        var index = firstIndex + 1
        while (input[index].isNotEmpty()) {
            val currentLine = input[index]   //debugging
            updateValues(currentLine)

            fertilizerToWater.values.forEach { water ->
                val value = water - source + destination
                if (!waterToLight.containsKey(water) || waterToLight[water] == water) {
                    waterToLight.put(water, if (water in range) value else water)
                }
            }
            index += 1
        }
    }

    private fun updateLightToTemp() {
        val firstIndex = input.indexOfFirst { it.contains("light-to-temp") }
        var index = firstIndex + 1
        while (input[index].isNotEmpty()) {
            val currentLine = input[index]   //debugging
            updateValues(currentLine)

            waterToLight.values.forEach { light ->
                val value = light - source + destination
                if (!lightToTemp.containsKey(light) || lightToTemp[light] == light) {
                    lightToTemp.put(light, if (light in range) value else light)
                }
            }
            index += 1
        }
    }

    private fun updateTempToHumidity() {
        val firstIndex = input.indexOfFirst { it.contains("temperature-to-humidity") }
        var index = firstIndex + 1
        while (input[index].isNotEmpty()) {
            val currentLine = input[index]   //debugging
            updateValues(currentLine)

            lightToTemp.values.forEach { temp ->
                val value = temp - source + destination
                if (!tempToHumidity.containsKey(temp) || tempToHumidity[temp] == temp) {
                    tempToHumidity.put(temp, if (temp in range) value else temp)
                }
            }
            index += 1
        }
    }

    private fun updateHumToLocation() {
        val firstIndex = input.indexOfFirst { it.contains("humidity-to-location") }
        var index = firstIndex + 1
        while (input[index].isNotEmpty() && index < input.lastIndex) {
            val currentLine = input[index]   //debugging
            updateValues(currentLine)

            tempToHumidity.values.forEach { hum ->
                val value = hum - source + destination
                if (!humToLocation.containsKey(hum) || humToLocation[hum] == hum) {
                    humToLocation.put(hum, if (hum in range) value else hum)
                }
            }
            index += 1
        }
    }

    private fun updateValues(line: String) {
        line.split(" ").apply {
            destination = first().toLong()
            source = this[1].toLong()
            length = last().toLong()
            range = source..<source + length
        }
    }


}


fun main() {
    fun part1(input: List<String>): Int {
        return Almanac(input).getMinLocation().toInt()
    }

    fun part2(input: List<String>): Any {
        return -1
    }

    val testInput = readInput("Day05_test")
    val input = readInput("Day05")
    check(part1(testInput) == 35)
    check(part2(testInput) == -1)

    part1(input).println()
//    part2(input).println()

}