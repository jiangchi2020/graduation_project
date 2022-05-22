package com.scarike.gp.crawler;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.*;

public class IDGenerator {
    public static final IDGenerator instance=new IDGenerator();
    static {
        instance.temp=0b0_00000000000000000000000000000000000000000_1010_000000_000000000000L;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static final long TIME_2022_01_01_00_00_00=1640966400000L;
    public long time=0;
    public long s=1;
    public long temp;

    public long nextId(int code){
        long cur = System.currentTimeMillis();
        if (cur != time) {
            if(cur<time) {
                throw new RuntimeException("CLOCK BACK!");
            }
            else {
                s=1;
                time=cur;
            }
        }else {
            s++;
            if(s>0b0111111111111L){
                System.err.println("[warning] too much request in one ms,waiting....");
                while ((cur=System.currentTimeMillis())==time);
                s=1;
                time=cur;
            }
        }
        return temp|((time-TIME_2022_01_01_00_00_00)<<22)| (long) code <<12|s;
    }

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://1.14.105.203:5432/gp?useUnicode=true&characterEncoding=utf-8", "postgres", "747899");
        long id = -1;
        final int count = 1000;
        PrintStream out=new PrintStream(new FileOutputStream("gp-crawler/sql/id_100000.sql"));
        out.println("BEGIN;");
        String sql = "SELECT pid,type_code FROM poi_100000 WHERE pid>? ORDER BY pid LIMIT " + count;
        PreparedStatement ps = connection.prepareStatement(sql);
        int i;long sum=0;
        while (true) {
            i = 0;
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                i++;
                long pid = resultSet.getLong(1);
                int code = resultSet.getInt(2);
                code=code%10000/100;
                id = pid;
                long ppid=instance.nextId(code);
                out.println("UPDATE poi_100000 SET pid = "+ppid+" WHERE pid = "+pid+";");
                out.println("UPDATE poi_100000_photo SET pid = "+ppid+" WHERE pid = "+pid+";");
            }
            System.out.println("id=>"+id);
            sum+=i;
            if (i < count) {
                break;
            }
            resultSet.close();
        }
        out.println("COMMIT;");
        out.close();
        ps.close();
        connection.close();
        System.out.println("sum="+sum);
    }
}
