Feature: Sim card activation

  Scenario: Successful SIM card activation is recorded
    Given a customer submits an activation request with iccid "1255789453849037777" and email "success@example.com"
    When the activation request is sent
    Then the activation record with id 1 should be active

  Scenario: Failed SIM card activation is recorded
    Given a customer submits an activation request with iccid "8944500102198304826" and email "failure@example.com"
    When the activation request is sent
    Then the activation record with id 2 should be inactive
