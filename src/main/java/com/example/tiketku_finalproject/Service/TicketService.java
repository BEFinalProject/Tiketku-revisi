package com.example.tiketku_finalproject.Service;

import com.example.tiketku_finalproject.Model.HistoryTransactionEntity;
import com.example.tiketku_finalproject.Repository.HistoryTransactionRepository;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.List;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TicketService {
    @Autowired
    HistoryTransactionRepository historyTransactionRepository;

    public String printReportbyUuidHistory(UUID uuid_history) throws FileNotFoundException, JRException {
        List<HistoryTransactionEntity> ticketEntity = historyTransactionRepository.findByUUIDHistory(uuid_history);
        String path = "C:\\Users\\ARJ\\Downloads";
        File file = ResourceUtils.getFile("classpath:TesJasper.jrxml");
        JasperReport jasperReport= JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ticketEntity);
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("createBy", "Kelompok B1 Final Project Binar Academy KM 4");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, path+"\\Ticket.pdf");

        return "Report generated in " + path;
    }
}
