package com.hotel.config.sapConnection;

import static org.junit.jupiter.api.Assertions.*;

class SAPConnUtilsTest {

    public static void main(String[] args) {

        SapConn con = new SapConn(
                "192.168.16.238",
                "00",
                "800",
                "HSIT-lucky",
                "lucky987",
                "zh",
                "200",
                "200",
                ""
        );

        // 测试连接
//        SAPConnUtils.connect(con,"ag");
        SAPConnUtils.connect(con,"customer");
    }
}