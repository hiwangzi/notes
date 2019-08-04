package taxipark

import kotlin.math.roundToInt

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        this.allDrivers.filter { driver -> this.trips.none { trip -> trip.driver == driver } }.toSet()


/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        this.allPassengers.associateWith { passenger -> this.trips.filter { trip -> trip.passengers.contains(passenger) }.size }
                .filter { it.value >= minTrips }.keys


/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        this.trips.filter { it.driver == driver }.flatMap { it.passengers }.groupBy { it }.filter { it.value.size > 1 }.keys


/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        this.allPassengers.filter { passenger ->
            val trips = this.trips.filter { it.passengers.contains(passenger) }
            val discountedTrips = trips.filter { it.discount != null }
            discountedTrips.isNotEmpty() && trips.size / discountedTrips.size < 2
        }.toSet()


/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    return if (trips.isEmpty()) {
        null
    } else {
        val groupByDuration = this.trips.groupBy { it.duration }
        val rangeEnd = groupByDuration.keys.max()?.plus(10) ?: 9
        fun foo(range: IntRange) = range to groupByDuration.filter { it.key in range }.map { it.value.size }.sum()
        val rangeList: MutableList<IntRange> = mutableListOf()
        for (i in 0..rangeEnd step 10) {
            rangeList.add(i..i + 9)
        }
        rangeList.map { foo(it) }.maxBy { it.second }?.first
    }
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    return if (trips.isEmpty()) {
        false
    } else {
        val sortedDriverIncome = this.trips.groupBy { it.driver }.mapValues { entry -> entry.value.sumByDouble { it.cost } }.entries.sortedByDescending { it.value }
        val twentyPercentDriverIncome = sortedDriverIncome.subList(0, (this.allDrivers.size * 0.2).roundToInt()).sumByDouble { it.value }
        val totalIncome = sortedDriverIncome.sumByDouble { it.value }
        twentyPercentDriverIncome / totalIncome >= 0.8
    }
}