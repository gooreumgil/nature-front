package com.rainyheaven.nature.core.domain.embedded;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class BirthDay {

    private Integer year;
    private Integer month;
    private Integer day;

}
