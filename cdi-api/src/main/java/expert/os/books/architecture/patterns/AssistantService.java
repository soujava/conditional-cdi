package expert.os.books.architecture.patterns;

import dev.langchain4j.cdi.spi.RegisterAIService;
import jakarta.enterprise.context.ApplicationScoped;

@RegisterAIService
@ApplicationScoped
public interface AssistantService {

    String chat(String userMessage);
}