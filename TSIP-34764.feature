@TSIP-T34764 @datemovement
Feature: BPAY Payment|Consumer Interest Free Loans - RTA - BPAY Payment Reversal

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

  Scenario Outline: BPAY Payment | Consumer Interest Free Loans - RTA - BPAY Payment Reversal
        Test Cases Involved:
        1) TSIP-T34764:  BPAY Payment | Consumer Interest Free Loans - RTA - BPAY Payment Reversal
        Jira:
        1) Ticket: https://racvone.atlassian.net/browse/TSIP-40569

    Given Create A New Loan Using API V2
      | Test Case Name      | TSIP-T34764                   |
      | Loan Test Data Name | Consumer_InterestFree_Monthly |
    And Open browser
    When Navigate to Salesforce Login Page
    And Log in as Administrator
    When I fetch all Processing DAG records
    And I update all DAG records to Success
    And Move Current System Date to 1 days ahead in ERPQASandbox
    And Close browser
    And Open browser
    And Navigate to Salesforce Login Page
    And Log in as <User>
    And Open "Saved" CL Contract
    When Open CL Loan View
    And Go to "Payment" under "Transactions" tab
    And Create a Loan Payment Transaction with Payment Mode "BPAY" and Transaction Amount "100"
    And Close browser
    And Open browser
    When Navigate to Salesforce Login Page
    And Log in as Administrator
    When I fetch all Processing DAG records
    And I update all DAG records to Success
    And Move Current System Date to 1 days ahead in ERPQASandbox
    And Close browser
    And Open browser
    And Navigate to Salesforce Login Page
    When Log in as <User>
    And Fetch the dynamic query for mapping header "Consumer Interest Free Loans - RTA - BPAY Payment Reversal"
    And Extract the where clause from dynamic query
    And Fetch invalid loan ids using the dynamic query for "RTA"
    And Make the invalid loans valid
    And Open "Saved" CL Contract
    And Open CL Loan View
    And Go to "Payment" under "Transactions" tab
    And Open Transaction with index 1
    And Reverse the LPT with "Human Error" reason
    And Close browser
    And Open browser
    When Navigate to Salesforce Login Page
    And Log in as Administrator
    And From App Launcher Open "RACVF Service Console"
    And Fetch the mapping header id for 'Consumer Interest Free Loans - RTA - BPAY Payment Reversal'
    And Save Mapping Header Id
    And Open Developer Console
    And Evaluate "DATAMAPPERINITIATORJOB" apex code in developer console with job name "SavedMappingHeader"
    And Open "Saved" CL Contract
    And Open CL Loan View
    And Save value of "Product" field in CL Contract Loan View Page
    And Go to "Payment" under "Transactions" tab
    And Open Transaction with index 1
    And Open Repayment Transaction Reversal Entries
    And Open Accounting Entries
    Then validate the details of Accounting Entries
      | Field                 | Expected Value                      |
      | Event Description     | Reversal of Direct Credit Repayment |
      | GL Account (Debit)    |                             10515XX |
      | GL Account (Credit)   |                             1003039 |
      | Cost Centre (Credit)  |                                1000 |
      | Cost Centre (Debit)   |                                1080 |
      | Product Code (Credit) |                               99999 |
      | Product Code (Debit)  |                               10501 |

    Examples:
      | User                    |
      | Lending Support Officer |
