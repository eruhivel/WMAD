package entity;

import entity.Topic;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-05T16:50:20")
@StaticMetamodel(Message.class)
public class Message_ { 

    public static volatile SingularAttribute<Message, Topic> topic;
    public static volatile SingularAttribute<Message, Integer> id;
    public static volatile SingularAttribute<Message, String> content;

}