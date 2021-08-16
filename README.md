# LawnCareAPI

# Business Requirements
Users (either home owners or lawn care contractors) may self register by providing at least their name, email address, and street address.  They must also indicate whether they are providing lawn care, or seeking lawn care.
Users seeking lawn care service can request service at their home on a specific day and time,
Service providers can retrieve a list of open service requests.
Service providers can search a list of open service requests by City and State or by Postal code.
Service providers can "claim" a request my marking it as accepted.  Once the request is accepted by a service provider no other service providers can "claim" it.
Service providers can mark a claimed request as completed.
User may NOT request recurring service.  They must create a new request for each lawn care service day and time.
Users may also update their own information including:  name and street address.  Their email address cannot be changed.
Users are required to create a password to use to access the service.  Users may change their own password at any time.
A user can reset their password by making a request to the service.  The service will generate a new temporary password and email it to the user.

# Technical Requirements
Your API must be Richardson Maturity Model level 3 (HATEOAS) compliant
Your API must use HTTP Basic authentication
Your API must use JSON as the primary data serialization mechanism.
Your persistent data storage can be in any relational database, graph database, or document database; columnar (column family) and key-value storage is not allowed on this assignment.
Your API should handle errors by responding with an appropriate (justifiable) HTTP response code and a json body with helpful information about the error.
