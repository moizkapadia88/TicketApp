# TicketApp
This ticket app can be used to book a ticket from One station to another.

Below are the supported APIs : 

1. Purchase Ticket Endpoint:
2. 
Method: POST
URL: /tickets/purchaseTicket

Description: Allows users to purchase a train ticket. It accepts a JSON payload containing details such as the origin, destination, and user information.


4. Get Receipt Endpoint:

Method: GET
URL: /tickets/users/{userId}

Description: Retrieves the receipt for a specific user's ticket purchase. It expects the user's email or identifier as a path variable.

3. Get Users by Section Endpoint:

Method: GET
URL: /tickets/usersBySection

Parameters: section (Query Parameter)

Description: Retrieves a map of users and their allocated seats within a specified train section. It expects the section identifier as a query parameter.

4. Remove User Endpoint:

Method: DELETE
URL: /tickets/users/{userId}

Description: Removes a user from the train. It expects the user's email or identifier as a path variable.

5. Modify User Seat Endpoint:

Method: PUT
URL: /tickets/users/{userId}

Description: Modifies a user's allocated seat on the train. It expects the user's email or identifier as a path variable and the new seat information in the request body.
