package application.library;

import application.models.CDRModel;

import java.util.ArrayList;

public class BinaryTree {

    Node root;

    public void addManyNodes(ArrayList<CDRModel> CDRs)
    {
        for (CDRModel CDR : CDRs
                ) {
            addNode(CDR);
        }
    }

    public void addNode(CDRModel CDR)
    {
        Node newNode = new Node(CDR);

        if (null == root) {
            root = newNode;
        } else {
            Node focusNode = root;

            Node parent;

            while (true){

                parent = focusNode;
                if (0 > newNode.CDR.getBarCode().compareToIgnoreCase(focusNode.CDR.getBarCode())) {
                    focusNode = focusNode.leftChild;

                    if (null == focusNode) {
                        parent.leftChild = newNode;

                        return;
                    }
                } else {
                    focusNode = focusNode.rightChild;

                    if (null == focusNode) {
                        parent.rightChild = newNode;

                        return;
                    }
                }
            }
        }
    }
    public ArrayList<CDRModel> getInOrderTraversalList()
    {
        ArrayList<CDRModel> CDRList = new ArrayList<>();

        CDRList = BinaryTree.buildInOrderTraversalList(CDRList, root);

        return CDRList;
    }

    public static ArrayList<CDRModel> buildInOrderTraversalList(ArrayList<CDRModel> list, Node focusNode)
    {
        if (null != focusNode)
        {
            BinaryTree.buildInOrderTraversalList(list, focusNode.leftChild);


            list.add(focusNode.CDR);

            BinaryTree.buildInOrderTraversalList(list, focusNode.rightChild);
        }
        return list;
    }
    public ArrayList<CDRModel> getPreOrderTraversalList()
    {
        ArrayList<CDRModel> CDRList = new ArrayList<>();

        CDRList = BinaryTree.buildPreOrderTraversalList(CDRList, root);

        return CDRList;
    }

    public static ArrayList<CDRModel> buildPreOrderTraversalList(ArrayList<CDRModel> list, Node focusNode)
    {
        if (null != focusNode)
        {
            list.add(focusNode.CDR);

            BinaryTree.buildPreOrderTraversalList(list, focusNode.leftChild);

            BinaryTree.buildPreOrderTraversalList(list, focusNode.rightChild);
        }
        return list;
    }

    public ArrayList<CDRModel> getPostOrderTraversalList()
    {
        ArrayList<CDRModel> CDRList = new ArrayList<>();

        CDRList = BinaryTree.buildPostOrderTraversalList(CDRList, root);

        return CDRList;
    }

    public static ArrayList<CDRModel> buildPostOrderTraversalList(ArrayList<CDRModel> list, Node focusNode)
    {
        if (null != focusNode)
        {
            BinaryTree.buildPostOrderTraversalList(list, focusNode.leftChild);

            BinaryTree.buildPostOrderTraversalList(list, focusNode.rightChild);

            list.add(focusNode.CDR);
        }
        return list;
    }


    class Node
    {
        CDRModel CDR;
        Node leftChild;
        Node rightChild;

        Node(CDRModel CDR)
        {
            this.CDR = CDR;
        }
        public String toString()
        {
            return CDR.toBarcodeString();
        }
    }
}
