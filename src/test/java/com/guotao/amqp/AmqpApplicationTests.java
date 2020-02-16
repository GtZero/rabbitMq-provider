package com.guotao.amqp;

import com.guotao.amqp.procuder.BootProducter;
import com.guotao.amqp.procuder.User;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class AmqpApplicationTests {
    @Autowired
    private BootProducter bootProducter;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Test
    void config() {
        rabbitAdmin.declareExchange(new DirectExchange("gt-ex3", false, false));
    }

    @Test
    void send() throws Exception {
        Message  message = new Message("i an gt".getBytes(), null);
        Map<String, Object> map = new HashMap<>();
        map.put("iid", "gt");
        map.put("__TypeId__", "com.guotao.rabbitmq.rabbit.User");

        User user = new User();
        user.setId("11");
        user.setNumber("1");
        user.setName("gt");
        bootProducter.sendMessage(user, map);
        Thread.sleep(1000);
//        bootProducter.sendMessage(new Integer(4), null);
    }

}
