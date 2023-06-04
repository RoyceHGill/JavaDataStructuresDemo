package application.services;

import application.library.ShutdownInterceptor;
import application.screens.MainScreen;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SortsTest {
    Object[] testArray1 = {"ZMT","LFB"};
    Object[] testArray2 = {"ZMT","KRT"};
    Object[] testArray3 = {"ABT","MRG"};


    ArrayList<Object[]> testData = new ArrayList<>();

    SortsTest()
    {
        testData.add(testArray2);
        testData.add(testArray1);
        testData.add(testArray3);
    }

    @Test
    void bubbleSortTest() {
        int titleArrayIndex = 1;
        ArrayList<Object[]> sortedArrayListOfObjects = Sorts
                .alphabeticalTableModelBubbleSort(
                        testData, titleArrayIndex
                );
        assertEquals("KRT", sortedArrayListOfObjects.get(0)[1]);
    }

    @Test
    void insertionSortTest() {
        int titleArrayIndex = 1;
        ArrayList<Object[]> sortedArrayListOfObjects = Sorts
                .ascendingTableModelInsertionSort(
                        testData, titleArrayIndex
                );
        assertEquals("KRT", sortedArrayListOfObjects.get(0)[1]);
    }

    @Test
    void mergeSortTest() {
        int titleArrayIndex = 1;
        ArrayList<Object[]> sortedArrayListOfObjects = Sorts
                .numericalAscendingMergeSort(
                        testData, titleArrayIndex
                );
        assertEquals("MRG", sortedArrayListOfObjects.get(0)[1]);
    }
}