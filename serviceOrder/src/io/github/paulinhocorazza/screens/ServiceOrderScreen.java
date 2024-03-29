/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.paulinhocorazza.screens;

import java.sql.*;
import io.github.paulinhocorazza.dal.DatabaseConnection;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author pauloviniciusbarbosacorazza
 */
public class ServiceOrderScreen extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    //radio button

    private String tipo;
    //private String status;

    /**
     * Creates new form ServiceOrderScreen
     */
    public ServiceOrderScreen() {
        initComponents();
        conexao = DatabaseConnection.conector();
    }

    private void searchClient() {
        String sql = "select id_cliente as ID,cliente_nome as NOME, cliente_fone as FONE from tb_clientes where cliente_nome like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtClientSearch.getText() + "%");
            rs = pst.executeQuery();
            tblClients.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
        }

    }

    private void setFieldsFromTable() {
        int set = tblClients.getSelectedRow();
        txtIdClient.setText(tblClients.getModel().getValueAt(set, 0).toString());

    }

    private void createrServiceOrder() {
        String sql = "insert into tb_os (tipo,status,os_equipamento,os_defeito,os_servico,os_tecnico,os_valor,id_cliente) values (?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareCall(sql);
            pst.setString(1, tipo);
            pst.setString(2, situationCombo.getSelectedItem().toString());
            pst.setString(3, txtEquipmentName.getText());
            pst.setString(4, txtIssueName.getText());
            pst.setString(5, txtServiceName.getText());
            pst.setString(6, txtTechnicianName.getText());
            pst.setString(7, txtOsValue.getText().replace(",", "."));
            pst.setString(8, txtIdClient.getText());

            if (txtIdClient.getText().isEmpty() || txtEquipmentName.getText().isEmpty() || txtIssueName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios !");
            } else {
                int addedServiceOrder = pst.executeUpdate();
                if (addedServiceOrder > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de servico emitida com sucesso !");
                    txtIdClient.setText(null);
                    txtEquipmentName.setText(null);
                    txtIssueName.setText(null);
                    txtServiceName.setText(null);
                    txtTechnicianName.setText(null);
                    txtOsValue.setText(null);
                    //habilitando campos
                    btnCreateServiceOrder.setEnabled(true);
                    txtClientSearch.setEnabled(true);
                    tblClients.setVisible(true);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro : " + e);
        }
    }

    public void readServiceOrder() {
        String serviceOrder;
        serviceOrder = JOptionPane.showInputDialog("Informe o número da Ordem de Serviço:");
        String sql = "select * from tb_os where id_os=" + serviceOrder;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtServiceOrder.setText(rs.getString(1));
                txtServiceOrderDate.setText(rs.getString(2));
                String radioSelected = rs.getString(3);
                if (radioSelected.equals("Ordem de Serviço")) {
                    serviceOrderRadio.setSelected(true);
                    tipo = "Ordem de Serviço";

                } else {
                    tenderRadio.setSelected(true);
                    tipo = "Orçamento";
                    situationCombo.setSelectedIndex(1);

                }
                situationCombo.setSelectedItem(rs.getString(4));
                txtEquipmentName.setText(rs.getString(5));
                txtIssueName.setText(rs.getString(6));
                txtServiceName.setText(rs.getString(7));
                txtTechnicianName.setText(rs.getString(8));
                txtOsValue.setText(rs.getString(9));
                btnCreateServiceOrder.setEnabled(false);
                txtClientSearch.setEnabled(false);
                tblClients.setVisible(false);

            } else {
                JOptionPane.showMessageDialog(null, "Ordem de serviço não cadastrada!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ordem der Serviço Inválida, digite apenas números! ");
            System.out.println(e);
        } 
    }

    public void updateServiceOrder() {
        String sql = "update tb_os set tipo=?,status=?,os_equipamento=?,os_defeito=?,os_servico=?,os_tecnico=?,os_valor=? where id_os=?";
        try {
            pst = conexao.prepareCall(sql);
            pst.setString(1, tipo);
            pst.setString(2, situationCombo.getSelectedItem().toString());
            pst.setString(3, txtEquipmentName.getText());
            pst.setString(4, txtIssueName.getText());
            pst.setString(5, txtServiceName.getText());
            pst.setString(6, txtTechnicianName.getText());
            pst.setString(7, txtOsValue.getText().replace(",", "."));
            pst.setString(8, txtServiceOrder.getText());

            if (txtEquipmentName.getText().isEmpty() || txtIssueName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios !");
            } else {
                int addedServiceOrder = pst.executeUpdate();
                if (addedServiceOrder > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de servico atualizada com sucesso !");
                    txtIdClient.setText(null);
                    txtEquipmentName.setText(null);
                    txtIssueName.setText(null);
                    txtServiceName.setText(null);
                    txtTechnicianName.setText(null);
                    txtOsValue.setText(null);
                    txtServiceOrder.setText(null);
                    txtServiceOrderDate.setText(null);
                    btnCreateServiceOrder.setEnabled(true);
                    txtClientSearch.setEnabled(true);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro : " + e);
        }
    }

    public void deleteServiceOrder() {
        int accept = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esta ordem de serviço ?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (accept == JOptionPane.YES_OPTION) {
            String sql = "delete from tb_os where id_os=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtServiceOrder.getText());
                int deletedServiceOrder = pst.executeUpdate();
                if (deletedServiceOrder > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de serviço excluída com sucesso !");
                    txtIdClient.setText(null);
                    txtEquipmentName.setText(null);
                    txtIssueName.setText(null);
                    txtServiceName.setText(null);
                    txtTechnicianName.setText(null);
                    txtOsValue.setText(null);
                    txtServiceOrder.setText(null);
                    txtServiceOrderDate.setText(null);
                    btnCreateServiceOrder.setEnabled(true);
                    txtClientSearch.setEnabled(true);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro: " + e);
            }
        }

    }
    
    private void printServiceOrder(){
        int accept = JOptionPane.showConfirmDialog(null,"Imprimir Ordem de Serviço ?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (accept == JOptionPane.YES_OPTION){
            try{
                HashMap filter = new HashMap();
                filter.put("os", Integer.parseInt(txtServiceOrder.getText()));
                //windows
                JasperPrint print = JasperFillManager.fillReport("C:/reports/Ordem.jasper", null, conexao);
                //JasperPrint print = JasperFillManager.fillReport("/Users/pauloviniciusbarbosacorazza/Documents/dev/service_order_java/reports/Ordem.jasper", filter, conexao);
                JasperViewer.viewReport(print, false);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtServiceOrder = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtServiceOrderDate = new javax.swing.JTextField();
        serviceOrderRadio = new javax.swing.JRadioButton();
        tenderRadio = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        situationCombo = new javax.swing.JComboBox<String>();
        clientPanel = new javax.swing.JPanel();
        txtClientSearch = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtIdClient = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClients = new javax.swing.JTable();
        btnCreateServiceOrder = new javax.swing.JButton();
        btnUpdateServiceOrder = new javax.swing.JButton();
        btnReadServiceOrder = new javax.swing.JButton();
        btnDeleteServiceOrder = new javax.swing.JButton();
        btnPrintServiceOrder = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtEquipmentName = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtIssueName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtServiceName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtOsValue = new javax.swing.JTextField();
        txtTechnicianName = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Ordens de Serviços");
        setPreferredSize(new java.awt.Dimension(915, 650));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Ordens de Serviços"));

        jLabel1.setText("N* OS:");

        txtServiceOrder.setEditable(false);
        txtServiceOrder.setEnabled(false);

        jLabel2.setText("Data:");

        txtServiceOrderDate.setEditable(false);

        buttonGroup1.add(serviceOrderRadio);
        serviceOrderRadio.setText("Ordem de serviço");
        serviceOrderRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serviceOrderRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(tenderRadio);
        tenderRadio.setText("Orçamento");
        tenderRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenderRadioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtServiceOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtServiceOrderDate, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(serviceOrderRadio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tenderRadio)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtServiceOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtServiceOrderDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tenderRadio)
                    .addComponent(serviceOrderRadio))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jLabel3.setText("Situação:");

        situationCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Orçamento", "Na bancada", "Entrega OK", "Orçamento Reprovado", "Aguardando Peças", "Abandonado pelo Cliente", "Retornou", "Aguardando Retirada" }));
        situationCombo.setToolTipText("Status ");

        clientPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Clientes"));

        txtClientSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtClientSearchKeyReleased(evt);
            }
        });

        jLabel5.setText("*id:");

        txtIdClient.setEnabled(false);

        tblClients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nome", "Telefone"
            }
        ));
        tblClients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClients);

        javax.swing.GroupLayout clientPanelLayout = new javax.swing.GroupLayout(clientPanel);
        clientPanel.setLayout(clientPanelLayout);
        clientPanelLayout.setHorizontalGroup(
            clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, clientPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtClientSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIdClient, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
            .addGroup(clientPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        clientPanelLayout.setVerticalGroup(
            clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clientPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtClientSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtIdClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        btnCreateServiceOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/github/paulinhocorazza/icons/create.png"))); // NOI18N
        btnCreateServiceOrder.setToolTipText("Adicionar Ordem de Serviço");
        btnCreateServiceOrder.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCreateServiceOrder.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCreateServiceOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateServiceOrderActionPerformed(evt);
            }
        });

        btnUpdateServiceOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/github/paulinhocorazza/icons/update.png"))); // NOI18N
        btnUpdateServiceOrder.setToolTipText("Editar Ordem de Serviço");
        btnUpdateServiceOrder.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUpdateServiceOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateServiceOrderActionPerformed(evt);
            }
        });

        btnReadServiceOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/github/paulinhocorazza/icons/read.png"))); // NOI18N
        btnReadServiceOrder.setToolTipText("Consultar Ordem de Serviço");
        btnReadServiceOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReadServiceOrderActionPerformed(evt);
            }
        });

        btnDeleteServiceOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/github/paulinhocorazza/icons/delete.png"))); // NOI18N
        btnDeleteServiceOrder.setToolTipText("Excluir Ordem de Serviço");
        btnDeleteServiceOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteServiceOrderActionPerformed(evt);
            }
        });

        btnPrintServiceOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/github/paulinhocorazza/icons/print.png"))); // NOI18N
        btnPrintServiceOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintServiceOrderActionPerformed(evt);
            }
        });

        jLabel4.setText("*Equipamento:");

        jLabel11.setBackground(new java.awt.Color(204, 0, 0));
        jLabel11.setText("* Campos Obrigatorios");

        jLabel6.setText("*Defeito:");

        jLabel7.setText("Serviço:");

        jLabel8.setText("Técnico:");

        jLabel9.setText("Valor Total:");

        txtOsValue.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(situationCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(clientPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 360, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtIssueName, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtServiceName, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtTechnicianName, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtOsValue, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE))
                            .addComponent(txtEquipmentName))))
                .addGap(30, 30, 30))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(btnCreateServiceOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReadServiceOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdateServiceOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteServiceOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrintServiceOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(situationCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(clientPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtEquipmentName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtIssueName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtServiceName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtOsValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTechnicianName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnReadServiceOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdateServiceOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCreateServiceOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDeleteServiceOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPrintServiceOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        setBounds(0, 0, 729, 622);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateServiceOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateServiceOrderActionPerformed
        // TODO add your handling code here:
        createrServiceOrder();
    }//GEN-LAST:event_btnCreateServiceOrderActionPerformed

    private void btnUpdateServiceOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateServiceOrderActionPerformed
        // TODO add your handling code here:
        updateServiceOrder();
    }//GEN-LAST:event_btnUpdateServiceOrderActionPerformed

    private void btnReadServiceOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReadServiceOrderActionPerformed
        // TODO add your handling code here:
        readServiceOrder();
    }//GEN-LAST:event_btnReadServiceOrderActionPerformed

    private void btnDeleteServiceOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteServiceOrderActionPerformed
        // TODO add your handling code here:
        deleteServiceOrder();
    }//GEN-LAST:event_btnDeleteServiceOrderActionPerformed

    private void txtClientSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClientSearchKeyReleased
        // TODO add your handling code here:
        searchClient();
    }//GEN-LAST:event_txtClientSearchKeyReleased

    private void tblClientsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientsMouseClicked
        // TODO add your handling code here:
        setFieldsFromTable();
    }//GEN-LAST:event_tblClientsMouseClicked

    private void serviceOrderRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serviceOrderRadioActionPerformed
        // TODO add your handling code here:
        tipo = "Ordem de Serviço";

    }//GEN-LAST:event_serviceOrderRadioActionPerformed

    private void tenderRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenderRadioActionPerformed
        // TODO add your handling code here:
        tipo = "Orçamento";
    }//GEN-LAST:event_tenderRadioActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        //ao abrir o form marcar o radioButton orcamento
        tenderRadio.setSelected(true);
        tipo = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnPrintServiceOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintServiceOrderActionPerformed
        // TODO add your handling code here:
        printServiceOrder();
    }//GEN-LAST:event_btnPrintServiceOrderActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreateServiceOrder;
    private javax.swing.JButton btnDeleteServiceOrder;
    private javax.swing.JButton btnPrintServiceOrder;
    private javax.swing.JButton btnReadServiceOrder;
    private javax.swing.JButton btnUpdateServiceOrder;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel clientPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton serviceOrderRadio;
    private javax.swing.JComboBox<String> situationCombo;
    private javax.swing.JTable tblClients;
    private javax.swing.JRadioButton tenderRadio;
    private javax.swing.JTextField txtClientSearch;
    private javax.swing.JTextField txtEquipmentName;
    private javax.swing.JTextField txtIdClient;
    private javax.swing.JTextField txtIssueName;
    private javax.swing.JTextField txtOsValue;
    private javax.swing.JTextField txtServiceName;
    private javax.swing.JTextField txtServiceOrder;
    private javax.swing.JTextField txtServiceOrderDate;
    private javax.swing.JTextField txtTechnicianName;
    // End of variables declaration//GEN-END:variables
}
