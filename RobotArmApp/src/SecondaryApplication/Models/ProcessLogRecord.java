package SecondaryApplication.Models;

import java.time.LocalDate;
import java.time.LocalTime;

public class ProcessLogRecord {
    public ProcessLogRecord()
    {
        Action = "action";
        CommunicationType = "communicationType";
        Barcode = "barcode";
    }

    public ProcessLogRecord(String communicationType, String action, String barcode)
    {
        Action = action;
        CommunicationType = communicationType;
        Barcode = barcode;
    }

    private final LocalTime Time = LocalTime.now();
    public LocalTime getTime() {
        return Time;
    }


    private final LocalDate Date = LocalDate.now();
    public LocalDate getDate() {
        return Date;
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
        return Time + " - " +
                Date + " - " +
                CommunicationType + " - " +
                Action + " - " +
                Barcode;
    }
}
