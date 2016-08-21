package cl.medipacs.pacs.cli.run;

import cl.medipacs.pacs.cli.service.DbService;
import java.io.PrintStream;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Sebastián Salazar Molina.
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PrintStream console = System.out;
        try {
            console.println("=== Iniciando Aplicación ===");
            ApplicationContext ctx = new ClassPathXmlApplicationContext("pacs-cli-context.xml");
            DbService dbService = ctx.getBean("dbService", DbService.class);

            List<String> lists = dbService.getSeriesAttributes();

            console.println("Se ha terminado la ejecución de la aplicación");
        } catch (Exception e) {
            console.println(String.format("Error al cargar aplicación: %s", e.toString()));
            e.printStackTrace(console);
        }
    }
}
