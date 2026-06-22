package expert.os.books.architecture.patterns.books;

import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class BookApp {

    public static void main(String[] args) {

        try(SeContainer container = SeContainerInitializer.newInstance().initialize()) {

            var requestContextController = container.select(RequestContextController.class).get();
            requestContextController.activate();
            var service = container.select(NextReadBookService.class).get();
            var bookRequest = new BookRequest("Clean Code", "Robert C. Martin");
            var books = service.recommend(bookRequest);

           books.recommendations().forEach(recommendation -> {
               System.out.println(ConsoleColors.GREEN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + ConsoleColors.RESET);

               System.out.println(ConsoleColors.PURPLE + "📚 NEXT READ RECOMMENDATION" + ConsoleColors.RESET);

               System.out.println();

               System.out.println(ConsoleColors.BLUE + "Title: " + ConsoleColors.RESET + recommendation.book().title());

               System.out.println(ConsoleColors.BLUE + "Author: " + ConsoleColors.RESET + recommendation.book().author());

               System.out.println(ConsoleColors.YELLOW + "Keywords: " + ConsoleColors.RESET + String.join(", ", recommendation.book().keywords())
               );

               System.out.println();

               System.out.println(ConsoleColors.CYAN + "Description:" + ConsoleColors.RESET);

               System.out.println(recommendation.book().description());

               System.out.println();

               System.out.println(ConsoleColors.GREEN + "Why this book?" + ConsoleColors.RESET);

               System.out.println(recommendation.reason());

               System.out.println();

               System.out.println(ConsoleColors.GREEN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + ConsoleColors.RESET);

               System.out.println();
            });

            requestContextController.deactivate();
        }
    }
}
