package au.com.racv.automation.portal.slf.pages;

import org.openqa.selenium.By;

import au.com.racv.automation.core.BasePage;
import au.com.racv.automation.portal.slf.datahandler.SlfDataHandler;
import au.com.racv.automation.portal.slf.models.RacvMemberDetails;
import au.com.racv.automation.utils.DataStore;

public class SlfPage extends BasePage {

    public final String slfPageDynamicDropdownXpath = "//*[contains(text(),'%s')]/parent::div//select";
    public final String selctDropdownOptionXpath = "//div//select//option[contains(@value,'%s')]";
    public final String filedValueDynamicXpath = "//div//input[@name='%s']";
    public final String racvMemberOrNot = "//label//input[@type='radio' and @value='%s']";
    public By getMyRateSubmitButton = By.xpath("//button[contains(text(),'Get my rate')]");
    public By selectHomeAddress = By.xpath("//ul//li[@class='qas-item'][1]");
    public By afterSubmissionSpinnerXpath = By.xpath("//lightning-spinner[contains(@class,'lightning-spinner')]");
    public By interestRateXpath = By.xpath("//div[@class='interest-section']/following-sibling::div//p");
    public By interestHeadingXpath = By.xpath("//div[@class='interest-heading']");
    public By refNumberXpath = By.xpath("//div[@class='reference-section']//p[@class='refno-section']");

    public void openSlfPage() {
        getDriver().get(properties.getProperty("racvSlfUrl"));
    }

    public void selctFromSlfPageDropdown(String optionName, String dropDownName) {
        By dropDownXpath = By.xpath(String.format(slfPageDynamicDropdownXpath, dropDownName));
        By dropDownOptionXpath = By.xpath(String.format(selctDropdownOptionXpath, optionName));
        waitForElementToBeClickable(dropDownXpath);
        click(dropDownXpath);
        scrollDownIntoView(dropDownOptionXpath);
        click(dropDownOptionXpath);
    }

    public void enterFieldValue(String fieldValue, String fieldName) {

        String camelCasedFieldName = convertToCamelCase(fieldName);
        By fieldXpath = By.xpath(String.format(filedValueDynamicXpath, camelCasedFieldName));
        waitForElementToBeVisible(fieldXpath, 4);
        enterTextField(fieldXpath, fieldValue);
        if (fieldName.equals("dob")) {
            String[] parts = fieldValue.split("/");
            enterTextField(fieldXpath, parts[0]);
            enterTextField(fieldXpath, parts[1]);
            enterTextField(fieldXpath, parts[2]);
        }
        if (fieldName.equals("Home Address")) {
            enterTextField(fieldXpath, fieldValue);
            waitForElementToBeVisible(selectHomeAddress, 1);
            wait(2000);
            click(selectHomeAddress);
        }
    }

    // public void addMemberDetails(String userId) {

    // RacvMemberDetails RacvMemberDetails =
    // SlfDataHandler.getMEmberDetailsByNumber(userId);
    // selctFromSlfPageDropdown(RacvMemberDetails.title,"Title");
    // enterFieldValue(RacvMemberDetails.firstName, "First Name");
    // enterFieldValue(RacvMemberDetails.lastName, "Last Name");
    // enterFieldValue(RacvMemberDetails.dateOfBirth, "dob");
    // enterFieldValue(RacvMemberDetails.homeAddress, "Home Address");
    // selctFromSlfPageDropdown(RacvMemberDetails.residentialStatus,"Residential
    // status");
    // enterFieldValue(RacvMemberDetails.membershipNumber, "Membership number");
    // selctFromSlfPageDropdown(RacvMemberDetails.membershipCardColor,"Membership
    // card colour");
    // selctFromSlfPageDropdown(RacvMemberDetails.gender,"Gender");
    // enterFieldValue(RacvMemberDetails.emailAddress, "email");
    // enterFieldValue(RacvMemberDetails.mobile, "Mobile");
    // enterFieldValue(RacvMemberDetails.licenceNumber, "Licence number");
    // selctFromSlfPageDropdown(RacvMemberDetails.licenceState,"State of issue");
    // }

    // public void addEquifaxPositiveUserDetails(String userId) {

