/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataproje;

/**
 *
 * @author talha
 */
import java.util.ArrayList;

public class MultiLinkedList<T> {

    ArrayList<String> possibleMoves = new ArrayList<>();     // places to jump over
    ArrayList<String> possibleMovesEmpty = new ArrayList<>();// possible movement places

    Node<T> mainHead;
    int myListSize;

    MultiLinkedList(int listSize) {
        mainHead = null;
        myListSize = listSize;
    }

    void createLinkedList() {
        mainHead = null;
        Node<T> temp = mainHead;
        for (int i = 65; i < 65 + myListSize; i++) {
            char firstChar = (char) i;
            T newData = (T) (firstChar + "1");
            Node<T> newNode = new Node<>(newData);

            if (mainHead == null) {
                mainHead = newNode;
                temp = mainHead;
            } else {
                temp.nextNode = newNode;
                temp = temp.nextNode;
            }
        }
        temp = mainHead;
        Node<T> tempDown = temp;
        for (int i = 65; i < 65 + myListSize; i++) { // 65 = 'A'  ,  49 = '1'
            char firstChar = (char) i;
            for (int j = 50; j < 50 + myListSize - 1; j++) {
                char secondChar = (char) j;
                T newData = (T) (String.valueOf(firstChar) + String.valueOf(secondChar));
                Node<T> newNode = new Node<>(newData);
                tempDown.downNode = newNode;
                tempDown = tempDown.downNode;
            }
            temp = temp.nextNode;
            tempDown = temp;
        }
    }

    boolean isItFull(T data) { // is there a node or not?
        Node<T> temp = mainHead;
        Node<T> tempDown = temp;

        boolean bulunduMu = false;
        while (temp != null) {
            while (tempDown != null) {
                if (tempDown.data.equals(data)) {
                    bulunduMu = true;
                    break;
                } else {
                    tempDown = tempDown.downNode;
                }
            }
            if (bulunduMu) {
                break;
            }
            temp = temp.nextNode;
            tempDown = temp;
        }

        return bulunduMu;
    }

    ArrayList<String> eatenList() { // returning the places to jump over
        return possibleMoves;
    }

