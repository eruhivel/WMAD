package entity;

import entity.Publisher;
import entity.Subscriber;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-05T16:50:20")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, Date> birthday;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, String> surname;
    public static volatile SingularAttribute<User, String> name;
    public static volatile CollectionAttribute<User, Subscriber> subscriberCollection;
    public static volatile SingularAttribute<User, Integer> id;
    public static volatile SingularAttribute<User, String> login;
    public static volatile CollectionAttribute<User, Publisher> publisherCollection;
    public static volatile SingularAttribute<User, String> email;

}