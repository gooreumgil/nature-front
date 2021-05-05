package com.rainyheaven.nature.core.domain.embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderAddress {

    private String main;
    private String detail;
    private String zipCode;

}
