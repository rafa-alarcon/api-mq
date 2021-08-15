package com.apimq.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

	@Value("${rabbitmq.queue}")
	String queueName;
	
	@Value("${rabbitmq.dead-letter.queue}")
	String deadLetterQueueName;

	@Value("${rabbitmq.exchange}")
	String queueExchange;
	
	@Value("${rabbitmq.dead-letter.exchange}")
	String deadLetterExchangeName;

	@Value("${rabbitmq.dead-letter.routingkey}")
	private String dedLetterRoutingkey;
	
	@Value("${rabbitmq.routingkey}")
	private String routingkey;
	
	@Bean
	Queue dlq() {
		return QueueBuilder.durable(deadLetterQueueName).build();
	}

	@Bean
	Queue queue() {
		return QueueBuilder.durable(queueName).withArgument("x-dead-letter-exchange", deadLetterExchangeName)
				.withArgument("x-dead-letter-routing-key", dedLetterRoutingkey).build();
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(queueExchange);
	}
	
	@Bean
	DirectExchange deadLetterExchange() {
		return new DirectExchange(deadLetterExchangeName);
	}

	@Bean
	Binding dlqBinding() {
		return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with(dedLetterRoutingkey);
	}
	
	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingkey);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	

	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
}
