import jdk.javadoc.doclet.Reporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProductListingPage {

    WebDriver driver;

    @FindBy(xpath = "//*[@id=\"search\"]/span[2]/div/h1/div/div[1]/div/div/span[1]")
    WebElement serachResultCount;

    @FindBy(xpath = "//*[@id=\"search\"]/div[1]/div[1]/div/span[1]/div[1]/div[3]/div")
    WebElement productListing;

    public ProductListingPage(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver,this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    /**
     * Fetch count of products present in First Page
     * @return String
     */
    public String getCountOftotalProductListedInFirstPage()
    {
        String totalCount = serachResultCount.getText();
        return totalCount.substring(totalCount.indexOf('-')+1,totalCount.indexOf('-')+3);
    }

    /**
     * List of product and its price on first page
     * @param totalCount
     * @return
     */
    public List<List<String>> getProductNameAndPrice(Integer totalCount)
    {
        List<List<String>> productNameAndPriceList = new ArrayList<>();

        for(int i = 3 ; i<=(totalCount+3) ; i++ )
        {
            String dynamicXpath = "//*[@id=\"search\"]/div[1]/div[1]/div/span[1]/div[1]/div["+i+"]/div";
            String productDescription;
            try {
                productDescription = driver.findElement(By.xpath(dynamicXpath)).getText();
            }
            catch (Exception exception)
            {
                System.out.println("No More Products avialable on first Page");
                break;
            }
            if(productDescription.contains("New Price") || productDescription.contains("M.R.P"))
            {
                List productNameAndPrice = new ArrayList();
                int productNameEndIndex = productDescription.indexOf("₹");
                String productName = productDescription.substring(0, productNameEndIndex).trim();
                int priceStartIndex = productDescription.indexOf("₹", productNameEndIndex)+1;
                int priceEndIndex = productDescription.indexOf(" ", priceStartIndex);
                String productPrice = productDescription.substring(priceStartIndex, priceEndIndex).trim();
                productNameAndPrice.add(productName);
                productNameAndPrice.add(productPrice);
                productNameAndPriceList.add(productNameAndPrice);
            }
            else {
                List productNameAndPrice = new ArrayList();
                productNameAndPrice.add(productDescription);
                productNameAndPrice.add("0");
                productNameAndPriceList.add(productNameAndPrice);
            }
        }
        return productNameAndPriceList;
    }
}
