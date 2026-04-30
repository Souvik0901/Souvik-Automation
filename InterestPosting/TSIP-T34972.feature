@TSIP-T34972 @datemovement
Feature: Interest Posting | Consumer Secured Loans - IPT - Interest Capitalisation Reversal

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

  Scenario Outline: Interest Posting | Consumer Secured Loans - IPT - Interest Capitalisation Reversal
        Test Cases Involved:
        1) TSIP-T34972: Interest Posting | Consumer Secured Loans - IPT - Interest Capitalisation Reversal
        Jira:
        1) Ticket: https://racvone.atlassian.net/browse/TSIP-40848

    Given Create A New Loan Using API V2
      | Test Case Name      | TSIP-T34972                |
      | Loan Test Data Name | Consumer_Secured_Weekly    |
    And Open browser
    When Navigate to Salesforce Login Page
    And Log in as Administrator
    When I fetch all Processing DAG records
    And I update all DAG records to Success
    And Move Current System Date to 7 days ahead in ERPQASandbox
    And Close browser
    And Open browser
    And Navigate to Salesforce Login Page
    When Log in as Administrator
    And From App Launcher Open "RACVF Service Console"
    And Fetch the mapping header id for 'Consumer Secured Loans - IPT - Interest Capitalisation'
    And Save Mapping Header Id
    And Open Developer Console
    And Evaluate "DATAMAPPERINITIATORJOB" apex code in developer console with job name "SavedMappingHeader"
    And Open "Saved" CL Contract
    And Open CL Loan View
    And Go to "Interest Posting" under "Transactions" tab
    And Open Interst Posting Transaction with index 1
    And Open Accounting Entries For Interest Posting Transaction with index 1
    Then validate the details of Accounting Entries
      | Field                 | Expected Value          |
      | Event Description     | Interest Capitalisation |
      | GL Account (Debit)    |                 1051001 |
      | GL Account (Credit)   |                 5201001 |
      | Cost Centre (Credit)  |                    1080 |
      | Cost Centre (Debit)   |                    1080 |
      | Product Code (Credit) |                   10501 |
      | Product Code (Debit)  |                   10501 |
    And From App Launcher Open "Loan Servicing"
    When I fetch all Processing DAG records
    And I update all DAG records to Success
    And Move Current System Date to "02/01/2025" for ERPSandbox
    And Close browser
    And Open browser
    And Navigate to Salesforce Login Page
    When Log in as Administrator
    And From App Launcher Open "RACVF Service Console"
    And Fetch the mapping header id for 'Consumer Secured Loans - IPT - Interest Capitalisation Reversal'
    And Save Mapping Header Id
    And Open Developer Console
    And Evaluate "DATAMAPPERINITIATORJOB" apex code in developer console with job name "SavedMappingHeader"
    And Open "Saved" CL Contract
    And Open CL Loan View
    And Go to "Interest Posting" under "Transactions" tab
    And Open Interst Posting Transaction with index 1
    And Open Accounting Entries For Interest Posting Transaction with index 1
    Then validate the details of Accounting Entries  
      | Field                 | Expected Value                      |
      | Event Description     | Reversal of Interest Capitalisation |
      | GL Account (Debit)    |                             5201001 |
      | GL Account (Credit)   |                             1051001 |
      | Cost Centre (Credit)  |                                1080 |
      | Cost Centre (Debit)   |                                1080 |
      | Product Code (Credit) |                               10501 |
      | Product Code (Debit)  |                               10501 |
      
    Examples:
      | User                    |
      | Lending Support Officer |
