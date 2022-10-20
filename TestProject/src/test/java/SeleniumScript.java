import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;


public class SeleniumScript {

    @BeforeMethod
    public WebDriver createDriver(){
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        WebDriver driver = new ChromeDriver();
        return driver;
    }



    /**
     * @param response
     * @return WebDriver
     * @desc THis function is used to signup new user
     */
    public WebDriver signUp(JsonPath response){
         String ac_no = null;
         WebDriver driver = createDriver();
       try {
       driver.get("https://parabank.parasoft.com/parabank/register.htm");
       Thread.sleep(5000);
       driver.findElement(By.xpath("//*[@id=\"customer.firstName\"]")).sendKeys(response.getString("results.name.first").substring(1,response.getString("results.name.first").length()-1));
       driver.findElement(By.xpath("//*[@id=\"customer.lastName\"]")).sendKeys(response.getString("results.name.last").substring(1,response.getString("results.name.last").length()-1));
       driver.findElement(By.xpath("//*[@id=\"customer.address.street\"]")).sendKeys(response.getString("results.location.street.number").substring(1,response.getString("results.location.street.number").length()-1) +" "+ response.getString("results.location.street.name").substring(1,response.getString("results.location.street.name").length()-1));
       driver.findElement(By.xpath("//*[@id=\"customer.address.city\"]")).sendKeys(response.getString("results.location.city").substring(1,response.getString("results.location.city").length()-1));
       driver.findElement(By.xpath("//*[@id=\"customer.address.state\"]")).sendKeys(response.getString("results.location.state").substring(1,response.getString("results.location.state").length()-1));
       driver.findElement(By.xpath("//*[@id=\"customer.address.zipCode\"]")).sendKeys(response.getString("results.location.postcode").substring(1,response.getString("results.location.postcode").length()-1));
       driver.findElement(By.xpath("//*[@id=\"customer.phoneNumber\"]")).sendKeys(response.getString("results.phone").substring(1,response.getString("results.phone").length()-1));
       driver.findElement(By.xpath("//*[@id=\"customer.ssn\"]")).sendKeys(response.getString("results.cell").substring(1,response.getString("results.cell").length()-1));
       driver.findElement(By.xpath("//*[@id=\"customer.username\"]")).sendKeys(response.getString("results.login.username").substring(1,response.getString("results.login.username").length()-1));
       driver.findElement(By.xpath("//*[@id=\"customer.password\"]")).sendKeys(response.getString("results.login.password").substring(1,response.getString("results.login.password").length()-1));
       driver.findElement(By.xpath("//*[@id=\"repeatedPassword\"]")).sendKeys(response.getString("results.login.password").substring(1,response.getString("results.login.password").length()-1));
       driver.findElement(By.xpath("//*[@id=\"customerForm\"]/table/tbody/tr[13]/td[2]/input")).click();
       Thread.sleep(2000);
       if(driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/p")).getText().equalsIgnoreCase("Your account was created successfully. You are now logged in.")){
        System.out.println("Account created");
       }
       else if(driver.findElement(By.xpath("//*[@id=\"customer.username.errors\"]")).getText().contains("This username already exists.")){
           Random rd = new Random();
           driver.findElement(By.xpath("//*[@id=\"customer.username\"]")).sendKeys(Integer.toString(rd.nextInt()));/* response.getString("results.login.username").substring(1,response.getString("results.login.username").length()-1) + */
           driver.findElement(By.xpath("//*[@id=\"customer.password\"]")).sendKeys(response.getString("results.login.password").substring(1,response.getString("results.login.password").length()-1));
           driver.findElement(By.xpath("//*[@id=\"repeatedPassword\"]")).sendKeys(response.getString("results.login.password").substring(1,response.getString("results.login.password").length()-1));
           driver.findElement(By.xpath("//*[@id=\"customerForm\"]/table/tbody/tr[13]/td[2]/input")).click();
       }
           Thread.sleep(3000);
            String message= driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/p")).getText();
           System.out.println(message);
           Assert.assertEquals( message, "Your account was created successfully. You are now logged in.");

       }
   catch ( Exception e){
       System.out.println(e);
   }
       return driver;
     }

