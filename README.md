# DeliverySample Android Mobile App
  
# User Requirements
   1. Retrieve list of deliveries from the API
   2. Display list of Delivery items.
   3. Show the Delivery details with location on map
   
   
# Project Specification
   1. Project is developed using MVVM Clean architecture.
   2. Programming language - KOTLIN
   3. App is fetching the list of items from the Network and saving them into the DB for local caching.
   4. Then the app displays the list from cache and request from server if needed.
   5. The list use a page size of 20 to fetch the list from server.
   

# Libraries used
  1. android architecture components -- for MVVM 
  2. android JetPack -- for androidx
  3. Retrofit    -- for Network api 
  4. Dagger2   -- for DI
  5. Robolectric -- UI Testing
  6. Mockito  -- Mocking framework for testing
  7. Room  --  android architecture component for local DB
  8. RxJava -- for Reactive Programming
  9. Espresso -- For UI testing
 10. Paging -- android architecture component paging library   
   
  
# Improvements / Not implemented
  
  1. Constraint Layout can be used.
  2. Can use Koin for DI in kotlin 
  3. Code coverage can be improved by writing unit tests
  
# Changes required for Using
  1. Generate your own key for google maps and update it in "google_maps_key.xml" file.
  
 