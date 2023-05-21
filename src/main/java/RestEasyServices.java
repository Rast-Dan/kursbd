import exceptions.MyExceptionMapper;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("")
public class RestEasyServices extends Application {
    private final Set<Object> singletons = new HashSet<Object>();

    public RestEasyServices() {
        singletons.add(new Api());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(MyExceptionMapper.class);
        return classes;

    }
}