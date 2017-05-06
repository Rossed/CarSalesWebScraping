
import groovy.json.*
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.JavascriptExecutor

import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

class CarFinder {

	def static allCars = []
    def static urlMain = ""

    static void main(String[] args) throws IOException {

        urlMain = "https://www.carsales.com.au/cars/results?sortby=TopDeal&limit=12&q=%28And.Service.Carsales._.State.ACT._.GenericGearType.Automatic._.Price.range%28..10100%29.%29"

        while (true) {
            Document doc

            boolean keepTrying = true
            while (keepTrying) {
                try {
                    doc = Jsoup.connect(urlMain).get()
                    keepTrying = false
                } catch (Exception e) {

                }
            }


            Elements cars = doc.select(".listing-item")
            Elements carTitle = cars.select(".n_width-max h2")
            Elements carPrice = cars.select("div.price")

            int index = 0
            for (Element h2 : carTitle) {

                def car = h2.text()
                if (car.contains("Manufacturer Marketing Year (MY) The manufacturer's marketing year of this model.")) {
                    car = car.replace("Manufacturer Marketing Year (MY) The manufacturer's marketing year of this model.", "")
                }
                def price = carPrice.get(index).text()

                if (car.contains(" MY")) {
                    car = car.reverse().drop(5).reverse()
                }

                def details = car.split()

                def year = details[0]
                def make = details[1]
                def model = details[2]
                def transmission = "Automatic"

                String display = String.format("year: %s, make: %s, model: %s, transmission, %s", year, make, model, transmission)
                String searchString = "https://www.carsales.com.au/car-valuations/refine/" + make + "/" + model + "/" + year + "/" + transmission

                WebDriver driver = new ChromeDriver()

                //  And now use this to visit Google
                price = NumberFormat.getNumberInstance(java.util.Locale.US).parse(price.substring(1, price.length() - 1))
                driver.get(searchString)

                JavascriptExecutor jse = (JavascriptExecutor) driver
                try {
                    jse.executeScript("document.getElementsByClassName(\"csn-btn csn-secondary csn-solid csn-small\")[0].click()")
                    String url = driver.getCurrentUrl()
                    url = url.reverse().drop(5).reverse() + "/buy"
                    driver.get(url)
                    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

                    try {
                        List<WebElement> price1 = driver.findElementsByCssSelector("span.price-max")
                        List<WebElement> price2 = driver.findElementsByCssSelector("span.price-min.ng-binding")

                        String priceOne = price1[1].findElement(By.xpath(".//*")).getText()
                        String priceTwo = price2[1].getText()

                        def lowerBoundPrice = NumberFormat.getNumberInstance(java.util.Locale.US).parse(priceOne.substring(1, priceOne.length() - 1))
                        def upperBoundPrice = NumberFormat.getNumberInstance(java.util.Locale.US).parse(priceTwo.substring(1, priceOne.length() - 1))
                        if (price <= lowerBoundPrice) {
                            allCars << new Car((year + " " + make + " " + model), price, lowerBoundPrice, upperBoundPrice, Choice.GOOD, urlMain, searchString)
                        } else if (price <= upperBoundPrice) {
                            allCars << new Car((year + " " + " " + make + " " + model), price, lowerBoundPrice, upperBoundPrice, Choice.MEDIUM, urlMain, searchString)
                        } else {
                            allCars << new Car((year + " " + make + " " + model), price, lowerBoundPrice, upperBoundPrice, Choice.BAD, urlMain, searchString)
                        }

                        driver.close()
                        driver.quit()

                    } catch (Exception e2) {
                        driver.close()
                        driver.quit()
                    }
                } catch (WebDriverException e) {
                    driver.close()
                    driver.quit()
                }

                index++
            }

            def test = []
            for (int i = 0; i < 3 ; i++) {
                for (def car in allCars) {
                    if ( i == 2 && car.choice == Choice.BAD) {
                        test << car
                    } else if (i==1 && car.choice == Choice.MEDIUM) {
                        test << car
                    } else if (i==0 && car.choice == Choice.GOOD) {
                        test << car
                    }
                }
            }

            String json = JsonOutput.prettyPrint(JsonOutput.toJson(test))
            File f = new File("src/main/resources/car.json")
            f.write(json)

            WebDriver driver = new ChromeDriver()
            driver.get(urlMain)
            driver.executeScript("document.querySelectorAll('[title=Next]')[0].click()")
            urlMain = driver.getCurrentUrl()

            driver.close()
            driver.quit()

        }

    }

}