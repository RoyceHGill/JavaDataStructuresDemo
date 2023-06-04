package application.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProcessLogRecord {
    public ProcessLogRecord()
    {
        Action = "action";
        CommunicationType = "communicationType";
        Barcode = "barcode";
    }

    public ProcessLogRecord(LocalDateTime date, String communicationType, String action, String barcode)
    {
        Date = date;
        Action = action;
        CommunicationType = communicationType;
        Barcode = barcode;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private LocalDateTime Date = LocalDateTime.now();
    public LocalDateTime getDate() {
        return Date;
    }
    public void setDate(LocalDateTime date) {
        Date = date;
    }


    private String CommunicationType;
    public String getCommunicationType() {
        return CommunicationType;
    }
    public void setCommunicationType(String communicationType) {
        CommunicationType = communicationType;
    }


    private String Action;
    public String getAction() {
        return Action;
    }
    public void setAction(String action) {
        Action = action;
    }


    private String Barcode;
    public String getBarcode() {
        return Barcode;
    }
    public void setBarcode(String barcode) {
        Barcode = barcode;
    }


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return Date.format(formatter) + " - " +
                CommunicationType + " - " +
                Action + " - " +
                Barcode;
    }
}
