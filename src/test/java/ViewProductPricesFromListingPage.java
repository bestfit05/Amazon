import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Duration;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ViewProductPricesFromListingPage {



   public WebDriver driver;
   public String baseUrl;

   @BeforeMethod
    public void setup()
   {
       driver = new ChromeDriver();
       baseUrl = "https://www.amazon.in/";
       driver.manage().window().maximize();
       driver.manage().timeouts().implicitlyWait(Duration.ofDays(10));
   }

   @Test
    public void listProductPrice()
   {
       driver.get(baseUrl);
       HomePage homePage = new HomePage(driver);
       ProductListingPage productListingPage = homePage.search("lg soundbar");
       String countOfProductListed = productListingPage.getCountOftotalProductListedInFirstPage();

       List<List<String>> allProductNameAndPriceList = productListingPage.getProductNameAndPrice(Integer.valueOf(countOfProductListed));
       // custom sort on price
       Collections.sort(allProductNameAndPriceList, new Comparator<List<String>>() {
           @Override
           public int compare(List<String> list1, List<String> list2) {
               int price1 = parsePrice(list1.get(1));
               int price2 = parsePrice(list2.get(1));
               return price1 - price2; // asc
           }
       });
       System.out.println("----------------------------------------");
       for(List<String> list: allProductNameAndPriceList)
       {
           //printing first 30 character of product name as product is too large to view and price
           System.out.println(list.get(0).substring(0,Math.min(list.get(0).length(),30)) +" : "+ list.get(1));
       }
       System.out.println("----------------------------------------");

   }

    /**
     * formatting price to value
     * @param priceStr
     * @return
     */
    private static int parsePrice(String priceStr) {
        try {
            NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
            Number number = format.parse(priceStr);
            return number.intValue();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

   @AfterMethod
    public void tearDown()
   {
       driver.close();
   }
}
