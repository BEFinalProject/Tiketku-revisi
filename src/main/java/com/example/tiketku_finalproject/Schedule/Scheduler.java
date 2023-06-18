//package com.example.tiketku_finalproject.Schedule;
//
//import com.example.tiketku_finalproject.Service.SchedulesService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.sql.Date;
//import java.sql.Time;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//
//@Component
//@Slf4j
//public class Scheduler {
//    @Autowired
//    SchedulesService schedulesService;
//    @Scheduled(fixedDelay = 1000, initialDelay = 2000)
//    public void Schedule() throws ParseException{
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//        String resetData = "17:14:20";
//        //String date2String = "00:00:00";
//        Time currentTime = new Time(System.currentTimeMillis());
//        System.out.println(currentTime);
//
//        try {
//            java.util.Date reset = sdf.parse(resetData);
//            java.util.Date current = sdf.parse(String.valueOf(currentTime));
//
//            // Mengubah tipe data menjadi java.sql.Date
//            Date resetDate = new Date(reset.getTime());
//            Date currentDate = new Date(current.getTime());
//            //date 1 = reset
//            //date 2 = current
//
//            // Membandingkan tanggal menggunakan metode compareTo()
//            int result = resetDate.compareTo(currentDate);
//            if (result < 0) {
//                System.out.println("resetDate is before currentDate");
//                boolean isBefore = resetDate.before(currentDate);
//                System.out.println("Is resetDate before currentDate? " + isBefore);
//
//            } else if (result > 0) {
//                System.out.println("resetDate is after currentDate");
//                boolean isAfter = resetDate.after(currentDate);
//                System.out.println("Is resetDate after currentDate? " + isAfter);
//
//            } else {
//                System.out.println("resetDate is equal to currentDate");
//                boolean isEqual = resetDate.equals(currentDate);
//                System.out.println("Is resetDate equal to currentDate? " + isEqual);
//                schedulesService.resetLimits();
//
//            }
//
//            // Membandingkan tanggal menggunakan operasi boolean
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//}
