package entity;

import entity.Message;
import entity.Publisher;
import entity.Subscriber;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-05T16:50:20")
@StaticMetamodel(Topic.class)
public class Topic_ { 

    public static volatile CollectionAttribute<Topic, Message> messageCollection;
    public static volatile SingularAttribute<Topic, String> name;
    public static volatile CollectionAttribute<Topic, Subscriber> subscriberCollection;
    public static volatile SingularAttribute<Topic, Integer> id;
    public static volatile CollectionAttribute<Topic, Publisher> publisherCollection;

}