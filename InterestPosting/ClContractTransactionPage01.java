package au.com.racv.automation.portal.cloudlending.pages.clcontracts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import au.com.racv.automation.utils.DataStore;
import au.com.racv.automation.utils.Log;
import org.junit.Assert;

public class ClContractTransactionPage extends ClContractBasePage {
    public final String dynamicPaymentTransactionsEntriesXpath = "//h3[text() = 'Loan Payment Transactions']/ancestor::div[@class='pbHeader']/following-sibling::div//tr[@class='headerRow']/following-sibling::tr[%s]";
    public final String dynamicPaymentTransactionsEntriesIdXpath = "//h3[text() = 'Loan Payment Transactions']/ancestor::div[@class='pbHeader']/following-sibling::div//tr[@class='headerRow']/following-sibling::tr[%s]/th/a";
    public final String dynamicInterestPostingTransactionsEntriesIdXpath = "//h3[text() = 'Interest Posting Transactions']/ancestor::div[@class='pbHeader']/following-sibling::div//tr[@class='headerRow']/following-sibling::tr[%s]/th/a";
    public final String dynamicFeePaymentIdXpath = "(//a[.//span[starts-with(text(),'FPID-')]])[%s]";
    public final String dynamicBillsEntriesIdXpath = "//h3[text() = 'Bills']/ancestor::div[@class='pbHeader']/following-sibling::div//tr[@class='headerRow']/following-sibling::tr[%s]/th/a";
    public final String dynamicgetFieldValueFromLPTDetailsTabXpath = "(//records-record-layout-item[@field-label='%s']//slot[@name='outputField']/*)[last()]";
    public final String dynamicOtherTransactionsEntriesXpath = "//h3[text() = 'Other Loan Transactions']/ancestor::div[@class='pbHeader']/following-sibling::div//tr[@class='headerRow']/following-sibling::tr[%s]";
    public final String dynamicOtherTransactionsEntriesIdXpath = "//h3[text() = 'Other Loan Transactions']/ancestor::div[@class='pbHeader']/following-sibling::div//tr[@class='headerRow']/following-sibling::tr[%s]/th/a";
    public final String dynamicOtherTransactionReversalOptionXpath = "//span[text() = '%s Reversal']/parent::a";
    public final String uploadBankFileUrl = "lightning/n/Upload_Bank_File";
    public final String dynamicAccountingEntryLink= "//th[@data-label='Accounting Entry']//a[%d]";

