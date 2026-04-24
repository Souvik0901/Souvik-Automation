@TSIP-T34734 @datemovement
Feature: BPAY Payment|Loans - RTA - BPAY Payment to Recovery Reversal

  Scenario: [Test Setup] Move System Date to 02/01/2025
    Given Open browser
    When Navigate to Salesforce Login Page
    And Log in as Administrator
    And Save System Date
    And From App Launcher Open "Loan Servicing"
    When I fetch all Processing DAG records
    And I update all DAG records to Success
    And Move Current System Date to "01/02/2025" for ERPSandbox
    When I fetch all Processing DAG records
    And I update all DAG records to Success

  Scenario Outline: BPAY Payment | Loans - RTA - BPAY Payment to Recovery Reversal
        Test Cases Involved:
        1) TSIP-T40466:  BPAY Payment | Loans - RTA - BPAY Payment to Recovery Reversal
        Jira:
        1) Ticket: https://racvone.atlassian.net/browse/TSIP-40464

    Given Create A New Loan Using API V2
      | Test Case Name      | TSIP-T34734                |
      | Loan Test Data Name | Consumer_Unsecured_Monthly |
    And Open browser
    When Navigate to Salesforce Login Page
    And Log in as Administrator
    When I fetch all Processing DAG records
    And I update all DAG records to Success
    And Move Current System Date to 1 days ahead in ERPQASandbox
    And Close browser
    And Open browser
    When Navigate to Salesforce Login Page
    And Log in as Administrator
    And From App Launcher Open "Loan Servicing"
    And Open "Saved" CL Contract
    And Manually Write Off Current Loan
    And Close browser
    And Open browser
    And Navigate to Salesforce Login Page
    And Log in as <User>
    And Open "Saved" CL Contract
    When Open CL Loan View
    And Go to "Payment" under "Transactions" tab
    And Create a Loan Payment Transaction with Payment Mode "BPAY" and Transaction Amount "100" and with ACH on recovery loan
    And Close browser
    And Open browser
    When Navigate to Salesforce Login Page
    And Log in as Administrator
    And From App Launcher Open "RACVF Service Console"
    And Fetch the mapping header id for 'Loans - RTA - BPAY Payment to Recovery Reversal'
    And Save Mapping Header Id
    And Open Developer Console
    And Evaluate "DATAMAPPERINITIATORJOB" apex code in developer console with job name "SavedMappingHeader"
    And Open "Saved" CL Contract
    And Open CL Loan View
    And Save value of "Product" field in CL Contract Loan View Page
    And Go to "Payment" under "Transactions" tab
    And Open Transaction with index 1
    And Open Accounting Entries
    Then validate the details of Accounting Entries
      | Field                 | Expected Value                      |
      | Event Description     | Reversal of Direct Credit Repayment |
      | GL Account (Debit)    |                             1003039 |
      | GL Account (Credit)   |                             5350401 |
      | Cost Centre (Credit)  |                                1080 |
      | Cost Centre (Debit)   |                                1000 |
      | Product Code (Credit) |                               99999 |
      | Product Code (Debit)  |                               99999 |

    Examples:
      | User                    |
      | Lending Support Officer |
