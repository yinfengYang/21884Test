package com.yyf.hap.utils;

import org.springframework.beans.factory.FactoryBean;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yang on 2019/3/15.
 */
public class SquenceFactory implements FactoryBean<String> {

    private static long counter = 0;
    @Override
    public synchronized String getObject() throws Exception {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " + ";
        String sequ = new DecimalFormat("000000").format(counter ++);
        return date + sequ;
    }

    @Override
    public Class<?> getObjectType() {
        return String.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
    public static void reset() {
        SquenceFactory.counter = 0;
    }
}
