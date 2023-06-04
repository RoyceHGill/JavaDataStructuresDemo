package SecondaryApplication.Library;

import SecondaryApplication.Models.ProcessLogRecord;

public class DoublyLinkedList {
    Node head;
    Node tail;
    private int size = 0;

    public int getSize() {
        return size;
    }

    public DoublyLinkedList()
    {
        head = null;
        tail = null;
    }
    public ProcessLogRecord get(int i)
    {
        i++;
        Node current = this.head;
        if (i < 1 || current == null)
        {
            throw new ArrayIndexOutOfBoundsException();
        }
        for (int j = 1; j < i; j++) {
            current = current.next;
            if (current == null)
            {
                throw new ArrayIndexOutOfBoundsException();
            }
        }
        return current.get();

    }


    public void append(ProcessLogRecord processLogRecord)
    {
        Node newNode = new Node(processLogRecord);
        if(head == null)
        {
            head = newNode;
            tail = newNode;
        }
        // existing tail's next points to new node.
        tail.next = newNode;
        //newNode's previous point to tail.
        newNode.previous = tail;
        // we make new node the new tail of the DList.
        tail = newNode;
        size++;
    }




    public static class Node {
        Node previous;
        Node next;
        ProcessLogRecord processLogRecord;

        Node()
        {
            previous = this;
            next = this;
            processLogRecord = new ProcessLogRecord();
        }

        Node(ProcessLogRecord newprocessLogRecord)
        {
            previous = null;
            next = null;
            processLogRecord = new ProcessLogRecord(newprocessLogRecord.getAction(),
                    newprocessLogRecord.getCommunicationType(), newprocessLogRecord.getBarcode());
        }

        private ProcessLogRecord get()
        {
            return processLogRecord;
        }
        // appends the new node behind this node.
        private void append(Node newNode)
        {
            newNode.previous = this;
            newNode.next = next;
            if (this.next != null)
            {
                this.next.previous = newNode;
            }
            this.next= newNode;
        }
        public void append(ProcessLogRecord processLogRecord)
        {
            this.append(new Node(processLogRecord));
        }
    }
}
