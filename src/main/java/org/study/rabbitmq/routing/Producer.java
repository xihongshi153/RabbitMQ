package org.study.rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class Producer {


    public static void main(String[] args)
    {
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
                String message = "Hello topic";

                //获得信道
                channel = conn.createChannel();

                //准备交换机
                String exchangeName="topic-exchange";
                //定义路由
                String routeKey="com.order.user.xxxx";
                //定义交换机的类型
                String type="topic";
                //此处没有绑定交换机 因为在图形化界面已经设置好了


                /**
                 * @Param1 队列的名称
                 * @Param1 是否要持久化
                 * @Param1  队列
                 * @Param1 排他性
                 * @Param1 是否自动删除，随着最后一个消费者完毕是否删除队列
                 * @Param1 携带属性参数
                 */
                //准备消息
                channel.basicPublish(exchangeName,routeKey,null,message.getBytes());
                System.out.println("消息发送成功");
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
