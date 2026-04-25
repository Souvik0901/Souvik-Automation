package au.com.racv.automation.portal.cloudlending.stepdefinitions.clcontracts;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import au.com.racv.automation.portal.cloudlending.pages.clcontracts.ClContractTransactionPage;
import au.com.racv.automation.utils.DataStore;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;

public class ClContractTransactionSteps {
    ClContractTransactionPage transactionPage = new ClContractTransactionPage();
    private String normalize(String value) {
        if (value == null) return "";
        return value.replaceAll("\\s+", " ").trim();
    }

    @Given("^Create a Loan Payment Transaction with Payment Mode \"([^\"]*)\" and Transaction Amount \"([^\"]*)\"( and with ACH)?( on recovery loan)?$")
    public void createLoanPaymentTransaction(String paymentMode, String transactionAmount, String withAchText,
            String onRecoveryLoanText) {
        String actualTransactionAmount = DataStore.getStoredValue(transactionAmount) == null
                ? transactionAmount
                : DataStore.getStoredValue(transactionAmount).toString();

        boolean withAch = withAchText != null; // true if "and with ACH" is present
        boolean onRecoveryLoan = onRecoveryLoanText != null;

        transactionPage.createLoanPaymentTransaction(paymentMode, actualTransactionAmount, withAch, onRecoveryLoan);
    }

    @Given("Create New LPT on {string} Date with {string} Payment Amount and {string} as Payment Method")
    public void createNewLptWithSavedFinalPayoffAmount(String paymentDate, String paymentAmount,
            String paymentMethod) {
        transactionPage.createNewLptWithPaymentDateAmountAndMethod(paymentDate, paymentAmount, paymentMethod);
    }

    @Given("Validate Loan Payment Transaction no. {string} has Payment Mode as {string} and Transaction Amount as {string}")
    public void validateLoanPaymentTransactionPaymentModeAndTransactionAmount(String transactionIndex,
            String paymentMode, String transactionAmount) {
        Map<String, String> transactionSummary = transactionPage
                .getLoanTransactionDetails(Integer.parseInt(transactionIndex));
        Assert.assertEquals(paymentMode, transactionSummary.get("PAYMENT_MODE"));
        Assert.assertEquals(transactionAmount, transactionSummary.get("TRANSACTION_AMOUNT"));
    }

    @Given("Validate Payoff Amount")
    public void validatePayoffAmount() {
        transactionPage.click(transactionPage.loanPayOffButton);
        transactionPage.switchToDefaultContent();
        transactionPage.switchToFrame(transactionPage.recordAPayOffIframe);
        transactionPage.waitForElementToBeVisible(transactionPage.recordAPayOffHeader, 10);
        String payoffAmount = transactionPage.getAttributeValueFromElement(transactionPage.transactionAmountInput,
                "value");
        Assert.assertEquals(DataStore.getStoredValue("Expected Payoff Amount"), payoffAmount.replace(",", ""));
    }

    @Given("Validate Newly Created LPT")
    public void validateNewlyCreatedLpt() {
        Assert.assertEquals(transactionPage.getTextFromElement(transactionPage.paymentModeText),
                DataStore.getStoredValue("Payment Mode"));
        Assert.assertEquals(transactionPage.getTextFromElement(transactionPage.transactionDateText),
                DataStore.getStoredValue("Transaction Date"));
        Assert.assertEquals(transactionPage.getTextFromElement(transactionPage.transactionAmountText),
                DataStore.getStoredValue("Transaction Amount"));
    }

    @Given("Open Transaction with index {int}")
    public void openTransactionWithIndex(int index) {
        transactionPage.openLoanPaymentTransactionByIndex(index);
        transactionPage.switchToDefaultContent();
    }

    @Given("Open Fee Payment with index {int} and open the Accounting Entry")
    public void openFeePaymentWithIndex(int index) {
        transactionPage.openFeePaymentTransactionByIndex(index);
        transactionPage.switchToDefaultContent();
    }

    @Given("Open Bill with index {int}")
    public void openBillWithIndex(int index) {
        transactionPage.openBillByIndex(index);
        transactionPage.switchToDefaultContent();
    }

    @Given("Reverse the recently generated LPT to dishonour payment with {string} reason")
    public void reverseTheRecentlyGeneratedLptToDishonourPayment(String reason) {
        openTransactionWithIndex(1);
        reverseAnLptWithReason(reason);
    }

    @Given("Reverse the LPT with {string} reason")
    public void reverseAnLptWithReason(String reason) {
        transactionPage.click(transactionPage.relatedButton);
        transactionPage.waitForElementToBeVisible(transactionPage.repaymentTransactionReversalHeader, 10);
        transactionPage.scrollIntoView(transactionPage.repaymentTransactionReversalNewButton);
        transactionPage.click(transactionPage.repaymentTransactionReversalNewButton);
        transactionPage.switchToFrame(transactionPage.reverseAPaymentIframe);
        transactionPage.waitForElementToBeVisible(transactionPage.reverseAPaymentHeader, 10);
        transactionPage.selectListboxItemByText(transactionPage.reasonCodeListbox, reason);
        // transactionPage.click(transactionPage.datePickerButton);
        transactionPage.click(transactionPage.saveButton);
        transactionPage.waitForElementToBeInVisible(transactionPage.saveButton, 20);
        transactionPage.switchToDefaultContent();
        transactionPage.waitForElementToBeVisible(transactionPage.relatedButton, 10);
    }

