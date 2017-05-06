import groovy.json.JsonOutput
import groovy.json.JsonSlurper

/**
 * Created by rossedwa on 17/4/17.
 */
class debug {
    static void main(String[] args) {


        File f = new File("src/main/resources/weather.json")
        JsonSlurper jsonSlurper = new JsonSlurper()

        def obj = jsonSlurper.parse(f)

        //println obj
        obj["year"]["2017"]["month"]["04"]["day"]["17"]["hour"].each { hour ->
            println hour
        }

        //Year newYear = new Year(obj[""])

//        def obj = new Year("2017", "04", "14", "00", "16", "0mm")
//
//        def weather
//
//        weather = obj
//        Year obj3 = weather
//        obj3.update("2017", "04", "15", "01", "17", "1mm")
//        weather = obj3
//        obj3 = weather
//        obj3.update("2017","04", "15", "02", "18", "1mm")
//        weather = obj3
//        obj3 = weather
//        obj3.update("2017","04", "15", "03", "18", "1mm")
//        weather = obj3
//        obj3 = weather
//        obj3.update("2017","04", "16", "03", "18", "1mm")
//        weather = obj3
//        obj3 = weather
//        obj3.update("2017","04", "16", "03", "18", "1mm")
//        weather = obj3
//        obj3 = weather
//        obj3.update("2017","04", "16", "04", "18", "1mm")
//        weather = obj3
//        obj3 = weather
//        obj3.update("2018","04", "16", "03", "18", "1mm")
//        weather = obj3
//        println JsonOutput.prettyPrint(JsonOutput.toJson(weather))


    }

}
