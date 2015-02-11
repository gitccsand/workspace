package ds.activeMQ;

import javax.jms.Destination;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
/**
 * ������Ϣ��
 */
public class MessageSender extends Thread {
    public static void main(String args[]) throws Exception {
        String[] configLocations = new String[] {"applicationContext.xml"};
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocations);
        JmsTemplate jmsTemplate = (JmsTemplate) context.getBean("jmsTemplate");
        Destination destination = (Destination) context.getBean("destination");
        for (int i = 1; i < 100; i++) {
            System.out.println("���� i=" + i);
            //��Ϣ������
            MyMessageCreator myMessageCreator = new MyMessageCreator();
            myMessageCreator.n = i;
            jmsTemplate.send(destination, myMessageCreator);
            sleep(10000);//10�������һ����Ϣ
        }
    }
}