    @Given("^validate the details of (?:LPT|Accounting Entries|Repayment Transaction Reversal|Payoff Quote|Bill|Lead)$")
    public void validateDetailsOfLPT(DataTable expectedDetails) {
        List<Map<String, String>> expectedValues = expectedDetails.asMaps(String.class, String.class);

        // Loop through each expected field and validate it
        for (Map<String, String> row : expectedValues) {
            String field = row.get("Field");
            String expectedValue = row.get("Expected Value");

            // Extract the actual value from the UI based on the field
            String actualValue = transactionPage.getFieldValueFromLPTDetailsTab(field);

            if (expectedValue != null && expectedValue.startsWith(">")) {
                double expected = Double.parseDouble(expectedValue.substring(1).trim());
                double actual = Double.parseDouble(actualValue.trim());
    
                Assert.assertTrue("Validation failed for field: " + field +". Expected > " + expected + " but was " + actual, actual > expected);
            }
            else if (expectedValue != null && expectedValue.startsWith("<")) {
                double expected = Double.parseDouble(expectedValue.substring(1).trim());
                double actual = Double.parseDouble(actualValue.trim());
    
                Assert.assertTrue("Validation failed for field: " + field +". Expected < " + expected + " but was " + actual, actual < expected);
            }
            else if ("Address".equalsIgnoreCase(field)) {
                    String normalizedExpected = normalize(expectedValue);
                    String normalizedActual = normalize(actualValue);
                    Assert.assertEquals("Validation failed for field: " + field, normalizedExpected, normalizedActual);
            }
            
            // Assert the expected value with the actual value
            else {
                    Assert.assertEquals("Validation failed for field: " + field, expectedValue, actualValue);
            }
        
    
        }
    }

    @Given("^validate the details contains in LPT$")
    public void validateAPSLPTLinkage(DataTable expectedDetails) {
        List<Map<String, String>> expectedValues = expectedDetails.asMaps(String.class, String.class);

        // Loop through each expected field and validate it
        for (Map<String, String> row : expectedValues) {
            String field = row.get("Field");

            // Value i want to check contains in the field
            String expectedValue = row.get("Expected Value");
            String expectedModuleName = expectedValue.split("\\s+")[0].trim();
            // Extract the actual value from the UI based on the field
            String actualValue = transactionPage.getFieldValueFromLPTDetailsTab(field);

            Assert.assertTrue("Validation failed for field: " + field + " | Expected to contain: " + expectedModuleName
                    + " | Actual: " + actualValue, actualValue.contains(expectedModuleName));
        }

    }

    @Given("^validate the stored details of (?:LPT|Accounting Entries|Repayment Transaction Reversal)$")
    public void validateStoredDetailsOfLPT(DataTable expectedDetails) {
        List<Map<String, String>> expectedValues = expectedDetails.asMaps(String.class, String.class);

        // Loop through each expected field and validate it
        for (Map<String, String> row : expectedValues) {
            String field = row.get("Field");
            String expectedValue = row.get("Expected Value");
            expectedValue = DataStore.getStoredValue(expectedValue).toString();
            // Extract the actual value from the UI based on the field
            String actualValue = transactionPage.getFieldValueFromLPTDetailsTab(field);

            // Assert the expected value with the actual value
            Assert.assertEquals("Validation failed for field: " + field, expectedValue, actualValue);
        }
    }

    @Given("Reverse other loan transaction with index {string} and transaction type {string}")
    public void reverseOtherLoanTransaction(String transactionIndex, String transationType) {
        transactionPage.reverseOtherPaymentTransactions(transactionIndex, transationType);
    }

    @Given("Open Accounting Entries")
    public void openAccountingEntries() {
        transactionPage.waitForElementToBeVisible(transactionPage.relatedButton, 20);
        transactionPage.refreshTheBrowser();
        transactionPage.clickUsingJavaScript(transactionPage.relatedButton);
        transactionPage.scrollDownIntoView(transactionPage.accountEntriesHeader);        
        transactionPage.waitForElementToBeVisible(transactionPage.accountEntriesHeader, 10);
        transactionPage.clickUsingJavaScript(transactionPage.accountingEntryLink);
    }

    @Given("Open Repayment Transaction Reversal Entries")
    public void openRepaymentTransactionReversalEntries() {
        transactionPage.waitForElementToBeVisible(transactionPage.relatedButton, 20);
        transactionPage.refreshTheBrowser();
        transactionPage.clickUsingJavaScript(transactionPage.relatedButton);
        transactionPage.scrollDownIntoView(transactionPage.repaymentTransactionReversalHeader); 
        transactionPage.waitForElementToBeVisible(transactionPage.repaymentTransactionReversalHeader, 20);
        transactionPage.clickUsingJavaScript(transactionPage.repaymentTractionReversalEntryLink);
    }

    @Given("Create bank upload file")
    public void createBankUploadFile() throws IOException, ParseException {
        transactionPage.createBankUploadFile();
    }

    @Given("Upload bank file")
    public void uploadBankFile() {
        transactionPage.uploadBankFile();
    }

    @Given("Open Charge Record and click on Related tab")
    public void openChargeRecord(){
        transactionPage.openChargeRecord();
    }

}
