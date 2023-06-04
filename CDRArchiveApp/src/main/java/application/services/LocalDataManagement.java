//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package application.services;

import application.models.CDRModel;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public abstract class LocalDataManagement {
    public static Comparator<CDRModel> TitleCompare = new Comparator<CDRModel>() {
        public int compare(CDRModel cdr1, CDRModel cdr2) {
            return cdr1.getTitle().compareTo(cdr2.getTitle());
        }
    };

    private static final String sampleDataFileName = Configuration.sampleDataFileName;


    protected LocalDataManagement() {
    }

    public static void writeHashMapToFile(HashMap<String, String> hashMap){
        final String hashMapFileName = "CD_ArchivePrototype_Hashmap.txt";
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(hashMapFileName, StandardCharsets.UTF_8));
            printWriter.append(String.valueOf(hashMap));
            printWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public static void writeProcessesLog(ProcessLogRecord log){
//        int lineCount = 0;
//        try {
//            StringBuilder logText = new StringBuilder();
//            PrintWriter printWriter = new PrintWriter(new FileWriter(processLogFileName,true));
//            logText.append(log.toString());
//            printWriter.append(logText.toString());
//            printWriter.append("\n");
//            printWriter.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public static DoublyLinkedList readProcessLog(){
//        DoublyLinkedList log = new DoublyLinkedList();
//        try {
//            FileInputStream fileInputStream = new FileInputStream(processLogFileName);
//            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
//            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
//            int lineCount = 0;
//
//
//            for (String line = ""; (line = bufferedReader.readLine()) != null && !line.isEmpty() && !line.trim().isEmpty(); ++lineCount) {
//                String[] splitLine = line.split("-");
//                int i = 0;
//                for (String string: splitLine
//                     ) {
//
//                    splitLine[i++] = string.trim();
//                }
//                ProcessLogRecord event = new ProcessLogRecord(LocalDateTime.parse(splitLine[0], dateTimeFormatter), splitLine[1], splitLine[2],splitLine[3]);
//                log.append(event);
//
//            }
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return log;
//    }

    public static void writeCDRStorage(ArrayList<CDRModel> cdrModels, String[] headers){
        int lineCount = 0;
        try {
            StringBuilder stringBuilder = new StringBuilder();
            PrintWriter printWriter = new PrintWriter(new FileWriter(LocalDataManagement.sampleDataFileName, StandardCharsets.UTF_8));
            for (String header: headers
                 ) {
                if (0 == lineCount)
                {
                    stringBuilder.append(header);
                }
                else {
                    stringBuilder.append(";");
                    stringBuilder.append(header);
                }
                lineCount++;

            }
            printWriter.println(stringBuilder);
            lineCount = 0;

            for (CDRModel CDR: cdrModels
                 ) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(CDR.getId());
                stringBuilder.append(";");
                stringBuilder.append(CDR.getTitle());
                stringBuilder.append(";");
                stringBuilder.append(CDR.getAuthor());
                stringBuilder.append(";");
                stringBuilder.append(CDR.getSection());
                stringBuilder.append(";");
                stringBuilder.append(CDR.getXAxis());
                stringBuilder.append(";");
                stringBuilder.append(CDR.getYAxis());
                stringBuilder.append(";");
                stringBuilder.append(CDR.getBarCode());
                stringBuilder.append(";");
                stringBuilder.append(CDR.getDescription());
                stringBuilder.append(";");
                stringBuilder.append(LocalDataManagement.boolToYesNo(CDR.getOnLoan()));
                printWriter.println(stringBuilder);
            }
            printWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<CDRModel> readCDRStorage() {

        ArrayList<CDRModel> cdrModels = new ArrayList();
        try {
            FileInputStream fileInputStream = new FileInputStream(LocalDataManagement.sampleDataFileName);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream, StandardCharsets.UTF_8));
            int lineCount = 0;

            for(String line = ""; null != (line = bufferedReader.readLine()) && !line.isEmpty() && !line.trim().isEmpty(); ++lineCount) {
                String[] values = line.split(";");
                if (0 != lineCount) {
                    cdrModels.add(new CDRModel(Integer.parseInt(values[0]), values[1], values[2], values[3], Integer.parseInt(values[4]), Integer.parseInt(values[5]), values[6], values[7], LocalDataManagement.yesNoToBool(values[8])));
                }
            }

            return cdrModels;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to load data: " + e);

            return new ArrayList<CDRModel>();
        }
    }

    public static String[] getTableHeaders() {
        try {
            FileInputStream fileInputStream = new FileInputStream(LocalDataManagement.sampleDataFileName);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream, StandardCharsets.UTF_8));
            String[] headers = bufferedReader.readLine().split(";");
            return headers;
        } catch (IOException var5) {
            throw new RuntimeException(var5);
        }
    }

    private static boolean yesNoToBool(String yesOrNo) {
        return "yes".equalsIgnoreCase(yesOrNo);
    }
    private static String boolToYesNo(boolean trueOrFalse) {
        if (trueOrFalse)
        {
            return "Yes";
        }
        else
        {
            return "No";
        }

    }

    public static ArrayList<Object[]> modelsToObjectArrayList(ArrayList<CDRModel> cdrModels) {
        ArrayList<Object[]> cdrModels2DArray = new ArrayList();
        Iterator var2 = cdrModels.iterator();

        while(var2.hasNext()) {
            CDRModel obj = (CDRModel)var2.next();
            Collections.addAll(cdrModels2DArray, new Object[][]{obj.toArray()});
        }

        return cdrModels2DArray;
    }

    public static ArrayList<Object[]> searchByString(String searchText) {
        ArrayList<CDRModel> cdrList = LocalDataManagement.readCDRStorage();
        ArrayList<Object[]> cdrModelsAs2DArray = new ArrayList();
        Iterator var3 = cdrList.iterator();

        while(var3.hasNext()) {
            CDRModel model = (CDRModel)var3.next();
            Object[] modelAsArray;
            if (model.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                modelAsArray = model.toArray();
                cdrModelsAs2DArray.add(modelAsArray);
            } else if (model.getAuthor().toLowerCase().contains(searchText.toLowerCase())) {
                modelAsArray = model.toArray();
                cdrModelsAs2DArray.add(modelAsArray);
            } else if (model.getDescription().toLowerCase().contains(searchText.toLowerCase())) {
                modelAsArray = model.toArray();
                cdrModelsAs2DArray.add(modelAsArray);
            }
            else if ("".equals(searchText))
            {
                modelAsArray = model.toArray();
                cdrModelsAs2DArray.add(modelAsArray);
            }
        }

        return cdrModelsAs2DArray;
    }

    public static ArrayList<CDRModel> objectArrayListToModels(ArrayList<Object[]> ArrayOfObjects) {
        ArrayList<CDRModel> CDRModels =new ArrayList<>();
        for (Object[] objectArray: ArrayOfObjects
             ) {
            CDRModel cdrModel = new CDRModel(
                    (int)objectArray[0],
                    (String)objectArray[1],
                    (String)objectArray[2],
                    (String)objectArray[3],
                    (int)objectArray[4],
                    (int)objectArray[5],
                    (String)objectArray[6],
                    (String)objectArray[7],
                    (Boolean)objectArray[8]
            );
            CDRModels.add(cdrModel);
        }
        return CDRModels;
    }
}
