Feature: Validating Place APIs

  @AddPlace
  Scenario Outline: Verify if Place is being succesfully added using AddPlaceAPI
    Given Add Place payload with '<name>' '<language>' '<address>'
    When User calls 'addPlaceApi' with 'POST' http request
    Then The API call is success with status codee 200
    And 'status' in response body is 'OK'
    And 'scope' in response body is 'APP'
    And verify place id created maps to '<name>' using 'getPlaceApi'

    Examples: 
      | name     | language | address                 |
      | getTest4 | Spanish  | WorldTrade Cross center |

  #			| Juan	| Spanish  |	Sea Cross center	 |
  
  @DeletedPlace
  Scenario: Verify if delete place functionality is working
    Given Delete place payload
    When User calls 'deletePlaceApi' with 'POST' http request
    Then The API call is success with status codee 200
    And 'status' in response body is 'OK'
