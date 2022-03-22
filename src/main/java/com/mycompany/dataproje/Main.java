/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataproje;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author talha
 */
public class Main extends javax.swing.JFrame {

    int step = 0;
    int size;
    int wantedIndex;
    int wantedIndex2;
    int wantedIndex3;

    ArrayList<JButton> listOfX = new ArrayList<>();         // all buttons in this list
    ArrayList<String> possibleList = new ArrayList<>();     // it will save the possible places to move
    ArrayList<String> eatByMoveList = new ArrayList<>();    // if we move, wee need to delete the node which we jump over, so it will save this nodes
    ArrayList<JLabel> listOfNums = new ArrayList<>();       // it is table's nums
    ArrayList<JLabel> listOfLetters = new ArrayList<>();    // it is table's letters

    MultiLinkedList<String> myList;

    /**
     * Creates new form myGame
     */
    public Main() {

        initComponents();

    }

    int remainingCalculate() {
        int sum = 0;
        for (int i = 0; i < listOfX.size(); i++) {
            if (listOfX.get(i).getText().equals("x")) {
                sum++;
            }
        }
        return sum;
    }

    private class Clicklistener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (step == 0) { // first step of movement
                lbl_gameInfo.setText("Select target location to move");
                for (int i = 0; i < listOfX.size(); i++) {
                    if (listOfX.get(i) == e.getSource()) {
                        wantedIndex = i;
                    }
                }
                char firstChar;
                char secondChar;
                String newData = "";
                int sayac = 0;
                for (int i = 0; i < size; i++) {
                    secondChar = (char) (49 + i);
                    for (int j = 0; j < size; j++) {
                        firstChar = (char) (65 + j);
                        if (sayac == wantedIndex) {
                            newData = String.valueOf(firstChar) + secondChar;
                            System.out.println("Data = " + newData);
                        }
                        sayac++;
                    }
                }
                Node<String> newNode = new Node<>(newData);

