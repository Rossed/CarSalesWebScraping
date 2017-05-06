import groovy.json.JsonSlurper

class CarSorter {

    def static test = []

    static void main(String[] args) throws IOException {

		File f = new File("src/main/resources/car.json")
        def slurper = new JsonSlurper()
        def result = slurper.parse(f)
        for (int i = 0; i < 3 ; i++) {
            for (def thing : result) {
                println thing.choice
                if ( i == 2 && thing.choice == "BAD") {
                    test << thing
                } else if (i==1 && thing.choice == "MEDIUM") {
                    test << thing
                } else if (i==0 && thing.choice == "GOOD") {
                    test << thing
                }

            }
        }

        println test.each { it -> println it}
    }
}