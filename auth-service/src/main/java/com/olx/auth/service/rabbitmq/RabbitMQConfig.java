package com.olx.auth.service.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
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
public class RabbitMQConfig{
	
	@Value("${queue.name.auth}")
	private String queueName;
	
	@Value("${fanout.exchange}")
	private String fanoutExchange;
	
	@Value("${queue.name.auth.delete}")
	private String queueNameDelete;
	
	@Value("${fanout.exchange.delete}")
	private String fanoutExchangeDelete;
	
	@Value("${queue.name.dead.letter}")
	private String queueNameDeadLetter;
	
	@Value("${direct.exchange.dead.letter}")
	private String directExchangeDeadLetter;

	//Dead Letter Queue
	@Bean
	Queue dlq() {
		return QueueBuilder.nonDurable(queueNameDeadLetter).build();
	}
	
	//Dead Letter Queue exchange
	@Bean
	DirectExchange deadLetterExchange() {
		return new DirectExchange(directExchangeDeadLetter);
	}
	
	//Bind 'dead_queue' and 'dead_exchange'.
	@Bean
	Binding DLQbinding() {
		return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with("bloola");
	}
	
	@Bean
	Queue queue() {
		return QueueBuilder.nonDurable(queueName).withArgument("x-dead-letter-exchange", directExchangeDeadLetter)
				.withArgument("x-dead-letter-routing-key", "bloola")
				.withArgument("x-message-ttl", 10000).build();
	}
	
	@Bean
	Queue queueDelete() {
		return QueueBuilder.nonDurable(queueNameDelete)
				.withArgument("x-message-ttl", 10000).build();
	}
	
	@Bean
	FanoutExchange exchange() {
		return new FanoutExchange(fanoutExchange);
	}
	
	@Bean
	FanoutExchange exchangeDelete() {
		return new FanoutExchange(fanoutExchangeDelete);
	}
	
	@Bean
	Binding binding(Queue queue, FanoutExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange);
	}
	
	@Bean
	Binding bindingDelete(Queue queueDelete, FanoutExchange exchangeDelete) {
		return BindingBuilder.bind(queueDelete).to(exchangeDelete);
	}
	
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
}
