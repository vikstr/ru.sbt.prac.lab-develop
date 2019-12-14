package ru.sberbank.dao.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Table(name="Rate")
public class Rate {

    private final static DateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Double rate;
    private String date;

    protected Rate() {}

    public Rate(Double rate, Date date) {
        this.rate = rate;
        this.date = dateFormater.format(date);
    }

    public static String dateFormat(Date date) {
        return dateFormater.format(date);
    }

    public Double getRate() {
        return rate;
    }
}
