package mindhub.adstoreDetailing.controladores;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@RequestMapping("/api")
@Controller
public class ControladorExportadorPDF {
//    @PostMapping("/export-to-PDF-stream-output")
//    public void exportToPDF(@RequestBody TransactionFilterDTO filter, Authentication auth, HttpServletResponse response) throws ParseException, IOException {
//
//        Client currentClient = clientRepo.findByEmail(auth.getName());
//        Account currentAcc = accRepo.findByNumber(filter.getAccountNumber());
//
//        LocalDate fromDate = filter.getFromDate().toLocalDate();
//        LocalDate toDate = filter.getToDate().toLocalDate();
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String fromDateStr = fromDate.format(formatter);
//        String toDateStr = toDate.format(formatter);
//
//        LocalDateTime start = filter.getFromDate().withHour(0).withMinute(0).withSecond(0);
//        LocalDateTime end = filter.getToDate().withHour(23).withMinute(59).withSecond(59);
//
//        List<Transaction> transactions = transactionRepo.findByIdAndDateBetween(
//                currentAcc.getId(), start, end);
//
//        response.setContentType("application/pdf");
//
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=transactionsFor" + currentAcc.getId() +"from"+ fromDateStr+"to"+toDateStr+".pdf";
//
//        response.setHeader(headerKey, headerValue);
//
//        this.pdfExporterService.setTransactionList(transactions);
//        this.pdfExporterService.export(response, start, end);
//    }
}
