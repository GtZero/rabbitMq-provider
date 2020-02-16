package com.guotao.amqp.procuder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.UUID;

@Component
public class BootProducter {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean b, String s) {
            System.out.println(correlationData);
            System.out.println("ack : " + b);
            System.out.println("this is confirms " + s);
            System.out.println("confirmListener");
        }
    };

    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(Message message, int i, String s, String s1, String s2) {
            System.out.println("_________________________________");
            System.out.println("this is return message start");
            System.out.println("this is return ------ " + new String(message.getBody()));
            System.out.println("return main config");
            System.out.println("_________________________________");
        }
    };

    public void sendMessage(User body, Map<String, Object> properties) throws Exception {
        MessageHeaders messageHeaders = new MessageHeaders(properties);
        org.springframework.messaging.Message<Object> message = MessageBuilder.createMessage(body, messageHeaders);
        MessageProperties pro = new MessageProperties();
        pro.setHeader("__TypeId__", properties.get("__TypeId__"));
        pro.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(body);
        String json = new Gson().toJson(body);
        System.out.println("one String " + str);
        System.out.println("two str " + new Gson().toJson(body));
        Message message1 = new Message(json.getBytes(), pro);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        System.out.println("producter send message");
        rabbitTemplate.convertAndSend("gt-exchange", "gt.*", body, correlationData);
        rabbitTemplate.convertAndSend("gt-exchange", "gt.*", body, correlationData);
        rabbitTemplate.convertAndSend("gt-exchange", "gt.*", body, correlationData);
        rabbitTemplate.convertAndSend("gt-exchange", "gt.*", body, correlationData);
        rabbitTemplate.convertAndSend("gt-exchange", "gt.*", body, correlationData);
        rabbitTemplate.convertAndSend("gt-exchange", "gt.*", body, correlationData);
        rabbitTemplate.convertAndSend("gt-exchange", "gt.*", body, correlationData);
    }

}