    ArrayList<String> possibleMovements(T data) {
        possibleMoves.clear();
        possibleMovesEmpty.clear();
        Node<T> temp = mainHead;
        Node<T> tempDown = temp;
        char firstCharData = data.toString().charAt(0);
        char secondCharData = data.toString().charAt(1);

        String possMove = String.valueOf(firstCharData) + (char) (secondCharData - 1);
        possibleMoves.add(possMove);
        possMove = String.valueOf(firstCharData) + (char) (secondCharData + 1);
        possibleMoves.add(possMove);
        possMove = (char) (firstCharData + 1) + String.valueOf(secondCharData);
        possibleMoves.add(possMove);
        possMove = (char) (firstCharData - 1) + String.valueOf(secondCharData);
        possibleMoves.add(possMove);

        possMove = String.valueOf(firstCharData) + (char) (secondCharData - 2);
        possibleMovesEmpty.add(possMove);
        possMove = String.valueOf(firstCharData) + (char) (secondCharData + 2);
        possibleMovesEmpty.add(possMove);
        possMove = (char) (firstCharData + 2) + String.valueOf(secondCharData);
        possibleMovesEmpty.add(possMove);
        possMove = (char) (firstCharData - 2) + String.valueOf(secondCharData);
        possibleMovesEmpty.add(possMove);

        boolean canPlay = false;
        char maxFirstChar = (char) ('A' + myListSize - 1);
        char maxSecondChar = (char) ('1' + myListSize - 1);

        for (int i = 0; i < possibleMoves.size(); i++) {
            if (possibleMoves.get(i).charAt(0) > 'I' || possibleMoves.get(i).charAt(0) < 'A'
                    || possibleMoves.get(i).charAt(1) < '1' || possibleMoves.get(i).charAt(1) > '9'
                    || possibleMovesEmpty.get(i).charAt(0) > 'I' || possibleMovesEmpty.get(i).charAt(0) < 'A'
                    || possibleMovesEmpty.get(i).charAt(1) > '9' || possibleMovesEmpty.get(i).charAt(1) < '1'
                    || possibleMovesEmpty.get(i).charAt(1) > maxSecondChar || possibleMovesEmpty.get(i).charAt(0) > maxFirstChar
                    || possibleMoves.get(i).charAt(1) > maxSecondChar || possibleMoves.get(i).charAt(0) > maxFirstChar) {
                possibleMoves.remove(i);
                possibleMovesEmpty.remove(i);
            }
        }
        for (int i = 0; i < possibleMovesEmpty.size(); i++) {
            if (possibleMoves.get(i).charAt(0) > 'I' || possibleMoves.get(i).charAt(0) < 'A'
                    || possibleMoves.get(i).charAt(1) < '1' || possibleMoves.get(i).charAt(1) > '9'
                    || possibleMovesEmpty.get(i).charAt(0) > 'I' || possibleMovesEmpty.get(i).charAt(0) < 'A'
                    || possibleMovesEmpty.get(i).charAt(1) > '9' || possibleMovesEmpty.get(i).charAt(1) < '1'
                    || possibleMovesEmpty.get(i).charAt(1) > maxSecondChar || possibleMovesEmpty.get(i).charAt(0) > maxFirstChar
                    || possibleMoves.get(i).charAt(1) > maxSecondChar || possibleMoves.get(i).charAt(0) > maxFirstChar) {
                possibleMoves.remove(i);
                possibleMovesEmpty.remove(i);
            } else {
                canPlay = true;
            }
        }

        boolean haveData = false;
        boolean isNextEmpty = true;
        boolean isDataDeleted = false;
        int i = 0;

        while (i < possibleMoves.size()) {
            if (isDataDeleted == true) {
                i = 0;
                isDataDeleted = false;
            }
            haveData = false;
            isNextEmpty = true;
            haveData = isItFull((T) possibleMoves.get(i));
            isNextEmpty = isItFull((T) possibleMovesEmpty.get(i));
            if (!haveData || isNextEmpty) {
                possibleMoves.remove(i);
                possibleMovesEmpty.remove(i);
                isDataDeleted = true;
                continue;
            }
            if (possibleMoves.size() == 1) {
                haveData = false;
                isNextEmpty = true;
                haveData = isItFull((T) possibleMoves.get(0));
                isNextEmpty = isItFull((T) possibleMovesEmpty.get(0));
                if (!haveData || isNextEmpty) {
                    possibleMoves.remove(0);
                    possibleMovesEmpty.remove(0);
                }
            }
            i++;
        }
        return possibleMovesEmpty;   // if there is no possible moves, arraylist will be empty
    }

    void deleteData(T data) {
        Node<T> temp = mainHead;
        Node<T> tempDown = temp;

        boolean silindiMi = false;
        if (mainHead.data.equals(data)) { // is data in the first node of the linkedlist
            if (mainHead.downNode != null) {
                mainHead.downNode.nextNode = mainHead.nextNode;
                mainHead = mainHead.downNode;
                silindiMi = true;
            } else {
                mainHead = mainHead.nextNode;
                temp = mainHead;
                tempDown = temp;
                silindiMi = true;
            }
        }

        while (temp.nextNode != null && !silindiMi) { // is data in the first row of the linkedlist
            if (temp.nextNode.data.equals(data)) {
                if (temp.nextNode.downNode == null) {
                    temp.nextNode = temp.nextNode.nextNode;
                    silindiMi = true;
                } else {
                    temp.nextNode.downNode.nextNode = temp.nextNode.nextNode;
                    temp.nextNode = temp.nextNode.downNode;
                    silindiMi = true;
                }
            } else {
                temp = temp.nextNode;
            }
        }

        temp = mainHead;

        while (temp != null && !silindiMi) {
            while (tempDown.downNode != null && !silindiMi) { // is data in other nodes of the linkedlist
                if (tempDown.downNode.data.equals(data)) {
                    tempDown.downNode = tempDown.downNode.downNode;
                    silindiMi = true;
                } else {
                    tempDown = tempDown.downNode;
                }
            }
            temp = temp.nextNode;
            tempDown = temp;
        }

    }

