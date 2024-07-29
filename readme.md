## E-commerce payment system
To run the project, just run `docker compose up` in the root directory of the project.

The project will be available at `http://localhost:8000`.

The project has a main endpoint `api/v1/payments` that receives a JSON payload with the following structure:
```json
{
  "senderId": 10,
  "receiverId": 20,
  "amount": 30
}
```
Using the enpoint `api/v1/payments/{id}` you can check the payment for that id.

Using the enpoint `api/v1/payments/sender/{senderId}` you can check the payments for that sender.

Using the enpoint `api/v1/payments/receiver/{receiverId}` you can check the payments for that receiver.

Finally, using the enpoint `api/v1/payments/suspicious` you can check the accounts that are suspicious based on the interactions (i.e. transactions) between different accounts.