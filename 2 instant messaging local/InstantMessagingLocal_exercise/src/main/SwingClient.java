package main;

import util.Topic;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import publisher.Publisher;
import subscriber.Subscriber;
import subscriber.SubscriberImpl;
import topicmanager.TopicManager;
import util.Message;
import util.Subscription_check;
import util.Subscription_check.Result;

public class SwingClient {

    TopicManager topicManager;
    public Map<Topic, Subscriber> my_subscriptions;
    Publisher publisher;
    Topic publisherTopic;

    JFrame frame;
    JTextArea topic_list_TextArea;
    public JTextArea messages_TextArea;
    public JTextArea my_subscriptions_TextArea;
    JTextArea publisher_TextArea;
    JTextField argument_TextField;

    public SwingClient(TopicManager topicManager) {
        this.topicManager = topicManager;
        my_subscriptions = new HashMap<Topic, Subscriber>();
        publisher = null;
        publisherTopic = null;
    }

    public void createAndShowGUI() {

        frame = new JFrame("Publisher/Subscriber demo");
        frame.setSize(300, 300);
        frame.addWindowListener(new CloseWindowHandler());

        topic_list_TextArea = new JTextArea(5, 10);
        messages_TextArea = new JTextArea(10, 20);
        my_subscriptions_TextArea = new JTextArea(5, 10);
        publisher_TextArea = new JTextArea(1, 10);
        argument_TextField = new JTextField(20);

        JButton show_topics_button = new JButton("show Topics");
        JButton new_publisher_button = new JButton("new Publisher");
        JButton new_subscriber_button = new JButton("new Subscriber");
        JButton to_unsubscribe_button = new JButton("to unsubscribe");
        JButton to_post_an_event_button = new JButton("post an event");
        JButton to_close_the_app = new JButton("close app.");

        show_topics_button.addActionListener(new showTopicsHandler());
        new_publisher_button.addActionListener(new newPublisherHandler());
        new_subscriber_button.addActionListener(new newSubscriberHandler());
        to_unsubscribe_button.addActionListener(new UnsubscribeHandler());
        to_post_an_event_button.addActionListener(new postEventHandler());
        to_close_the_app.addActionListener(new CloseAppHandler());

        JPanel buttonsPannel = new JPanel(new FlowLayout());
        buttonsPannel.add(show_topics_button);
        buttonsPannel.add(new_publisher_button);
        buttonsPannel.add(new_subscriber_button);
        buttonsPannel.add(to_unsubscribe_button);
        buttonsPannel.add(to_post_an_event_button);
        buttonsPannel.add(to_close_the_app);

        JPanel argumentP = new JPanel(new FlowLayout());
        argumentP.add(new JLabel("Write content to set a new_publisher / new_subscriber / unsubscribe / post_event:"));
        argumentP.add(argument_TextField);

        JPanel topicsP = new JPanel();
        topicsP.setLayout(new BoxLayout(topicsP, BoxLayout.PAGE_AXIS));
        topicsP.add(new JLabel("Topics:"));
        topicsP.add(topic_list_TextArea);
        topicsP.add(new JScrollPane(topic_list_TextArea));
        topicsP.add(new JLabel("My Subscriptions:"));
        topicsP.add(my_subscriptions_TextArea);
        topicsP.add(new JScrollPane(my_subscriptions_TextArea));
        topicsP.add(new JLabel("I'm Publisher of topic:"));
        topicsP.add(publisher_TextArea);
        topicsP.add(new JScrollPane(publisher_TextArea));

        JPanel messagesPanel = new JPanel();
        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.PAGE_AXIS));
        messagesPanel.add(new JLabel("Messages:"));
        messagesPanel.add(messages_TextArea);
        messagesPanel.add(new JScrollPane(messages_TextArea));

        Container mainPanel = frame.getContentPane();
        mainPanel.add(buttonsPannel, BorderLayout.PAGE_START);
        mainPanel.add(messagesPanel, BorderLayout.CENTER);
        mainPanel.add(argumentP, BorderLayout.PAGE_END);
        mainPanel.add(topicsP, BorderLayout.LINE_START);

        frame.pack();
        frame.setVisible(true);
    }

    class showTopicsHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Topic> topics = topicManager.topics();
            if (topics.isEmpty()) {
                return;
            }
            Boolean first = true;
            String topicList = "";
            for (Topic topic : topics) {
                if (first) {
                    first = false;
                    topicList += (topic.name);
                } else {
                    topicList += ("\n" + topic.name);
                }
            }

            topic_list_TextArea.setText(topicList);
        }
    }

    class newPublisherHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (publisher != null) {
                topicManager.removePublisherFromTopic(publisherTopic);
                publisher_TextArea.setText(argument_TextField.getText());
            }

            List<Topic> topics = topicManager.topics();
            for (Topic topic : topics) {
                if (topic.name.equals(argument_TextField.getText())) {
                    publisher = topicManager.addPublisherToTopic(topic);
                    publisherTopic = topic;
                    return;
                }
            }
            publisherTopic = new Topic(argument_TextField.getText());
            publisher = topicManager.addPublisherToTopic(publisherTopic);
            publisher_TextArea.setText(argument_TextField.getText());
            // IMPROVEMENT: When you become a publisher, you subscrive to the topic automatically.
            // It doen't make sense to be a publisher if you can't see your own messages.
            new newSubscriberHandler().actionPerformed(e);
        }
    }

    class newSubscriberHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Topic topic = new Topic(argument_TextField.getText());
            if (my_subscriptions.get(topic) != null) {
                return; // If you are already subscribed doesn't conrinue
            }
            Subscriber subscriber = new SubscriberImpl(SwingClient.this);
            Subscription_check sub_check = topicManager.subscribe(topic, subscriber);
            if (sub_check.result.equals(Result.OKAY)) {
                my_subscriptions.put(topic, subscriber);
                if (my_subscriptions_TextArea.getText().trim().equals("")) {
                    my_subscriptions_TextArea.append(topic.name);
                } else {
                    my_subscriptions_TextArea.append("\n" + topic.name);

                }
            }
        }
    }

    class UnsubscribeHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Topic topic = new Topic(argument_TextField.getText());
            Subscriber subscriber = new SubscriberImpl(SwingClient.this);
            Subscription_check sub_check = topicManager.unsubscribe(topic, subscriber);
            switch (sub_check.result) {
                case NO_TOPIC: // TODO: Alerts for typo?
                    break;
                case NO_SUBSCRIPTION:
                    break;
                default: // OKAY case
                    Boolean isFirst = true;
                    String topicList = "";
                    my_subscriptions.remove(topic);
                    if (my_subscriptions.isEmpty()) {
                        my_subscriptions_TextArea.setText("");
                        break;
                    }
                    for (Topic t : my_subscriptions.keySet()) {
                        System.out.println(t.name);
                        if (isFirst) {
                            isFirst = false;
                            topicList += (t.name);
                        } else {
                            topicList += ("\n" + t.name);
                        }
                    }
                    my_subscriptions_TextArea.setText(topicList);
            }
        }
    }

    class postEventHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Topic topic = new Topic(publisher_TextArea.getText());
            Message message = new Message(topic, argument_TextField.getText());
            publisher.publish(message);
        }
    }

    class CloseAppHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("all users closed");
            System.exit(0);
        }
    }

    class CloseWindowHandler implements WindowListener {

        @Override
        public void windowDeactivated(WindowEvent e) {
        }

        @Override
        public void windowActivated(WindowEvent e) {
        }

        @Override
        public void windowIconified(WindowEvent e) {
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
        }

        @Override
        public void windowClosed(WindowEvent e) {
        }

        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosing(WindowEvent e) {

            if (publisher != null) {
                topicManager.removePublisherFromTopic(publisherTopic);
            }
            if (!my_subscriptions.isEmpty()) {
                Subscriber subscriber = new SubscriberImpl(SwingClient.this);
                for (Topic topic : my_subscriptions.keySet()) {
                    topicManager.unsubscribe(topic, subscriber);
                }
            }

            System.out.println("one user closed");
        }
    }
}
