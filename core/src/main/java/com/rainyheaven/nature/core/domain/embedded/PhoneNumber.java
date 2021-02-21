package com.rainyheaven.nature.core.domain.embedded;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class PhoneNumber {

    private Integer phoneNum1;
    private Integer phoneNum2;
    private Integer phoneNum3;

}
