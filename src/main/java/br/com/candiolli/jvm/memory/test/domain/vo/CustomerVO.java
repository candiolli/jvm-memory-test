package br.com.candiolli.jvm.memory.test.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerVO {

    private UUID id;
    private String name;
    private String cpf;
    private Integer rg;

}
