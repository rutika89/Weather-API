Feature: Weather API Test
  Verify weather API functionality

	@APIKeyError
  Scenario: Verify API Key error message
    When I try to register a station without an API key
    Then I get error message in the response as "Invalid API key. Please see http://openweathermap.org/faq#error401 for more info."
    And status code is "401"

	@StationNotFound
  Scenario: Verify error message station not found
    When I try to delete a station which is already deleted
    Then I get error message in the response as "Station not found"
    And status code is "404"

	@RegisterStation
  Scenario: Register two stations and verify that it is successful
   	When I register below stations the code is 201
      | external_id  | name                       | latitude | longitude | altitude |
      | DEMO_TEST001 | Team Demo Test Station 001 |    33.33 |   -122.43 |      222 |
      | DEMO_TEST001 | Team Demo Test Station 001 |    33.33 |   -122.43 |      222 |
