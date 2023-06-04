//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package SecondaryApplication.Services;

import SecondaryApplication.Models.CDRModel;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public abstract class LocalDataManagement {
    public static Comparator<CDRModel> TitleCompare = new Comparator<CDRModel>() {
        public int compare(CDRModel cdr1, CDRModel cdr2) {
            return cdr1.getTitle().compareTo(cdr2.getTitle());
        }
    };

    private static final String fileName = Configuration.fileName;

    public LocalDataManagement() {
    }


    public static void writeFileData(ArrayList<CDRModel> cdrModels, String[] headers){
        int lineCount = 0;
        try {
            StringBuilder stringBuilder = new StringBuilder();
            PrintWriter printWriter = new PrintWriter(new FileWriter(fileName));
            for (String header: headers
                 ) {
                if (lineCount == 0)
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
                stringBuilder.append(boolToYesNo(CDR.getOnLoan()));
                printWriter.println(stringBuilder);
            }
            printWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<CDRModel> readFileData() {

        ArrayList<CDRModel> cdrModels = new ArrayList();
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
            int lineCount = 0;

            for(String line = ""; (line = bufferedReader.readLine()) != null && !line.isEmpty() && !line.trim().isEmpty(); ++lineCount) {
                String[] values = line.split(";");
                if (lineCount != 0) {
                    cdrModels.add(new CDRModel(Integer.parseInt(values[0]), values[1], values[2], values[3], Integer.parseInt(values[4]), Integer.parseInt(values[5]), values[6], values[7], yesNoToBool(values[8])));
                }
            }

            return cdrModels;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to load data." + e.getMessage());
            return new ArrayList<CDRModel>();
        }
    }

    public static String[] getTableHeaders() {

        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
            String[] headers = bufferedReader.readLine().split(";");
            return headers;
        } catch (IOException var5) {
            throw new RuntimeException(var5);
        }
    }

    private static boolean yesNoToBool(String yesOrNo) {
        return yesOrNo.equalsIgnoreCase("yes");
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
        ArrayList<CDRModel> cdrList = readFileData();
        ArrayList<Object[]> cdrModelsAs2DArray = new ArrayList();
        Iterator interator = cdrList.iterator();

        while(interator.hasNext()) {
            CDRModel model = (CDRModel)interator.next();
            Object[] modelAsArray;
            if (model.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                modelAsArray = model.toArray();
                cdrModelsAs2DArray.add(modelAsArray);
            }
            else if (model.getAuthor().toLowerCase().contains(searchText.toLowerCase())) {
                modelAsArray = model.toArray();
                cdrModelsAs2DArray.add(modelAsArray);
            } else if (model.getDescription().toLowerCase().contains(searchText.toLowerCase())) {
                modelAsArray = model.toArray();
                cdrModelsAs2DArray.add(modelAsArray);
            }
            else if (searchText.equals(""))
            {
                modelAsArray = model.toArray();
                cdrModelsAs2DArray.add(modelAsArray);
            }
        }

        return cdrModelsAs2DArray;
    }

    public static ArrayList<CDRModel> searchBySection(String section) {
        ArrayList<CDRModel> cdrList = readFileData();
        Iterator<CDRModel> iterator = cdrList.iterator();
        ArrayList<CDRModel> filteredArray = new ArrayList<>();

        while (iterator.hasNext()) {
            CDRModel model = iterator.next();
            if (model.getSection().contains(section)) {
                filteredArray.add(model);
            }


        }
        return filteredArray;
    }

    public static ArrayList<CDRModel> searchByX(ArrayList<CDRModel> cdrList, int x) {
        Iterator<CDRModel> iterator = cdrList.iterator();
        ArrayList<CDRModel> filteredArray = new ArrayList<>();

        while (iterator.hasNext()) {
            CDRModel model = iterator.next();
            if (model.getXAxis() == x) {
                filteredArray.add(model);
            }


        }
        return filteredArray;
    }

    public static ArrayList<CDRModel> searchByY(ArrayList<CDRModel> cdrList, int x) {
        Iterator<CDRModel> iterator = cdrList.iterator();
        ArrayList<CDRModel> filteredArray = new ArrayList<>();

        while (iterator.hasNext()) {
            CDRModel model = iterator.next();
            if (model.getYAxis() == x) {
                filteredArray.add(model);
            }


        }
        return filteredArray;
    }

    public static ArrayList<CDRModel> searchByBarcode(String barcode) {
        ArrayList<CDRModel> cdrList = readFileData();
        Iterator<CDRModel> iterator = cdrList.iterator();
        ArrayList<CDRModel> filteredArray = new ArrayList<>();

        while (iterator.hasNext()) {
            CDRModel model = iterator.next();
            if (model.getBarCode().equals(barcode)) {
                filteredArray.add(model);
            }


        }
        return filteredArray;
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
