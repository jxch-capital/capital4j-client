package org.jxch.capital.client.db.pg.base;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class IdBasePo extends BasePo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
