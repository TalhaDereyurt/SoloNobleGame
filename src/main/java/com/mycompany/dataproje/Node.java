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
public class Node<T> {

    T data;
    Node<T> nextNode;
    Node<T> downNode;

    Node(T data) {
        this.data = data;
        this.nextNode = null;
        this.downNode = null;

    }
}