    public final By newLoanPaymentTransactionButton = By.xpath("//input[@value='New Loan Payment Transaction']");
    public final By paymentModeInput = By.xpath("(//label[text()='Payment Mode']/ancestor::tr//input)[last()]");
    public final By transactionAmountInput = By.xpath("//label[text()='Transaction Amount']/ancestor::tr//input");
    public final By transactionDateInput = By.xpath("(//label[text()='Transaction Date']/ancestor::tr//input)[last()]");
    public final By validateButton = By.xpath("(//input[@value='Validate'])[last()]");
    public final By successMessage = By.xpath("//h4[text()='Success:']");
    public final By saveButton = By.xpath("//input[@value='Save']");
    public final By datePickerButton = By.xpath("//input[contains(@onfocus,'reversePayment')]/following-sibling::span[@class='dateFormat']/a");
    public final By cancelButton = By.xpath("//input[@value='Cancel']");
    public final By paymentTransactionIframe = By.xpath("//iframe[@title='Record A New Payment']");
    public final By loanPayOffButton = By.xpath("//input[@value='Loan Pay Off']");
    public final By recordNewPaymentIframe = By.xpath("//iframe[@title='Record A New Payment']");
    public final By recordPaymentHeader = By.xpath("//h1[text()='Record a payment']");
    public final By recordAPayOffIframe = By.xpath("//iframe[@title='Record A Pay-Off']");
    public final By recordAPayOffHeader = By.xpath("//h1[text()='Record a Pay-Off']");
    public final By paymentModeText = By.xpath(
            "(//th[text()='Payment Mode']/../..//tr)[2]//td/a[not(contains(text(),'Edit'))and not(contains(text(),'Del'))]");
    public final By transactionDateText = By
            .xpath("(//th[text()='Payment Mode']/../..//tr)[2]//td[contains(@class,'Date')]");
    public final By loanPaymentTransactionIdText = By
            .xpath("(//th[text()='Payment Mode']/../..//tr)[2]//th//a[contains(text(),'LPT-')]");
    public final By transactionAmountText = By.xpath("((//th[text()='Payment Mode']/../..//tr)[2]//td)[5]");
    public final By relatedButton = By.xpath(
            "//records-entity-label[text()='Loan Payment Transaction' or text()='Repayment Transaction Reversal']/ancestor::div[contains(@class,'region-header')]/following-sibling::div//a[text()='Related']");  
    public final By relatedButtonInterestPosting = By.xpath(
            "//records-entity-label[text()='Interest Posting Transaction' or text()='Interest Posting Transaction Reversal']/ancestor::div[contains(@class,'region-header')]/following-sibling::div//a[text()='Related']");   
    public final By repaymentTransactionReversalHeader = By
            .xpath("//h2//span[text()='Repayment Transaction Reversal']");
    public final By accountEntriesHeader = By
            .xpath("//h2//span[text()='Repayment Transaction Reversal' or text()='Accounting Entries']");
    public final By repaymentTransactionReversalNewButton = By
            .xpath("//article[@aria-label='Repayment Transaction Reversal']//button[text()='New']");
    public final By reverseAPaymentIframe = By.xpath("//iframe[@title='Reverse a payment']");
    public final By reverseAPaymentHeader = By.xpath("//h1[text()='Reverse A Payment']");
    public final By reasonCodeListbox = By.xpath("//select[contains(@id,'reversePayment')]");
    public final By showMoreOptionButton = By.xpath("//span[text() = 'Show more actions']/parent::button");
    public final By confirmButton = By.xpath("//input[@value = 'Confirm']");
    public final By accountingEntryLink = By.xpath("//th[@data-label='Accounting Entry']//a");
    public final By repaymentTractionReversalEntryLink = By
            .xpath("//th[@data-label='Repayment Transaction Adjustment ID']//a");
    public final By fileType = By.xpath("//select");
    public final By bankUploadFilePath = By.xpath("//input[@type='file']");
    public final By uploafFileButton = By.xpath("//div[@class='pbBottomButtons']//input[@value='Upload']");
    public final By successMessageText = By.xpath("//div[@class='messageText']");
    public final By sendToAcheEditButton = By
            .xpath("(//th[text()='Sent to ACH']/..//span[contains(@id,'achId')])[last()]");
    public final By sendToAcheInputTextField = By.xpath("//th[text()='Sent to ACH']/..//input");

    public final By sentToAchOnEditButton = By
            .xpath("(//span[text()='Sent To ACH On']/../..//span[contains(@id,'achId')])[last()]");
    public final By sentToAchOnInputTextField = By.xpath("//span[text()='Sent To ACH On']/../..//input");

    public final By achFilenameEditButton = By
            .xpath("(//th[text()='ACH Filename']/..//span[contains(@id,'achId')])[last()]");
    public final By achFilenameInputTextField = By.xpath("//th[text()='ACH Filename']/..//input");
    public final By writeOffRecoveryCheckbox = By.xpath("//label[text()='Write off Recovery']/../..//input[@type='checkbox']");
    public final By chargeRecordButton= By.xpath("//a[starts-with(text(),'CHG-')]");
    public final By relatedTabButtonOnChargeField= By.xpath("//records-entity-label[text()='Charge']/ancestor::div[contains(@class,'region-header')]/following-sibling::div//a[text()='Related']");
    public final By relatedTabButtonOnFeepaymentPage= By.xpath("//records-entity-label[text()='Fee Payment']/ancestor::div[contains(@class,'region-header')]/following-sibling::div//a[text()='Related']");
    public final By accountingEntryButton= By.xpath("//span[@class='slds-truncate']//span[contains(text(),'AE-')]"); 
 

