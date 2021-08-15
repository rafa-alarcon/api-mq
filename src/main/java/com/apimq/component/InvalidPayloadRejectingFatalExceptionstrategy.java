package com.apimq.component;

import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;



import lombok.extern.slf4j.Slf4j;

/**
 * Extension of Spring-AMQP's
 * {@link ConditionalRejectingErrorHandler.DefaultExceptionStrategy}
 * which also considers a root cause of {@link MethodArgumentNotValidException}
 * (thrown when payload does not validate) as fatal.
 */
@Slf4j
public class InvalidPayloadRejectingFatalExceptionstrategy implements FatalExceptionStrategy {
	 
	  @Override
	  public boolean isFatal(Throwable t) {
	    if (t instanceof ListenerExecutionFailedException &&
	          (t.getCause() instanceof MessageConversionException ||
	           t.getCause() instanceof MethodArgumentNotValidException)) {
	      log.warn("Fatal message conversion error; message rejected; it will be dropped: {}",
	                  ((ListenerExecutionFailedException) t).getFailedMessage());
	      return true;
	    }
	    return false;
	  }
	}