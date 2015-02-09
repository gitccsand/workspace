package ds.activeMQ;

import javax.jms.Destination;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
/**
 * ��Ϣ���շ�
 */
public class MessageReciver{
    public static void main(String args[]) throws Exception {
        String[] configLocations = new String[] {"applicationContext.xml"};
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocations);

        JmsTemplate jmsTemplate = (JmsTemplate) context.getBean("jmsTemplate");
        Destination destination = (Destination) context.getBean("destination");

        TextMessage msg = null;
        //�Ƿ����������Ϣ
        boolean isContinue = true;
        while (isContinue) {
            msg = (TextMessage) jmsTemplate.receive(destination);
            System.out.println("�յ���Ϣ :" + msg.getText());
            if (msg.getText().equals("end")) {
                isContinue = false;
                System.out.println("�յ��˳���Ϣ������Ҫ�˳���");
            }
        }
        System.out.println("�����˳��ˣ�");
    }
}