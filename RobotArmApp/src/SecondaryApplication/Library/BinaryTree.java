package SecondaryApplication.Library;

public class BinaryTree {

    Node root;

    public void addNode(String barcode, int key)
    {
        Node newNode = new Node(barcode, key);

        if (root == null) {
            root = newNode;
        } else {
            Node focusNode = root;
            Node parent;
            while (true){
                parent = focusNode;
                if (newNode.key < focusNode.key) {
                    focusNode = focusNode.leftChild;
                    if (focusNode == null) {
                        parent.leftChild = newNode;
                        return;
                    }
                } else {
                    focusNode = focusNode.rightChild;
                    if (focusNode == null) {
                        parent.rightChild = newNode;
                        return;
                    }
                }
            }
        }

    }
    public static class Node
    {
        int key;
        String barcode;

        Node leftChild;
        Node rightChild;

        Node(String barcode, int key)
        {
            this.barcode = barcode;
            this.key = key;
        }

        public String toString()
        {
            return barcode;
        }
    }
}
