package application.services;

import java.util.ArrayList;

public enum Sorts {
    ;

    public static ArrayList<Object[]> alphabeticalTableModelBubbleSort(ArrayList<Object[]> CDR2dArray, int arrIndex)
    {
        int sizeOuter = CDR2dArray.size();
        // loops through for each object array in the arrayList
        for(int i = 0 ; i < sizeOuter; i++)
        {
            int sizeInner = CDR2dArray.size();
            // loops though each object array in the array list i's
            // starting point on each loop is 1 more than j.
            for (int j = 1; j < sizeInner; j++)
            {

                if (0 > CDR2dArray.get(j)[arrIndex].toString().compareToIgnoreCase(
                        CDR2dArray.get(j - 1)[arrIndex].toString()))
                {

                    Object[] Titles = CDR2dArray.get( j - 1 );
                    CDR2dArray.set( j - 1 , CDR2dArray.get( j ));
                    CDR2dArray.set(  j  , Titles);
                }
            }
        }
        return CDR2dArray;
    }

    public static ArrayList<Object[]> ascendingTableModelInsertionSort(ArrayList<Object[]> objectArrays, int index)
    { // the number of items sorted so far
        Object[] key;
        int pointer;
        int size = objectArrays.size();
        for (int j = 1; j < size; j++) // Start with 1 (not 0)
        {
            key = objectArrays.get(j);
            for(pointer = j - 1 ;
                (0 <= pointer) && (0 < objectArrays.get(pointer)[index].toString().compareToIgnoreCase(key[index].toString()));
                pointer--) // Smaller values are moving up
            {
//                System.out.println(num[i+1] + " " +num[i]);
                objectArrays.set(pointer + 1,  objectArrays.get(pointer));
            }
//            System.out.println(num[i+1] + " " +key + "--");
            objectArrays.set( pointer+1, key); // Put the key in its proper location
        }
        return objectArrays;
    }

    public static ArrayList<Object[]> numericalAscendingMergeSort(ArrayList<Object[]> objectArrays , int index)
    {
        //Split the ArrayList into two parts until there is only one Object[] in each part.
        int arrayLength = objectArrays.size();

        if (2 > objectArrays.size())
        {
            return objectArrays;
        }

        int middle = arrayLength / 2;
        ArrayList<Object[]> splitListA = new ArrayList<>();
        ArrayList<Object[]> splitListB = new ArrayList<>();

        for (int i = 0; i < middle; i++)
        {
            splitListA.add(objectArrays.get(i));
        }
        for (int i = middle; i < arrayLength; i++)
        {
            splitListB.add(objectArrays.get(i));
        }


        splitListA = Sorts.numericalAscendingMergeSort(splitListA ,index);
        splitListB = Sorts.numericalAscendingMergeSort(splitListB, index);

        ArrayList<Object[]> sortedArrayList = new ArrayList<>();

        int splitListALength = splitListA.size();
        int splitListBLength = splitListB.size();

        int i = 0; // for pointing at splitListA
        int j = 0; // for pointing at splitListB
        final int k = 0; // for pointing at the merged array

        // compares the two lists objects and adds them to t
        while (i < splitListALength && j < splitListBLength)
        {
            if (0 <= splitListA.get(i)[index].toString().compareToIgnoreCase(
                    splitListB.get(j)[index].toString()))
            {
                sortedArrayList.add(splitListA.get(i));
                i++;
            }
            else
            {
                sortedArrayList.add(splitListB.get(j));
                j++;
            }
        }

        while ( i < splitListALength)
        {
            sortedArrayList.add(splitListA.get(i));
            i++;

        }

        while ( j < splitListBLength)
        {
            sortedArrayList.add(splitListB.get(j));
            j++;
        }
        return sortedArrayList;

    }


}
