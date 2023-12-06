class AlmanacWithMorePlants(private val input: List<String>) {
    private var minLocation = Long.MAX_VALUE

    private var seedSoilMap = mutableMapOf<LongRange, Long>()     // start..end: step
    private var soilFertilizerMap = mutableMapOf<LongRange, Long>()
    private var fertilizerWaterMap = mutableMapOf<LongRange, Long>()
    private var waterLightMap = mutableMapOf<LongRange, Long>()
    private var lightTemperatureMap = mutableMapOf<LongRange, Long>()
    private var tempHumidityMap = mutableMapOf<LongRange, Long>()
    private var humLocationMap = mutableMapOf<LongRange, Long>()

    var soil = 0L
    var fertilizer = 0L
    var water = 0L
    var light = 0L
    var temp = 0L
    var hum = 0L
    var loc = 0L

    val list = listOf(
        "seed-to-soil",
        "soil-to-fertilizer",
        "fertilizer-to-water",
        "water-to-light",
        "light-to-temperature",
        "temperature-to-humidity",
        "humidity-to-location",
    )

    init {
        updateMaps(list[0], seedSoilMap)
        updateMaps(list[1], soilFertilizerMap)
        updateMaps(list[2], fertilizerWaterMap)
        updateMaps(list[3], waterLightMap)
        updateMaps(list[4], lightTemperatureMap)
        updateMaps(list[5], tempHumidityMap)
        updateMaps(list[6], humLocationMap)
    }

    private fun updateMaps(name: String, map: MutableMap<LongRange, Long>) {
        val firstIndex = input.indexOfFirst { it.contains(name) }
        var index = firstIndex + 1
        while (input[index].isNotEmpty() && index < input.lastIndex) {
            val currentLine = input[index]   // 0 1551952886 33233684
            currentLine.split(" ").apply {// loop into each line of the map
                val start = this[1].toLong()   // src
                val destination = this.first().toLong()  //destination
                val length = last().toLong()
                val step = destination - start
                val end = start + length - 1
                map.put(start..end, step)
            }
            index += 1
        }
    }

    fun getValue(i: Long, map: Map<LongRange, Long>): Long {
        for ((range, step) in map) {
            if (i in range) {
                return i + step
            }
        }
        return i
    }

    fun getMinLocation(): Long {
        val listOfMapTitles = listOf(
            "seed-to-soil",
            "soil-to-fertilizer",
            "fertilizer-to-water",
            "water-to-light",
            "light-to-temperature",
            "temperature-to-humidity",
            "humidity-to-locatio",
        )
        input.first().split(":").last().trim().split(" ").chunked(2).forEach { pair ->
            val start = pair.first().toLong()
            val end = start + pair.last().toLong()

            for (i in start..end) {
                soil = getValue(i, seedSoilMap)
                fertilizer = getValue(soil, soilFertilizerMap)
                water = getValue(fertilizer, fertilizerWaterMap)
                light = getValue(water, waterLightMap)
                temp = getValue(light, lightTemperatureMap)
                hum = getValue(temp, tempHumidityMap)
                loc = getValue(hum, humLocationMap)
                if (loc < minLocation) minLocation = loc
            }
        }
        return minLocation
    }
}