    public void createLoanPaymentTransaction(String paymentMode, String transactionAmount, boolean withAch, boolean onRecoveryLoan) {
        click(newLoanPaymentTransactionButton);
        switchToDefaultContent();
        switchToFrame(paymentTransactionIframe);
        
        if (withAch) {
            doubleClickUsingJavaScript(sendToAcheEditButton);
            setCheckbox(sendToAcheInputTextField, true);
            doubleClickUsingJavaScript(sentToAchOnEditButton);
            enterTextField(sentToAchOnInputTextField, "10/12/2000");
            doubleClickUsingJavaScript(achFilenameEditButton);
            enterTextField(achFilenameInputTextField, "AutomationFile");
            doubleClickUsingJavaScript(achFilenameEditButton);
        }

        if (onRecoveryLoan) {
            click(writeOffRecoveryCheckbox);
        }
        
        enterTextField(paymentModeInput, paymentMode);
        wait(5000);
        waitForElementAttributeToHaveText(paymentModeInput, "value", paymentMode, 10);
        enterTextField(transactionAmountInput, transactionAmount);


        click(saveButton);
        waitForElementToBeInVisible(cancelButton, 30);
        switchToDefaultContent();
    }

    public void createNewLptWithPaymentDateAmountAndMethod(String paymentDate, String paymentAmount,
            String paymentMethod) {
        click(newLoanPaymentTransactionButton);
        switchToDefaultContent();
        switchToFrame(recordNewPaymentIframe);
        waitForElementToBeVisible(recordPaymentHeader, 20);

        // Enter Transaction Date
        if ("System".equals(paymentDate)) {
            // System date is used by default
            Log.info("Using System date as Transaction Date");
            DataStore.storeValue("Transaction Date", DataStore.getStoredValue("System Date"));
        } else {
            enterTextField(transactionDateInput, paymentDate);
            wait(2000);
            DataStore.storeValue("Transaction Date", paymentDate);
        }

        // Enter Final Payoff Amount
        if ("Saved".equals(paymentAmount)) {
            if ((String) DataStore.getStoredValue("Final Payoff Amount") != null) {
                enterTextField(transactionAmountInput, (String) (DataStore.getStoredValue("Final Payoff Amount")));
                DataStore.storeValue("Transaction Amount",
                        (String) (DataStore.getStoredValue("Final Payoff Amount")));

            } else {
                enterTextField(transactionAmountInput, (String) (DataStore.getStoredValue("Today's Payoff (approx.)")));
                DataStore.storeValue("Transaction Amount",
                        (String) (DataStore.getStoredValue("Today's Payoff (approx.)")));
            }
        }

        else if (paymentAmount.startsWith("Saved -") || paymentAmount.startsWith("Saved +")) {
            double impactedAmount = Double.parseDouble(paymentAmount.replaceAll("[^0-9]", ""));
            double originalAmount = Double.parseDouble(((String) DataStore.getStoredValue("Final Payoff Amount") != null
                    ? (String) DataStore.getStoredValue("Final Payoff Amount")
                    : (String) DataStore.getStoredValue("Today's Payoff (approx.)")).replace(",", ""));

            double transactionAmountValue = paymentAmount.startsWith("Saved -") ? originalAmount - impactedAmount
                    : originalAmount + impactedAmount;
            String transactionAmount = String.valueOf(transactionAmountValue);
            enterTextField(transactionAmountInput, transactionAmount);
            DataStore.storeValue("Transaction Amount", transactionAmount);
            DataStore.storeValue("Reduced Buffer Amount", String.valueOf(impactedAmount));
        } else {
            enterTextField(transactionAmountInput, paymentAmount);
            DataStore.storeValue("Transaction Amount", paymentAmount);
        }
        // Enter Payment Method
        enterTextField(paymentModeInput, paymentMethod);
        int iterations = 6;
        while (true) {
            try {
                // after entering the payment method, the attributeValue appears but don't get
                // loaded instantly
                wait(10000);
                getAttributeValueFromElement(paymentModeInput, "value").equals(paymentMethod);
                break;
            } catch (AssertionError e) {
                if (--iterations == 0) {
                    Log.error(paymentMethod + " Payment Method is not updated");
                    throw e;
                }
            }
        }
        DataStore.storeValue("Payment Mode", paymentMethod);
        click(validateButton);
        waitForElementToBeVisible(successMessage, 10);
        click(saveButton);
        switchToDefaultContent();
    }