    /**
     * @param path
     * @param driver
     * @return String
     * @desc THis function is used to copy account number of receiver
     */
     public String copyText(String path, WebDriver driver){

         try {
             if(checkStatus(driver, path)) {
                 driver.findElement(By.xpath(path)).click();
             }
             else {
                 System.out.println("Getting status code other than 200");
             }
             Thread.sleep(2000);
     }
         catch (Exception e){
             System.out.println(e);
         }
         return driver.findElement(By.xpath("//*[@id=\"accountTable\"]/tbody/tr[1]/td[1]")).getText();
     }

    /**
     * @param sender
     * @param reciver
     * @param acNo
     * @param driver
     * @return boolean
     * @desc THis function is used to transfer amount from sender account to receiver account
     */
     public Boolean transferAmount(JsonPath sender, JsonPath reciver, String acNo, WebDriver driver){
         try {
             checkStatus(driver, "//*[@id=\"leftPanel\"]/ul/li[4]/a");
             driver.findElement(By.xpath("//*[@id=\"leftPanel\"]/ul/li[4]/a")).click();
             Thread.sleep(5000);
             driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/div/div[1]/form/table/tbody/tr[1]/td[2]/input")).sendKeys(reciver.getString("results.name.first").substring(1,reciver.getString("results.name.first").length()-1));
             driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/div/div[1]/form/table/tbody/tr[2]/td[2]/input")).sendKeys(reciver.getString("results.location.street.number").substring(1,reciver.getString("results.location.street.number").length()-1) +" "+ reciver.getString("results.location.street.name").substring(1,reciver.getString("results.location.street.name").length()-1));
             driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/div/div[1]/form/table/tbody/tr[3]/td[2]/input")).sendKeys(reciver.getString("results.location.city").substring(1,reciver.getString("results.location.city").length()-1));
             driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/div/div[1]/form/table/tbody/tr[4]/td[2]/input")).sendKeys(reciver.getString("results.location.state").substring(1,reciver.getString("results.location.state").length()-1));
             driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/div/div[1]/form/table/tbody/tr[5]/td[2]/input")).sendKeys(reciver.getString("results.location.postcode").substring(1,reciver.getString("results.location.postcode").length()-1));
             driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/div/div[1]/form/table/tbody/tr[6]/td[2]/input")).sendKeys(reciver.getString("results.phone").substring(1,reciver.getString("results.phone").length()-1));

             driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/div/div[1]/form/table/tbody/tr[8]/td[2]/input")).sendKeys(acNo);
             driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/div/div[1]/form/table/tbody/tr[9]/td[2]/input")).sendKeys(acNo);
             driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/div/div[1]/form/table/tbody/tr[11]/td[2]/input")).sendKeys("99");

             driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/div/div[1]/form/table/tbody/tr[14]/td[2]/input")).click();
         }
         catch (Exception e){
             System.out.println(e);
         }
         String status = driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/div/div[2]/p[1]")).getText();
         System.out.println("Status: "+status);
          if(status.contains("successful")){
              return true;
          }
          else {
              return false;
          }
     }

     public Boolean checkStatus(WebDriver driver, String path){
         Boolean statusCode = null;
         try{

             HttpURLConnection c= (HttpURLConnection)new URL(driver.findElement(By.xpath(path)).getAttribute("href")).openConnection();

             // set the HEAD request with setRequestMethod
             c.setRequestMethod("HEAD");

             // connection started and get response code
             c.connect();
             int r = c.getResponseCode();
             System.out.println("Http response code: " + r);
             if(r==200){
                 statusCode = true;
             }
             else
                 statusCode= false;

         } catch (Exception e) {
             e.printStackTrace();
         }
         return statusCode;
     }
}
