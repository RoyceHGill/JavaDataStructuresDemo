package SecondaryApplication.Services;

import SecondaryApplication.Models.CDRModel;

import java.util.ArrayList;

public abstract class Sorts {

    public static ArrayList<Object[]> alphabeticalTableModelBubbleSort(ArrayList<Object[]> CDR2dArray, int arrIndex)
    {
        // loops through for each object array in the arrayList
        for(int i = 0 ; i < CDR2dArray.size(); i++)
        {
            // loops though each object array in the array list i's
            // starting point on each loop is 1 more than j.
            for (int j = 1; j < CDR2dArray.size(); j++)
            {

                if (CDR2dArray.get( j )[ arrIndex ].toString().compareToIgnoreCase(
                        CDR2dArray.get( j - 1 )[ arrIndex ].toString()) < 0 )
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
    {
        Object[] key;
        int pointer;
        for (int j = 1; j < objectArrays.size(); j++)
        {
            key = objectArrays.get(j);
            for(pointer = j - 1 ;
                (pointer >= 0) && (objectArrays.get(pointer)[index].toString().compareToIgnoreCase(key[index].toString()) > 0);
                pointer--)
            {
                objectArrays.set(pointer + 1,  objectArrays.get(pointer));
            }
            objectArrays.set( pointer+1, key);
        }
        return objectArrays;
    }

    public static ArrayList<Object[]> alphabeticalObjectArrayMergeSort(ArrayList<Object[]> objectArrays , int index)
    {
        int arrayLength = objectArrays.size();

        if (objectArrays.size() < 2)
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


        splitListA = alphabeticalObjectArrayMergeSort(splitListA ,index);
        splitListB = alphabeticalObjectArrayMergeSort(splitListB, index);

        ArrayList<Object[]> sortedArrayList = new ArrayList<>();

        int splitListALength = splitListA.size();
        int splitListBLength = splitListB.size();

        int i = 0;
        int j = 0;
        int k = 0;

        while (i < splitListALength && j < splitListBLength)
        {
            if (splitListA.get(i)[index].toString().compareToIgnoreCase(
                    splitListB.get(j)[index].toString())  >= 0)
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

    public static ArrayList<CDRModel> reverseMergeSortByTitle(ArrayList<CDRModel> objectArrays)
    {
        int arrayLength = objectArrays.size();

        if (objectArrays.size() < 2)
        {
            return objectArrays;
        }

        int middle = arrayLength / 2;
        ArrayList<CDRModel> splitListA = new ArrayList<>();
        ArrayList<CDRModel> splitListB = new ArrayList<>();

        for (int i = 0; i < middle; i++)
        {
            splitListA.add(objectArrays.get(i));
        }
        for (int i = middle; i < arrayLength; i++)
        {
            splitListB.add(objectArrays.get(i));
        }


        splitListA = reverseMergeSortByTitle(splitListA);
        splitListB = reverseMergeSortByTitle(splitListB);

        ArrayList<CDRModel> sortedArrayList = new ArrayList<>();

        int splitListALength = splitListA.size();
        int splitListBLength = splitListB.size();

        int i = 0;
        int j = 0;
        int k = 0;

        while (i < splitListALength && j < splitListBLength)
        {
            if (splitListA.get(i).getTitle().compareToIgnoreCase(
                    splitListB.get(j).getTitle())  >= 0)
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

    public static ArrayList<CDRModel> mergeSortByTitle(ArrayList<CDRModel> objectArrays)
    {
        int arrayLength = objectArrays.size();

        if (objectArrays.size() < 2)
        {
            return objectArrays;
        }

        int middle = arrayLength / 2;
        ArrayList<CDRModel> splitListA = new ArrayList<>();
        ArrayList<CDRModel> splitListB = new ArrayList<>();

        for (int i = 0; i < middle; i++)
        {
            splitListA.add(objectArrays.get(i));
        }
        for (int i = middle; i < arrayLength; i++)
        {
            splitListB.add(objectArrays.get(i));
        }


        splitListA = mergeSortByTitle(splitListA);
        splitListB = mergeSortByTitle(splitListB);

        ArrayList<CDRModel> sortedArrayList = new ArrayList<>();

        int splitListALength = splitListA.size();
        int splitListBLength = splitListB.size();

        int i = 0;
        int j = 0;
        int k = 0;

        while (i < splitListALength && j < splitListBLength)
        {
            if (splitListA.get(i).getTitle().compareToIgnoreCase(
                    splitListB.get(j).getTitle())  <= 0)
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