    public Map<String, String> getLoanTransactionDetails(int transactionIndex) {
        Map<String, String> transactionSummary = new HashMap<String, String>();
        String transactionRecordXpath = String.format(dynamicPaymentTransactionsEntriesXpath, transactionIndex);
        By loanPaymentTransactionId = By.xpath(transactionRecordXpath + "/th/a");
        By paymentType = By.xpath(transactionRecordXpath + "/td[2]");
        By paymentModes = By.xpath(transactionRecordXpath + "/td[3]");
        By transactionDate = By.xpath(transactionRecordXpath + "/td[4]");
        By transactionAmount = By.xpath(transactionRecordXpath + "/td[5]");

        transactionSummary.put("LOAN_PAYMENT_TRANSACTION_ID", getTextFromElement(loanPaymentTransactionId));
        transactionSummary.put("PAYMENT_TYPE", getTextFromElement(paymentType));
        transactionSummary.put("PAYMENT_MODE", getTextFromElement(paymentModes));
        transactionSummary.put("TRANSACTION_DATE", getTextFromElement(transactionDate));
        transactionSummary.put("TRANSACTION_AMOUNT", getTextFromElement(transactionAmount));
        return transactionSummary;
    }

    public void openLoanPaymentTransactionByIndex(int transactionIndex) {
        By loanPaymentTransactionId = By
                .xpath(String.format(dynamicPaymentTransactionsEntriesIdXpath, transactionIndex));
        click(loanPaymentTransactionId);
        wait(2000);
        refreshTheBrowser();
        waitForElementToBeVisible(relatedButton, 5);
    }

    public void openInterstPostingTransactionByIndex(int transactionIndex){
        By interestPostingTransactionId = By
        .xpath(String.format(dynamicInterestPostingTransactionsEntriesIdXpath, transactionIndex));
        click(interestPostingTransactionId);
        wait(2000);
        refreshTheBrowser();
        waitForElementToBeVisible(relatedButtonInterestPosting, 5);
    }

    public void openFeePaymentTransactionByIndex(int transactionIndex) {
        By feePaymentTransactionID = By
                .xpath(String.format(dynamicFeePaymentIdXpath, transactionIndex));
        clickUsingJavaScript(feePaymentTransactionID);
        wait(2000);
        refreshTheBrowser();
        waitForPageToFinishLoading();
        waitForElementToBeClickable(relatedTabButtonOnFeepaymentPage);
        click(relatedTabButtonOnFeepaymentPage);
        clickUsingJavaScript(accountingEntryButton);
        // waitForElementToBeVisible(relatedButton, 5);
    }

    public void openBillByIndex(int BillsIndex) {
        By BillId = By
                .xpath(String.format(dynamicBillsEntriesIdXpath, BillsIndex));
        click(BillId);
        wait(2000);
    }

    public String getFieldValueFromLPTDetailsTab(String fieldName) {
        String fieldValue;
        fieldValue = getTextFromElement(By.xpath(String.format(dynamicgetFieldValueFromLPTDetailsTabXpath, fieldName)));
        // Remove "Open <Text> Preview" if present in the element and only keep the
        // <Text>
        // This is a work around in case of Link elements with tag:
        // records-hoverable-link
        if (fieldValue.contains("Open") && fieldValue.contains("Preview")) {
            return fieldValue.split("Open")[1].split("Preview")[0].trim();
        } else {
            return fieldValue;
        }
    }

