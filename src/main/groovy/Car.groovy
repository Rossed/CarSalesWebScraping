/**
 * Created by rossedwa on 16/4/17.
 */
class Car implements Comparable<Car> {

    def price
    def carDescription
    def lowerBoundPrice
    def upperBoundPrice
    Choice choice
    def carWebsite
    def valuationWebsite

    Car(carDescription, price, lowerBoundPrice, upperBoundPrice, choice, carWebsite, valuationWebsite) {
        this.carDescription = carDescription
        this.price = price
        this.lowerBoundPrice = lowerBoundPrice
        this.upperBoundPrice = upperBoundPrice
        this.choice = choice
        this.carWebsite = carWebsite
        this.valuationWebsite = valuationWebsite
    }

    @Override
    String toString() {
        return String.format("%s\nPrice: %s, lower bound: %s, upper bound: %s\nDecision: %s", carDescription, price, lowerBoundPrice, upperBoundPrice, choice)
    }

    @Override
    int compareTo(Car car) {
        if(car.choice.ordinal() < this.choice.ordinal())
            return 1
        else if(car.choice.ordinal() > this.choice.ordinal())
            return -1
        else
            return 1
    }
}

enum Choice {
    GOOD,
    MEDIUM,
    BAD
}