    void addData(T data) {
        Node<T> temp = mainHead;
        Node<T> tempDown = temp;
        Node<T> newNode = new Node<>(data);

        char firstCharData = data.toString().charAt(0);
        char secondCharData = data.toString().charAt(1);
        char firstCharTemp = mainHead.data.toString().charAt(0);
        char secondCharTemp = mainHead.data.toString().charAt(1);

        boolean eklendiMi = false;
        if ((firstCharData == firstCharTemp) && (secondCharData < secondCharTemp)) { // do we need to add the data to head
            newNode.nextNode = mainHead.nextNode;
            newNode.downNode = mainHead.downNode;
            mainHead.nextNode = null;
            mainHead = newNode;
            eklendiMi = true;
        }

        while (temp.nextNode != null && !eklendiMi) { //  do we need to add data to first row
            firstCharTemp = temp.nextNode.data.toString().charAt(0);
            secondCharTemp = temp.nextNode.data.toString().charAt(1);
            if ((firstCharTemp == firstCharData) && (secondCharData < secondCharTemp)) {
                if (temp.nextNode.nextNode == null) {
                    newNode.nextNode = null;
                    newNode.downNode = temp.nextNode;
                    temp.nextNode = newNode;
                    eklendiMi = true;
                } else {
                    newNode.nextNode = temp.nextNode.nextNode;
                    newNode.downNode = temp.nextNode;
                    temp.nextNode.nextNode = null;
                    temp.nextNode = newNode;
                    eklendiMi = true;
                }
            } else {
                temp = temp.nextNode;
            }
        }

        temp = mainHead;
        String wantedTemp;
        firstCharTemp = data.toString().charAt(0);

        while (temp != null && !eklendiMi) { // if we have all the nodes on every column
            if (temp.data.toString().charAt(0) == firstCharTemp) {
                while (tempDown != null && !eklendiMi) {
                    if (tempDown.downNode != null) {
                        secondCharTemp = tempDown.downNode.data.toString().charAt(1);
                        if (secondCharTemp > data.toString().charAt(1)) {
                            newNode.downNode = tempDown.downNode;
                            tempDown.downNode = newNode;
                            eklendiMi = true;
                        } else {
                            tempDown = tempDown.downNode;
                        }
                    } else {
                        newNode.downNode = tempDown.downNode;
                        tempDown.downNode = newNode;
                        eklendiMi = true;
                    }
                }
            }
            temp = temp.nextNode;
            tempDown = temp;
        }

        if (eklendiMi == false) {
            temp = mainHead;
            tempDown = temp;
            firstCharData = data.toString().charAt(0);
            secondCharData = data.toString().charAt(1);
            while (temp.nextNode != null && !eklendiMi) { //  adding node to be added somewhere in between (A-B exists, there is no C, there is D. If it is to be added to C.)
                char firstCharTemp2 = temp.nextNode.data.toString().charAt(0);
                firstCharTemp = temp.data.toString().charAt(0);
                firstCharTemp2 = temp.nextNode.data.toString().charAt(0);
                if ((firstCharTemp < firstCharData) && (firstCharData < firstCharTemp2)) {
                    newNode.nextNode = temp.nextNode;
                    temp.nextNode = newNode;
                    eklendiMi = true;
                } else {
                    temp = temp.nextNode;
                }
            }
        }

        firstCharTemp = mainHead.data.toString().charAt(0);
        if (eklendiMi == false) {//  adding node to be added to the first column. (There is no A - B, but there is C or D)
            if (newNode.data.toString().charAt(0) < firstCharTemp) {
                newNode.nextNode = mainHead;
                mainHead = newNode;
                eklendiMi = true;
            }
        }

        temp = mainHead;
        if (eklendiMi == false) {
            while (temp.nextNode != null) {
                temp = temp.nextNode;
            }
            temp.nextNode = newNode;
            eklendiMi = true;
        }
    }
}
