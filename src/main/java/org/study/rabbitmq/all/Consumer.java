package org.study.rabbitmq.all;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
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

            channel.basicConsume("queue2",true,new DeliverCallback(){
                @Override
                public void handle(String consumerTag, Delivery message) throws IOException {
                    System.out.println("收到消息是"+new String(message.getBody(),"UTF-8"));
                }
            },new CancelCallback(){
                @Override
                public void handle(String s) throws IOException {
                    System.out.println("接受失败了");
                }
            });
            System.out.println("开始接受消息");
            System.in.read();
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