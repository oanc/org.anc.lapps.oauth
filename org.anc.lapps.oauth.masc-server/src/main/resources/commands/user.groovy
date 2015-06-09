import org.anc.lapps.oauth.database.User
import org.anc.lapps.oauth.database.UserDatabase
import org.anc.lapps.oauth.security.PasswordHash
import org.crsh.cli.Command
import org.crsh.cli.Option
import org.crsh.cli.Usage
import org.springframework.beans.factory.BeanFactory

@Usage("manage users")
class user {

    @Command
    @Usage("list all users")
    void list() {
        Iterable<User> users = getDatabase().findAll()
        int count = 0
        for (User user : users) {
            ++count
            out.println user.name
        }
        if (count == 0) {
            out.println "No users have been registered"
        }
    }

    @Command
    @Usage("add a user")
    void add(@Usage("the userName of the user")
             @Option(names = ['u','user'])
                     String name,
             @Usage("the user's password")
             @Option(names=['p','password'])
                     String password) {
        if (name == null) {
            out.println "No user userName provided."
            return
        }
        if (password == null) {
            out.println "No password provided."
            return
        }
        UserDatabase database = getDatabase()

        User user = database.findByName(name)
        if (user != null) {
            out.println "A user with that userName already exists."
            return
        }
        user = new User(name, PasswordHash.hash(password))
        database.save(user)
        out.println "User ${name} created."
    }

    @Command
    @Usage("deletes a user from the system")
    void delete(
            @Usage("the user to be deleted")
            @Option(names=['u','user'])
                    String name) {
        if (name == null) {
            out.println "No user userName provided."
            return
        }
        UserDatabase database = getDatabase()
        User user = database.findByName(name)
        if (user == null) {
            out.println "No such user."
            return
        }
        database.delete(user)
        out.println "User ${name} deleted."
    }

    UserDatabase getDatabase() {
//        return getBean(UserDatabase)
        return getBean(UserDatabase)
    }

    public <T> T getBean(Class<T> beanClass) {
        BeanFactory factory = context.attributes.get("spring.beanfactory")
        if (factory == null) {
            throw new RuntimeException("Unable to get the BeanFactory instance.")
        }
        T bean = factory.getBean(beanClass)
        if (bean == null) {
            throw new RuntimeException("Unable to create bean.")
        }
        return bean
    }

}
