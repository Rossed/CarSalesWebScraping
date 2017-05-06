/**
 * Created by rossedwa on 16/4/17.
 */
class Car {

    def price
    def Odometer
    def carDescription
    def lowerBoundPrice
    def upperBoundPrice
    ChoicePrice choicePrice
    def carWebsite
    def valuationWebsite
    ChoiceOdometer choiceOdometer

    Car(carDescription, price, lowerBoundPrice, upperBoundPrice, choicePrice, choiceOdometer, carWebsite, valuationWebsite, Odometer) {
        this.carDescription = carDescription
        this.price = price
        this.lowerBoundPrice = lowerBoundPrice
        this.upperBoundPrice = upperBoundPrice
        this.choicePrice = choicePrice
        this.choiceOdometer = choiceOdometer
        this.carWebsite = carWebsite
        this.valuationWebsite = valuationWebsite
        this.Odometer = Odometer
    }

//    @Override
//    String toString() {
//        return String.format("%s\nPrice: %s, lower bound: %s, upper bound: %s\nDecision: %s", carDescription, price, lowerBoundPrice, upperBoundPrice, choice, Odometer)
//    }
}

enum ChoicePrice {
    GOOD,
    MEDIUM,
    BAD
}

enum Choice {
    GOOD,
    MEDIUM,
    BAD
}


enum ChoiceOdometer {
    GOOD,
    MEDIUM,
    BAD
}


