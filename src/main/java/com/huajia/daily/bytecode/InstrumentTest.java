package com.huajia.daily.bytecode;

//import com.huajia.common.Spy;

import java.util.ArrayList;
import java.util.List;

/*
 * @description:
 * @author: huajia
 * @create: 2020-11-17 11:20
 **/
public class InstrumentTest {
//    @Spy
    public static void test() throws Exception{
        Thread.sleep(1000);
    }
    public static void main(String[] args) throws Exception {
        System.out.println("start");
        test();
    }
}
