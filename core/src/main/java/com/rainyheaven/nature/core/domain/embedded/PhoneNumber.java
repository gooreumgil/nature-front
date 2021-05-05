package com.rainyheaven.nature.core.domain.embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumber {

    private String phoneNum1;
    private String phoneNum2;
    private String phoneNum3;

    public static PhoneNumber create(String number) {
        PhoneNumber phoneNumber = new PhoneNumber();
        String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
        String replaceNum = number.replaceAll(regEx, "$1-$2-$3");

        int idx1 = replaceNum.indexOf("-");
        int idx2 = replaceNum.indexOf("-", 4);

        phoneNumber.phoneNum1 = replaceNum.substring(0, idx1);
        phoneNumber.phoneNum2 = replaceNum.substring(4, idx2);
        phoneNumber.phoneNum3 = replaceNum.substring(idx2 + 1);

        return phoneNumber;
    }

}
