import io.restassured.path.json.JsonPath;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

//@ClassNameExt(ClassNameProvided = "BackOfficeCron-EmiReportsConsolidatedCronProcessorTest")
public class TestCase_TransferAmount extends CreateUser{

    public JsonPath receiver = CreateUser();
    public JsonPath sender = CreateUser();

    @Test(description = "Verify Transaction between sender and reciver")
    public void TC_001(){
        String receiver_ac_no = null;
        SeleniumScript ss = new SeleniumScript();
        WebDriver driver = null;
        Boolean txnStatus = null;
        
        try{
            // Sign up using receiver user details
            driver=ss.signUp(receiver);

            // get account number of receiver
            receiver_ac_no = ss.copyText("//*[@id=\"leftPanel\"]/ul/li[2]/a", driver);

            // print Account number
            System.out.println("Account Number: "+receiver_ac_no);

            // close browser
            driver.quit();

            // sign up using sender user details
            driver=ss.signUp(sender);

            // verifying txn based on boolean value
            txnStatus = ss.transferAmount(sender, receiver, receiver_ac_no, driver);

            // print txn status
            System.out.println("Status :"+txnStatus);
            
        }
        catch ( Exception e){
            System.out.println(e);
        }
        finally {
            // close browser
            driver.quit();

            // Assertion for txn
            Assert.assertTrue(txnStatus);
        }

    }

}
