package com.rainyheaven.nature.core.domain.embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.Calendar;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BirthDay {

    private Integer year;
    private Integer month;
    private Integer day;

    public static BirthDay create(Calendar calendar) {
        BirthDay birthDay = new BirthDay();
        birthDay.year = calendar.get(Calendar.YEAR);
        birthDay.month = calendar.get(Calendar.MONTH) + 1;
        birthDay.day = calendar.get(Calendar.DAY_OF_MONTH);
        return birthDay;
    }

}
