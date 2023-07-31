package org.rgo;
import javax.enterprise.context.ApplicationScoped;
import com.fasterxml.jackson.databind.JsonNode;

@ApplicationScoped
public class ServiceImpl {
    
    public void manipulate(JsonNode node) {
        System.out.println("foor bar " + node.size());
      
    }
}
