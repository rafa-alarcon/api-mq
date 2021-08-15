# API MQ
### Description

Poject working with a Producer-Consumer pattern using RabbitMQ

### Fetures
- Incomming data is added to RabbitMQ queue
- If Max retries is reached the messages are moved to dead letter queue
- Valide messages received by the consumer are saved on the database
- Email is sent for a valid messsage once is saved
- Validations run on incomming message
- Swagger UI added at endpoint /swagger-ui/

### Flow
![2021-08-14_20-02-32](https://user-images.githubusercontent.com/11185117/129464609-28797e62-6a2c-43a0-9954-6c76c558faf1.png)
