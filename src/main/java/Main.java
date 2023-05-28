import io.undertow.Undertow;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.util.PortProvider;

public class Main {

    public static void main(String[] args) {
        startWebServer();
    }

    /**
     * Запускает web-сервер. По окончании работы требуется ручная остановка процесса.
     */
    private static void startWebServer() {
        UndertowJaxrsServer server = new UndertowJaxrsServer().start();
        server.deploy(RestEasyServices.class);
        System.out.println("Сервер запущен: http://localhost:8081/");
    }
}
