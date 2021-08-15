package com.apimq.config;


import org.aopalliance.aop.Advice;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.OptionalValidatorFactoryBean;

import com.apimq.component.InvalidPayloadRejectingFatalExceptionstrategy;
import com.apimq.service.EmailService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableRabbit
@Slf4j
public class RabbitListenerConfig implements RabbitListenerConfigurer {
	
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
	    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
	    factory.setConnectionFactory(this.connectionFactory);
	    factory.setMessageConverter(messageConverter());
	    factory.setConcurrentConsumers(3);
	    factory.setMaxConcurrentConsumers(10);
	    factory.setAdviceChain(retries());
	    factory.setErrorHandler(
	    	      new ConditionalRejectingErrorHandler(
	    	          new InvalidPayloadRejectingFatalExceptionstrategy()));
	    return factory;
	}
	
	@Bean
	public RetryOperationsInterceptor retries() {
	    return RetryInterceptorBuilder.stateless().maxAttempts(3)
	            .backOffOptions(1000,
	                    3.0, 10000)
	            .recoverer(new RejectAndDontRequeueRecoverer()).build();
	}
	
	@Autowired
	public ConnectionFactory connectionFactory; 
	
	@Bean
    public Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return new Jackson2JsonMessageConverter(mapper);
    }
	
	@Bean
	public DefaultMessageHandlerMethodFactory defaultHandlerMethodFactory() {
	    DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
	    factory.setValidator(amqpValidator());
	    return factory;
	}
	
	@Bean
	public Validator amqpValidator() {
	    return new OptionalValidatorFactoryBean();
	}
	
	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
	    registrar.setContainerFactory(rabbitListenerContainerFactory());
	    registrar.setMessageHandlerMethodFactory(defaultHandlerMethodFactory());
	}

}
