Project Overview: E-commerce System Workflow
This project outlines the step-by-step workflow for an E-commerce system that allows users to browse categories, select products, add them to a cart, place an order, make payments, and track delivery. The details are as follows:
________________________________________
Technologies and Tools Used
1.	Programming Language:
o	Java: The core language for development.
2.	Framework:
o	Spring Boot: Used for building microservices and RESTful APIs.
3.	Database:
o	MySQL: Utilized for data storage, including user details, product catalogs, orders, and delivery addresses.
4.	Messaging and Notification:
o	Kafka: Configured via Docker for sending email notifications.
5.	Service Discovery:
o	Eureka Server: Ensures smooth communication and distribution between microservices.
6.	API Management:
o	API Gateway: Handles routing and load balancing for the microservices.
7.	Payment Integration:
o	Stripe: Integrated for secure and reliable payment processing.
8.	Delivery Service:
o	Delivery addresses are hardcoded in MySQL, restricting service availability to Ukraine and Europe.
Note – currently working on AI model to get delivery status by chatgpt


1. Browse Categories
•	Endpoint: GET http://localhost:8080/category/
Retrieve all available product categories. Choose your preferred category from the displayed list.
________________________________________
2. Search Products by Category
•	Endpoint: GET http://localhost:8080/category/searchByCategoryName/
Example: Search for products in the "PHONES" category.
Alternatively, you can fetch all products using the endpoint:
•	Endpoint: GET http://localhost:8080/product/
________________________________________
3. Add Products to Cart
•	Endpoint: POST http://localhost:8085/cart/add
Add products to your cart by specifying the product ID and quantity in the request payload.
Example Request:
{
    "item": [
        {
            "productId": 352,
            "quantity": 5
        },
        {
            "productId": 355,
            "quantity": 4
        }
    ]
}
Example Response:
{
    "userId": 2,
    "items": [
        {
            "cartId": 3,
            "productId": 352,
            "productName": "IPHONE",
            "quantity": 5,
            "price": 1000.0
        },
        {
            "cartId": 4,
            "productId": 355,
            "productName": "IPHONE",
            "quantity": 4,
            "price": 1800.0
        }
    ],
    "total": 12200,
    "cartCreatedTime": "2024-12-27T19:12:51.1442883"
}
________________________________________
4. Confirm Cart
•	Endpoint: GET http://localhost:8085/cart/getCartById/{userId}
Confirm the cart details and proceed to the order service.
________________________________________
5. Place an Order
•	Endpoint: POST http://localhost:8086/order/getCartById/{cartId}
Place an order using the cartId.
Example Response:
{
    "orderId": 1,
    "cartId": 1,
    "orderStatus": "SUCCESSFUL",
    "price": 1800
}
________________________________________
6. Make Payment
•	Endpoint: POST http://localhost:9095/pay/{orderId}
Pay for the order. A payment URL will be generated.
Example Response:
{
    "status": "SUCCESSFUL",
    "message": "HERE IS YOUR LINK TO PAY",
    "lineItems": [
        {
            "adjustableQuantity": {
                "enabled": true,
                "maximum": 10,
                "minimum": 1
            },
            "price": "price_1QeJcjFKjxizuB9sqD6XOUIp",
            "quantity": 1
        }
    ],
    "url": "https://buy.stripe.com/test_9AQ3d85pY4Sm4X68xU"
}
Action: Open the URL in a browser to complete payment.
________________________________________
7. Delivery Service
Register/Login as a User
•	Signup Endpoint: POST http://localhost:8090/user/signup
•	USER WILL BE INFORMED WHEN SIGNEDUP BY SENDING EMAIL
USED SERVICE AS KAFKA FOR SENDING EMAIL 
Example Payload:
{
    "userName": "",
    "userPhone": "",
    "userPassword": "",
    "userEmail": "",
    "roles": "",
    "userHouseNumber": "",
    "userStreet": "",
    "userLandMark": "",
    "city": "",
    "state": "",
    "country": "",
    "postalCode": ""
}
•	Delivery Request Endpoint: POST http://localhost:8091/deliveryUser/{cartId}/{email}
Use this endpoint to finalize the delivery after payment.
Example Response:
{
    "id": 752,
    "createdAt": "2025-01-06T18:58:42.4352385",
    "updatedAt": "2025-01-06T18:58:42.4352385",
    "userName": "XXXXXX",
    "userPhone": "050505050",
    "userEmail": "XXXXXX@gmail.com",
    "userPassword": "NOT VISIBLE BECAUSE OF PRIVACY REASONS",
    "userHouseNumber": "XX",
    "userStreet": "XXXXXXX",
    "userLandMark": "XXXXXX",
    "userCity": "XXXXXXX",
    "userState": "XXXXXXX",
    "userCountry": "XXXXXXX",
    "userPostalCode": XXXXX,
    "message": "PARCEL WILL DELIVER IN MAXIMUM 2 DAYS",
    "countryDistance": 260, KILOMETERS
    "cartId": 1,
    "totalAmount": 1800 USD
}
________________________________________
For Returning Customers
•	Delivery service uses a database to calculate the distance between the warehouse and the user’s location based on the country, state, city, and postal code. The estimated delivery time is then provided.

