/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.avalon.java.dev.j120.practice.UI;

import java.math.BigDecimal;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.table.TableModel;
import ru.avalon.java.dev.j120.practice.UI.tablemodels.GoodsTableModel;
import ru.avalon.java.dev.j120.practice.controller.Mediator;
import ru.avalon.java.dev.j120.practice.entity.Good;
import ru.avalon.java.dev.j120.practice.entity.Order;
import ru.avalon.java.dev.j120.practice.entity.OrderedItem;
import ru.avalon.java.dev.j120.practice.exceptions.IllegalStatusException;
import ru.avalon.java.dev.j120.practice.utils.StateEnum;

/**
 *
 * @author Hino
 */
public class GoodsPanel extends javax.swing.JPanel {
    private JTabbedPane maintab;
    private GoodsCardPanel goodsCard;
    private final Mediator mediator;
    private TableModel gtm;
    private JPanel opParent;
    private StateEnum state;
    private Order order;
    /**
     * Creates new form GoodsPanel
     * @param mediator
     * @param state
     */
    
    public GoodsPanel(Mediator mediator, StateEnum state) {
        initComponents();
        this.mediator = mediator;        
        gtm = new GoodsTableModel(mediator);
        goodsTable.setModel(gtm);
        this.state = state;
        if (state.equals(StateEnum.NEW)){
            addToOrderButton.setVisible(false);
            closeButton.setVisible(false);            
        }
        
        if (state.equals(StateEnum.EXIST)){
            openNewGoodsCardButton.setVisible(false);            
        }
    }
    
    public GoodsPanel(Mediator mediator, JPanel opParent, Order order, StateEnum state) {
        initComponents();
        this.mediator = mediator;
        this.order = order;
        this.opParent = opParent;
        gtm = new GoodsTableModel(mediator);
        goodsTable.setModel(gtm);
        this.state = state;
        if (state.equals(StateEnum.NEW)){
            addToOrderButton.setVisible(false);
            closeButton.setVisible(false);            
        }
        
        if (state.equals(StateEnum.EXIST)){
            openNewGoodsCardButton.setVisible(false);            
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

        jToolBar1 = new javax.swing.JToolBar();
        openNewGoodsCardButton = new javax.swing.JButton();
        addToOrderButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        goodsTable = new javax.swing.JTable();

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        openNewGoodsCardButton.setText("Добавить номенклатуру");
        openNewGoodsCardButton.setFocusable(false);
        openNewGoodsCardButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        openNewGoodsCardButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        openNewGoodsCardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openNewGoodsCardButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(openNewGoodsCardButton);

        addToOrderButton.setText("Добавить в заказ");
        addToOrderButton.setFocusable(false);
        addToOrderButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addToOrderButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(addToOrderButton);

        closeButton.setText("Закрыть");
        closeButton.setFocusable(false);
        closeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        closeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(closeButton);

        goodsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        goodsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                goodsTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(goodsTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void openNewGoodsCardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openNewGoodsCardButtonActionPerformed
        JTabbedPane maintab = (JTabbedPane) this.getParent();
        goodsCard = new GoodsCardPanel(mediator, this);
        maintab.addTab("Новая номенклатурная единица", goodsCard);
        maintab.setSelectedIndex(maintab.getTabCount() -1);
    }//GEN-LAST:event_openNewGoodsCardButtonActionPerformed

    private void goodsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goodsTableMouseClicked
        if (evt.getButton() == 1 && evt.getClickCount() == 2){
        maintab = (JTabbedPane) this.getParent();
        //Получение данных о товаре из таблицы
        //-----------------------------------------------------------------------------
        Long article = (Long) gtm.getValueAt(goodsTable.getSelectedRow(), 0);        
        String variety =(String) gtm.getValueAt(goodsTable.getSelectedRow(), 1);
        String color =(String) gtm.getValueAt(goodsTable.getSelectedRow(), 2);
        BigDecimal price = (BigDecimal) gtm.getValueAt(goodsTable.getSelectedRow(), 3);
        Long instock = (Long) gtm.getValueAt(goodsTable.getSelectedRow(), 4);            
        //-----------------------------------------------------------------------------
        
        Good good = new Good (article,variety,color,price,instock);
        
            if (state.equals(StateEnum.NEW)){
                openGoodCardPanel(good);
            }
            
            if (state.equals(StateEnum.EXIST)){
            try {
                order.add(new OrderedItem(good,good.getPrice(),1));                
            } catch (IllegalStatusException ex) {
                
            }
            }
        }
    }//GEN-LAST:event_goodsTableMouseClicked
    
    private void openGoodCardPanel(Good good){        
        //Инициализация панели товара        
        goodsCard = new GoodsCardPanel(mediator, this, good);
        maintab.addTab("Номенклатурная единица "+ good.getArticle(), goodsCard);
        
        maintab.setSelectedIndex(maintab.getTabCount() -1);
    }
    
    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        maintab = (JTabbedPane) this.getParent();        
        maintab.setSelectedComponent(opParent);
        maintab.remove(this);
    }//GEN-LAST:event_closeButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addToOrderButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JTable goodsTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton openNewGoodsCardButton;
    // End of variables declaration//GEN-END:variables
}