    public void reverseOtherPaymentTransactions(String transactionIndex, String transactionType) {
        click(By.xpath(String.format(dynamicOtherTransactionsEntriesIdXpath, transactionIndex)));
        switchToDefaultContent();
        click(showMoreOptionButton);
        click(By.xpath(String.format(dynamicOtherTransactionReversalOptionXpath, transactionType)));
        switchToFrame(accessibilityIframe);
        click(confirmButton);
        waitForElementToBeInVisible(confirmButton, 20);
    }

    public void createBankUploadFile() throws IOException, ParseException {
        // Collect all details for Lodgement Reference
        scrollIntoView(By.xpath(String.format(dynamicgetFieldValueFromLPTDetailsTabXpath, "Unique Key")));
        String[] uniqueKey = getTextFromElement(
                By.xpath(String.format(dynamicgetFieldValueFromLPTDetailsTabXpath, "Unique Key"))).split(" ");
        String contractNumber = uniqueKey[0];
        String lptLastDigit = uniqueKey[1];
        String transactionAmount = getTextFromElement(
                By.xpath(String.format(dynamicgetFieldValueFromLPTDetailsTabXpath, "Transaction Amount")))
                .replace(",", "").replace(".", "");
        transactionAmount = String.format("%010d", Integer.valueOf(transactionAmount));
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(DataStore.getStoredValue("System Date").toString());
        String formattedDate = new SimpleDateFormat("ddMMyy").format(date);

        // Create file for Lodgement Reference
        String filePath = "src/test/resources/LodgementReferenceFile/LodgementReference.txt";
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        StringBuilder sb = new StringBuilder(content);
        sb.replace(143, 153, contractNumber); // Contract Number
        sb.replace(154, 161, lptLastDigit); // LPT Last Digit
        sb.replace(74, 80, formattedDate); // Date
        sb.replace(101, 111, transactionAmount); // Amount
        String newFilePath = "src/test/resources/LodgementReferenceFile/GeneratedLodgementReference.txt";
        Files.write(Paths.get(newFilePath), sb.toString().getBytes(), StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
        Log.info("Bank upload file created successfully with the following details:");
        Log.info("Contract Number: " + contractNumber);
        Log.info("LPT Last Digit: " + lptLastDigit);
        Log.info("Transaction Amount: " + transactionAmount);
        Log.info("Transaction Date: " + formattedDate);

    }

    public void uploadBankFile() {
        String baseUrl = properties.getProperty("orgUrl");
        getDriver().navigate().to(baseUrl + uploadBankFileUrl);
        switchToFrame(accessibilityIframe);
        selectListboxItemByText(fileType, "Direct Debit Dishonor File");
        waitForElementToBeVisible(bankUploadFilePath, 30);
        enterTextField(bankUploadFilePath,
                Paths.get("src/test/resources/LodgementReferenceFile/GeneratedLodgementReference.txt").toAbsolutePath()
                        .toString());
        click(uploafFileButton);
        int retryCount = 8;
        for (int i = 0; i < retryCount; i++) {
            try {
                switchToDefaultContent();
                switchToFrame(accessibilityIframe);
                waitForElementToBeVisible(successMessageText, 10);
                break;
            } catch (Throwable e) {
                Log.info("Waiting for success message to appear...");
            }
        }
        Assert.assertEquals("SUCCESS", getTextFromElement(successMessageText));
        Log.info("Bank file uploaded successfully");
    }

    public void openChargeRecord(){
        click(chargeRecordButton);
        refreshTheBrowser();
        waitForPageToFinishLoading();
        waitForElementToBeClickable(relatedTabButtonOnChargeField);
        click(relatedTabButtonOnChargeField);
    }
}