                possibleList = myList.possibleMovements(newData);
                eatByMoveList = myList.eatenList();
                int finalSayac = 0;
                if (possibleList.isEmpty()) {
                    step = 0;
                    lbl_possibleHits.setText("Can't play anywhere...");
                } else {
                    step = 1;
                    String moveThere = "";
                    for (int i = 0; i < possibleList.size(); i++) {
                        moveThere += possibleList.get(i) + ", ";
                    }
                    lbl_possibleHits.setText(moveThere);
                    for (int i = 0; i < listOfX.size(); i++) {
                        listOfX.get(i).setEnabled(false);
                    }

                    int sayac2 = 0;
                    for (int i = 0; i < size; i++) {
                        secondChar = (char) (49 + i);
                        for (int j = 0; j < size; j++) {
                            firstChar = (char) (65 + j);
                            newData = String.valueOf(firstChar) + secondChar;
                            for (int k = 0; k < possibleList.size(); k++) {
                                if (newData.equals(possibleList.get(k))) {
                                    finalSayac = sayac2;
                                    for (int l = 0; l < listOfX.size(); l++) {
                                        if (l == finalSayac) {
                                            listOfX.get(l).setEnabled(true);
                                        }
                                    }
                                }
                            }
                            sayac2++;
                        }
                    }

                }
            } else if (step == 1) { // second step of movement
                for (int i = 0; i < listOfX.size(); i++) {
                    if (listOfX.get(i) == e.getSource()) {
                        wantedIndex2 = i;
                        listOfX.get(i).setText("x");
                        listOfX.get(wantedIndex).setText("");
                        listOfX.get(i).setEnabled(true);
                    }
                }
                for (int i = 0; i < listOfX.size(); i++) {
                    listOfX.get(i).setEnabled(false);
                }
                for (int i = 0; i < listOfX.size(); i++) {
                    if (listOfX.get(i).getText().equals("x")) {
                        listOfX.get(i).setEnabled(true);
                    }
                }
                char firstChar;
                char secondChar;
                String mustDeleteData = "";
                String mustDeleteData2 = "";
                String mustAddData = "";
                int sayac = 0;
                for (int i = 0; i < size; i++) {
                    secondChar = (char) (49 + i);
                    for (int j = 0; j < size; j++) {
                        firstChar = (char) (65 + j);
                        if (sayac == wantedIndex) {
                            mustDeleteData = String.valueOf(firstChar) + secondChar;
                        } else if (sayac == wantedIndex2) {
                            mustAddData = String.valueOf(firstChar) + secondChar;
                        }
                        sayac++;
                    }
                }
                for (int i = 0; i < possibleList.size(); i++) {
                    if (possibleList.get(i).equals(mustAddData)) {
                        mustDeleteData2 = eatByMoveList.get(i);
                    }
                }
                myList.deleteData(mustDeleteData);
                myList.addData(mustAddData);
                myList.deleteData(mustDeleteData2);
                sayac = 0;
                int needToDelete = 0;
                String tempData = "";
                for (int i = 0; i < size; i++) {
                    secondChar = (char) (49 + i);
                    for (int j = 0; j < size; j++) {
                        firstChar = (char) (65 + j);
                        tempData = String.valueOf(firstChar) + secondChar;
                        if (tempData.equals(mustDeleteData2)) {
                            needToDelete = sayac;
                        }
                        sayac++;
                    }
                }
                listOfX.get(needToDelete).setText("");
                listOfX.get(needToDelete).setEnabled(false);
                lbl_remainingNum.setText(String.valueOf(Integer.valueOf(lbl_remainingNum.getText()) - 1));
                lbl_gameInfo.setText("Successfull hit! Pick next location...");

                boolean isGameOver = true;
                Node<String> temp = myList.mainHead;
                Node<String> tempDown = temp;
                ArrayList<String> playableList = new ArrayList<>();

                while (temp != null) {
                    while (tempDown != null) {
                        playableList = myList.possibleMovements(tempDown.data);
                        if (!playableList.isEmpty()) {
                            isGameOver = false;
                            break;
                        }
                        tempDown = tempDown.downNode;
                    }
                    if (!isGameOver) {
                        break;
                    }
                    temp = temp.nextNode;
                    tempDown = temp;
                }
                if (isGameOver && (myList.mainHead.downNode != null || myList.mainHead.nextNode != null)) {
                    JOptionPane.showMessageDialog(null, "Your score is: " + lbl_remainingNum.getText(), "GAME OVER!", JOptionPane.INFORMATION_MESSAGE);
                } else if (isGameOver && (myList.mainHead.downNode == null && myList.mainHead.nextNode == null)) {
                    JOptionPane.showMessageDialog(null, "Your score is: " + lbl_remainingNum.getText(), "YOU WON", JOptionPane.INFORMATION_MESSAGE);

                }
                step = 0;

            }

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_main = new javax.swing.JPanel();
        pnl_panel = new javax.swing.JPanel();
        lbl_remainText = new javax.swing.JLabel();
        lbl_remainingNum = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        spnr_size = new javax.swing.JSpinner();
        lbl_possibleHits = new javax.swing.JLabel();
        lbl_gameInfo = new javax.swing.JLabel();
        btn_newGame = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 25, 700, 450));
        setMinimumSize(new java.awt.Dimension(750, 500));

        pnl_main.setMaximumSize(new java.awt.Dimension(750, 500));
        pnl_main.setMinimumSize(new java.awt.Dimension(750, 500));
        pnl_main.setPreferredSize(new java.awt.Dimension(750, 500));
        pnl_main.setSize(new java.awt.Dimension(750, 500));

        pnl_panel.setBackground(new java.awt.Color(255, 153, 102));
        pnl_panel.setMaximumSize(new java.awt.Dimension(350, 350));
        pnl_panel.setMinimumSize(new java.awt.Dimension(350, 350));
        pnl_panel.setPreferredSize(new java.awt.Dimension(350, 350));
        pnl_panel.setSize(new java.awt.Dimension(350, 350));
        pnl_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_panelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnl_panelMousePressed(evt);
            }
        });
        pnl_panel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                pnl_panelPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout pnl_panelLayout = new javax.swing.GroupLayout(pnl_panel);
        pnl_panel.setLayout(pnl_panelLayout);
        pnl_panelLayout.setHorizontalGroup(
            pnl_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );
        pnl_panelLayout.setVerticalGroup(
            pnl_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );

        lbl_remainText.setText("Remaining: ");

        lbl_remainingNum.setText("0");

        jLabel1.setText("Possible hit location(s):");

        spnr_size.setModel(new javax.swing.SpinnerNumberModel(5, 5, 9, 1));

        lbl_possibleHits.setText("-");

        lbl_gameInfo.setText("Select location that you want to move");

        btn_newGame.setText("New Game");
        btn_newGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newGameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_mainLayout = new javax.swing.GroupLayout(pnl_main);
        pnl_main.setLayout(pnl_mainLayout);
        pnl_mainLayout.setHorizontalGroup(
            pnl_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_mainLayout.createSequentialGroup()
                .addGroup(pnl_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_mainLayout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(spnr_size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(btn_newGame))
                    .addGroup(pnl_mainLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(pnl_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addGroup(pnl_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_mainLayout.createSequentialGroup()
                                .addComponent(lbl_remainText)
                                .addGap(43, 43, 43)
                                .addComponent(lbl_remainingNum))
                            .addComponent(lbl_gameInfo)
                            .addGroup(pnl_mainLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_possibleHits, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(167, 167, 167))
        );
        pnl_mainLayout.setVerticalGroup(
            pnl_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_mainLayout.createSequentialGroup()
                .addGroup(pnl_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_mainLayout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addGroup(pnl_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_remainText)
                            .addComponent(lbl_remainingNum))
                        .addGap(72, 72, 72)
                        .addGroup(pnl_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_possibleHits)
                            .addComponent(jLabel1))
                        .addGap(71, 71, 71)
                        .addComponent(lbl_gameInfo))
                    .addGroup(pnl_mainLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(pnl_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_newGame)
                            .addComponent(spnr_size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addComponent(pnl_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(pnl_main, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_main, javax.swing.GroupLayout.PREFERRED_SIZE, 471, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_newGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newGameActionPerformed
        // TODO add your handling code here:
        step = 0;
        listOfX.clear();
        for (int j = 0; j < listOfLetters.size(); j++) {
            pnl_main.remove(listOfLetters.get(j));
            pnl_main.remove((listOfNums.get(j)));
        }

        listOfLetters.clear();
        listOfNums.clear();

        pnl_panel.removeAll();
        pnl_panel.revalidate();
        pnl_main.revalidate();
        pnl_main.repaint();
        pnl_panel.repaint();
        size = (int) spnr_size.getValue();
        int totalButton = size * size;

        int buttonHeight = pnl_panel.getHeight() / size;

        Clicklistener click = new Clicklistener();

        int numsX = pnl_panel.getX(), numsY = pnl_panel.getY(), lettersX = pnl_panel.getX(), lettersY = pnl_panel.getY();
        for (int i = 0; i < size; i++) {
            if (i < size) {
                JLabel lbl = new JLabel(String.valueOf((char) (i + '1')));
                listOfNums.add(lbl);
                pnl_main.add(lbl);
                lbl.setBounds(numsX - buttonHeight / 3, numsY + 5, 20, 30);
                JLabel lbl2 = new JLabel(String.valueOf((char) ('A' + i)));
                listOfLetters.add(lbl2);
                pnl_main.add(lbl2);
                lbl2.setBounds(lettersX + buttonHeight / 3, lettersY - 35, 20, 30);
                numsY += buttonHeight;
                lettersX += buttonHeight;
            }

        }

        int startX = 0;
        int startY = 0;
        for (int i = 0; i < totalButton; i++) {
            JButton btn = new JButton("x");
            btn.setBounds(startX, startY, buttonHeight, buttonHeight);
            startX += buttonHeight;
            listOfX.add(btn);
            btn.addActionListener(click);
            pnl_panel.add(listOfX.get(i));
            if (listOfX.size() % size == 0) {
                startY += buttonHeight;
                startX = 0;

            }
        }
        if (listOfX.size() % 2 == 1) {
            listOfX.get(listOfX.size() / 2).setEnabled(false);
            listOfX.get(listOfX.size() / 2).setText("");
        } else {
            int temp = size / 2;
            listOfX.get(listOfX.size() / 2 + temp - 1).setEnabled(false);
            listOfX.get(listOfX.size() / 2 + temp).setEnabled(false);
            listOfX.get(listOfX.size() / 2 - temp).setEnabled(false);
            listOfX.get(listOfX.size() / 2 - temp - 1).setEnabled(false);
            listOfX.get(listOfX.size() / 2 + temp - 1).setText("");
            listOfX.get(listOfX.size() / 2 + temp).setText("");
            listOfX.get(listOfX.size() / 2 - temp).setText("");
            listOfX.get(listOfX.size() / 2 - temp - 1).setText("");
        }
        lbl_remainingNum.setText(String.valueOf(remainingCalculate()));
        myList = new MultiLinkedList<>(size);
        myList.createLinkedList();

        char firstChar;
        char secondChar;
        String newData = "";
        int sayac = 0;
        int needToRemove;
        for (int i = 0; i < listOfX.size(); i++) {
            if (listOfX.get(i).getText().equals("")) {
                needToRemove = i;
                for (int j = 0; j < size; j++) {
                    secondChar = (char) (49 + j);
                    for (int k = 0; k < size; k++) {
                        firstChar = (char) (65 + k);
                        if (sayac == needToRemove) {
                            newData = String.valueOf(firstChar) + secondChar;
                            myList.deleteData(newData);
                        }
                        sayac++;
                    }
                }
            }
            sayac = 0;
        }
    }//GEN-LAST:event_btn_newGameActionPerformed

    private void pnl_panelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_panelMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_pnl_panelMouseClicked

    private void pnl_panelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_panelMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pnl_panelMousePressed

    private void pnl_panelPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_pnl_panelPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_pnl_panelPropertyChange

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_newGame;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbl_gameInfo;
    private javax.swing.JLabel lbl_possibleHits;
    private javax.swing.JLabel lbl_remainText;
    private javax.swing.JLabel lbl_remainingNum;
    private javax.swing.JPanel pnl_main;
    private javax.swing.JPanel pnl_panel;
    private javax.swing.JSpinner spnr_size;
    // End of variables declaration//GEN-END:variables
}
