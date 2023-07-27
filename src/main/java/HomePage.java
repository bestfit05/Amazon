import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class HomePage {

    WebDriver driver;

    @FindBy(xpath = "//*[@id=\"twotabsearchtextbox\"]")
    private WebElement searchBar;

    @FindBy(xpath = "//*[@id=\"nav-search-submit-button\"]")
    private WebElement searchBtn;

    public HomePage(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver,this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    /**
     * Search Product in search bar
     * @param seachKeyword
     * @return
     */
    public ProductListingPage search(String seachKeyword)
    {
        searchBar.clear();
        searchBar.sendKeys(seachKeyword);
        searchBtn.click();
        return new ProductListingPage(driver);
    }
}
