package org.study.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class Producer {
    public static void main(String[] args)    {
        //遵循amqp协议

        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        //设置 RabbitMQ 地址
        factory.setHost("localhost");
        Connection conn=null;
        Channel channel=null;
        try {
            //factory.setPort(15672);
            //建立到代理服务器到连接
            conn = factory.newConnection();

            //获得信道
            channel = conn.createChannel();
            //声明队列名
            String queueName = "queue2";
            /**
             * @Param1 队列的名称
             * @Param1 是否要持久化
             * @Param1  队列
             * @Param1 排他性
             * @Param1 是否自动删除，随着最后一个消费者完毕是否删除队列
             * @Param1 携带属性参数
             */
            channel.queueDeclare(queueName,false,false,false,null);
            //准备消息
            String message = "Hello world";
            channel.basicPublish("",queueName,null,message.getBytes());


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭通道
            if(channel!=null&&channel.isOpen()){
                try {
                    channel.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            //关闭连接
            if(conn!=null&&conn.isOpen()){
                try {
                    conn.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }

    }
}
