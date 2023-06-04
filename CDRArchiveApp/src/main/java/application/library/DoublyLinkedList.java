package application.library;

import application.models.ProcessLogRecord;

public class DoublyLinkedList {
    Node head;
    Node tail;
    private int size;

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
        Node current = head;
        if (1 > i || null == current)
        {
            throw new ArrayIndexOutOfBoundsException();
        }
        for (int j = 1; j < i; j++) {
            current = current.next;
            if (null == current)
            {
                throw new ArrayIndexOutOfBoundsException();
            }
        }
        return current.get();

    }

    public void append(ProcessLogRecord processLogRecord)
    {
        Node newNode = new Node(processLogRecord);
        if(null == head)
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
            processLogRecord = new ProcessLogRecord(newprocessLogRecord.getDate(), newprocessLogRecord.getAction(),
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
            if (null != next)
            {
                next.previous = newNode;
            }
            next= newNode;
        }
        public void append(ProcessLogRecord processLogRecord)
        {
            append(new Node(processLogRecord));
        }
    }
}