    // RacvMemberDetails RacvMemberDetails =
    // SlfDataHandler.getEquifaxPositiveUserDetailsByNumber(userId);
    // selctFromSlfPageDropdown(RacvMemberDetails.title,"Title");
    // enterFieldValue(RacvMemberDetails.firstName, "First Name");
    // enterFieldValue(RacvMemberDetails.lastName, "Last Name");
    // enterFieldValue(RacvMemberDetails.dateOfBirth, "dob");
    // enterFieldValue(RacvMemberDetails.homeAddress, "Home Address");
    // selctFromSlfPageDropdown(RacvMemberDetails.residentialStatus,"Residential
    // status");
    // // enterFieldValue(RacvMemberDetails.membershipNumber, "Membership number");
    // // selctFromSlfPageDropdown(RacvMemberDetails.membershipCardColor,"Membership
    // card colour");
    // selctFromSlfPageDropdown(RacvMemberDetails.gender,"Gender");
    // enterFieldValue(RacvMemberDetails.emailAddress, "email");
    // enterFieldValue(RacvMemberDetails.mobile, "Mobile");
    // enterFieldValue(RacvMemberDetails.licenceNumber, "Licence number");
    // selctFromSlfPageDropdown(RacvMemberDetails.licenceState,"State of issue");
    // }

    public void addUserDetails(String userId, String userType) {

        RacvMemberDetails details = SlfDataHandler.getUserDetailsByNumber(userId, userType);

        // selctFromSlfPageDropdown(details.title, "Title");
        // enterFieldValue(details.firstName, "First Name");
        // enterFieldValue(details.lastName, "Last Name");
        // enterFieldValue(details.dateOfBirth, "dob");
        // enterFieldValue(details.homeAddress, "Home Address");
        // selctFromSlfPageDropdown(details.residentialStatus, "Residential status");
        // selctFromSlfPageDropdown(details.gender, "Gender");
        // enterFieldValue(details.emailAddress, "email");
        // enterFieldValue(details.mobile, "Mobile");
        // enterFieldValue(details.licenceNumber, "Licence number");
        // selctFromSlfPageDropdown(details.licenceState, "State of issue");

        // only for Member
        if ("Member".equalsIgnoreCase(userType)) {
            selctFromSlfPageDropdown(details.title, "Title");
            enterFieldValue(details.firstName, "First Name");
            enterFieldValue(details.lastName, "Last Name");
            enterFieldValue(details.dateOfBirth, "dob");
            enterFieldValue(details.homeAddress, "Home Address");
            selctFromSlfPageDropdown(details.residentialStatus, "Residential status");
            selctFromSlfPageDropdown(details.gender, "Gender");
            enterFieldValue(details.emailAddress, "email");
            enterFieldValue(details.mobile, "Mobile");
            enterFieldValue(details.licenceNumber, "Licence number");
            selctFromSlfPageDropdown(details.licenceState, "State of issue");
    
            enterFieldValue(details.membershipNumber, "Membership number");
            selctFromSlfPageDropdown(details.membershipCardColor, "Membership card colour");
        }

        if("Equifax Positive User".equalsIgnoreCase(userType)){
            selctFromSlfPageDropdown(details.title, "Title");
            enterFieldValue(details.firstName, "First Name");
            enterFieldValue(details.lastName, "Last Name");
            enterFieldValue(details.dateOfBirth, "dob");
            enterFieldValue(details.homeAddress, "Home Address");
            selctFromSlfPageDropdown(details.residentialStatus, "Residential status");
            selctFromSlfPageDropdown(details.gender, "Gender");
            enterFieldValue(details.emailAddress, "email");
            enterFieldValue(details.mobile, "Mobile");
            enterFieldValue(details.licenceNumber, "Licence number");
            selctFromSlfPageDropdown(details.licenceState, "State of issue");
        }
    }

    public void chooseIfRacvMemberUserOrNot(String choice) {
        By fieldXpath = By.xpath(String.format(racvMemberOrNot, choice));
        scrollDownIntoView(fieldXpath);
        click(fieldXpath);
    }

    public void submitGetMyRateFormOnSlfPage() {
        scrollDownIntoView(getMyRateSubmitButton);
        click(getMyRateSubmitButton);
        // click(getMyRateSubmitButton);
        waitForElementToBeInVisible(afterSubmissionSpinnerXpath, 30);
        // waitForElementToBeVisible(interestHeadingXpath, 2);
        // waitForElementToBeVisible(interestRateXpath, 2);
        String interestRate = getTextFromElement(interestRateXpath).replace("%", "");
        DataStore.storeValue("Estimated Interest Rate", interestRate);
        String refNo = getTextFromElement(refNumberXpath);
        DataStore.storeValue("Lead Reference Number", refNo);
    }
}
