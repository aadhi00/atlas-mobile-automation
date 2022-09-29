Feature: Login Feature

  @invalid
  Scenario Outline: Entering invalid username and invalid password at login
    Given User is on login page
    When User enters "<invalidUsername>" and "<invalidPassword>"
    And User taps Sign In button
    Then Alert message is displayed
    Examples:
      | invalidUsername | invalidPassword |
      | aadhithyan      | 12345678        |

  Scenario Outline: Entering valid username and valid password at login with a paired device
    Given User is on login page
    When User enters "<validUsername>" and "<validPassword>"
    And User taps Sign In button
    Then User is taken to Biometric page
    Examples:
      | validUsername | validPassword |
      | testaugust01  | Password@1    |

  Scenario Outline: Entering valid username and valid password at login with an unpaired device
    Given User is on login page
    When User enters "<validUsername>" and "<validPassword>"
    And User taps Sign In button
    Then User is taken to Biometric page
    Examples:
      | validUsername | validPassword |
      | testaugust01  | Password@1    |