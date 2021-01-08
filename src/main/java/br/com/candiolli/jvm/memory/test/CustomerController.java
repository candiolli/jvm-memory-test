package br.com.candiolli.jvm.memory.test;

import br.com.candiolli.jvm.memory.test.domain.entity.Customer;
import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/customer")
public class CustomerController {

    //10000000 para OutOfMemory
    private static final int COUNT_CUSTOMERS = 1000000;

    @GetMapping(path = "/import")
    public ResponseEntity importCustomers() {
        var listCustomers = new ArrayList();
        for (int i = 0; i < COUNT_CUSTOMERS; i++) {
            listCustomers.add(
                    Customer.builder()
                            .id(UUID.randomUUID())
                            .rg(new Random().nextInt())
                            .name("Usuario Teste " + i)
                            .cpf(String.valueOf(new Random().nextInt()))
                            .build()
            );
        }

        String s = new Gson().toJson(listCustomers);
        Path jsonFile = Paths.get("customers"+
                UUID.randomUUID().toString()
                +".json");
        try {
            BufferedWriter writer =
                    Files.newBufferedWriter(jsonFile, StandardCharsets.UTF_8,
                            StandardOpenOption.CREATE_NEW);
            writer.write(s);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Reader reader = Files.newBufferedReader(jsonFile);
            List<Customer> customers = new Gson().fromJson(reader, List.class);
            int e = 0;
            while (customers.size() < 999999) {
                customers.remove(e++);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("Finalizou programa");
    }

}
