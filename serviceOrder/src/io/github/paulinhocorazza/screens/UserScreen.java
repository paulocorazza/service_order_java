/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.paulinhocorazza.screens;

import java.sql.*;
import io.github.paulinhocorazza.dal.DatabaseConnection;
import java.awt.Color;
import javax.swing.JOptionPane;

/**
 *
 * @author paulo.corazza
 */
public class UserScreen extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form UserScreen
     */
    public UserScreen() {
        initComponents();
        conexao = DatabaseConnection.conector();
    }

    //criar usuario
    private void createUser() {
        String sql = "insert into tb_usuarios(id,usuario,usuario_cargo,usuario_perfil,login,senha) values (?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtIdUser.getText());
            pst.setString(2, txtUserName.getText());
            pst.setString(3, comboUserRole.getSelectedItem().toString());
            pst.setString(4, comboUserProfile.getSelectedItem().toString());
            pst.setString(5, txtUserLogin.getText());
            pst.setString(6, txtUserPassword.getText());
            //linha abaixo atualiza o banco
            //validacao do banco
            if(txtIdUser.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Preencha o campo ID !");
            }
            if(txtUserName.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"Preencha o campo Nome !");
            }
            if(txtUserLogin.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Preencha o campo Login !");
            }
            if(txtUserPassword.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"Preencha o campo senha !");
            }
            else {

                int addedUser = pst.executeUpdate();
                if (addedUser > 0) {
                    JOptionPane.showMessageDialog(null, "Usuario cadastrado com sucesso !");
                    txtIdUser.setText("");
                    txtUserName.setText("");
                    txtUserLogin.setText("");
                    txtUserPassword.setText("");
                    comboUserRole.setSelectedItem(null);
                    comboUserProfile.setSelectedItem(null);

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Algo deu errado, erro: " + e);
        }

    }

    //consultar usuario
    private void readUser() {
        String sql = "select * from tb_usuarios where id=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtIdUser.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtUserName.setText(rs.getString(2));
                comboUserRole.setSelectedItem(rs.getString(3));
                comboUserProfile.setSelectedItem(rs.getString(4));
                txtUserLogin.setText(rs.getString(5));
                txtUserPassword.setText(rs.getString(6));

            } else {
                JOptionPane.showMessageDialog(null, "Usuario nao cadastrado !");
                txtUserName.setText("");
                txtUserLogin.setText("");
                txtUserPassword.setText("");
                comboUserRole.setSelectedItem(null);
                comboUserProfile.setSelectedItem(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //atualizar usuario
    private void updateUser() {
        String sql = "update tb_usuarios set usuario=?, usuario_cargo=?, usuario_perfil=?, login=?, senha=? where id=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUserName.getText());
            pst.setString(2, comboUserRole.getSelectedItem().toString());
            pst.setString(3, comboUserProfile.getSelectedItem().toString());
            pst.setString(4, txtUserLogin.getText());
            pst.setString(5, txtUserPassword.getText());
            pst.setString(6, txtIdUser.getText());

            if (txtIdUser.getText().isEmpty() || (txtUserName.getText().isEmpty()) || (txtUserLogin.getText().isEmpty()) || (txtUserPassword.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos !");
            } else {

                int updatedUser = pst.executeUpdate();

                if (updatedUser > 0) {
                    JOptionPane.showMessageDialog(null, "Usuario alterado com sucesso !");
                    txtIdUser.setText("");
                    txtUserName.setText("");
                    txtUserLogin.setText("");
                    txtUserPassword.setText("");
                    comboUserRole.setSelectedItem(null);
                    comboUserProfile.setSelectedItem(null);

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro:" + e);
        }
    }

    //delete user
    public void deleteUser() {

        int accept = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o usuario " + txtUserName.getText() + " ?", "Atencao", JOptionPane.YES_NO_OPTION);
        if (accept == JOptionPane.YES_OPTION) {
            String sql = "delete from tb_usuarios where id=?";

            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtIdUser.getText());
                int deleted = pst.executeUpdate();

                if (deleted > 0) {
                    JOptionPane.showMessageDialog(null, "O usuario " + txtUserName.getText() + "foi removido com sucesso !");
                    txtIdUser.setText("");
                    txtUserName.setText("");
                    txtUserLogin.setText("");
                    txtUserPassword.setText("");
                    comboUserRole.setSelectedItem(null);
                    comboUserProfile.setSelectedItem(null);

                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro: " + e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Operação Cancelada");
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

        userName = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        comboUserProfile = new javax.swing.JComboBox();
        txtUserName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtUserLogin = new javax.swing.JTextField();
        txtUserPassword = new javax.swing.JPasswordField();
        comboUserRole = new javax.swing.JComboBox();
        btnCreateUser = new javax.swing.JButton();
        btnReadUser = new javax.swing.JButton();
        btnDeleteUser = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtIdUser = new javax.swing.JTextField();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        userName1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        comboUserProfile1 = new javax.swing.JComboBox();
        txtUserName1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtUserLogin1 = new javax.swing.JTextField();
        btnEditUser1 = new javax.swing.JButton();
        txtUserPassword1 = new javax.swing.JPasswordField();
        comboUserRole1 = new javax.swing.JComboBox();
        btnCreateUser1 = new javax.swing.JButton();
        btnReadUser1 = new javax.swing.JButton();
        btnDeleteUser1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtIdUser1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnUpdateUser = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Usuários");
        setMaximumSize(new java.awt.Dimension(916, 650));
        setPreferredSize(new java.awt.Dimension(915, 650));

        userName.setText("* Nome:");

        jLabel3.setText("* Cargo:");

        jLabel4.setText("* Senha:");

        jLabel5.setText("* Perfil de usuário:");

        comboUserProfile.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "admin", "restrito" }));

        txtUserName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserNameActionPerformed(evt);
            }
        });

        jLabel6.setText("* Login:");

        txtUserPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserPasswordActionPerformed(evt);
            }
        });

        comboUserRole.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "administrador", "técnico", "atendente" }));
        comboUserRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboUserRoleActionPerformed(evt);
            }
        });

        btnCreateUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/github/paulinhocorazza/icons/create.png"))); // NOI18N
        btnCreateUser.setToolTipText("Adicionar Usuário");
        btnCreateUser.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCreateUser.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCreateUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateUserActionPerformed(evt);
            }
        });

        btnReadUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/github/paulinhocorazza/icons/read.png"))); // NOI18N
        btnReadUser.setToolTipText("Consultar Usuário");
        btnReadUser.setPreferredSize(new java.awt.Dimension(80, 80));
        btnReadUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReadUserActionPerformed(evt);
            }
        });

        btnDeleteUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/github/paulinhocorazza/icons/delete.png"))); // NOI18N
        btnDeleteUser.setToolTipText("Excluir Usuário");
        btnDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteUserActionPerformed(evt);
            }
        });

        jLabel1.setText("* ID:");

        jInternalFrame1.setClosable(true);
        jInternalFrame1.setIconifiable(true);
        jInternalFrame1.setMaximizable(true);
        jInternalFrame1.setResizable(true);
        jInternalFrame1.setTitle("Usuários");
        jInternalFrame1.setMaximumSize(new java.awt.Dimension(916, 650));
        jInternalFrame1.setPreferredSize(new java.awt.Dimension(915, 650));

        userName1.setText("Nome:");

        jLabel7.setText("Cargo:");

        jLabel8.setText("Senha:");

        jLabel9.setText("Perfil de usuário:");

        comboUserProfile1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "admin", "restrito" }));

        txtUserName1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserName1ActionPerformed(evt);
            }
        });

        jLabel10.setText("Login:");

        btnEditUser1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/github/paulinhocorazza/icons/update.png"))); // NOI18N
        btnEditUser1.setToolTipText("Editar Usuário");
        btnEditUser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditUser1ActionPerformed(evt);
            }
        });

        txtUserPassword1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserPassword1ActionPerformed(evt);
            }
        });

        comboUserRole1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "administrador", "técnico", "atendente" }));
        comboUserRole1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboUserRole1ActionPerformed(evt);
            }
        });

        btnCreateUser1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/github/paulinhocorazza/icons/create.png"))); // NOI18N
        btnCreateUser1.setToolTipText("Adicionar Usuário");
        btnCreateUser1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCreateUser1.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCreateUser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateUser1ActionPerformed(evt);
            }
        });

        btnReadUser1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/github/paulinhocorazza/icons/read.png"))); // NOI18N
        btnReadUser1.setToolTipText("Consultar Usuário");
        btnReadUser1.setPreferredSize(new java.awt.Dimension(80, 80));
        btnReadUser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReadUser1ActionPerformed(evt);
            }
        });

        btnDeleteUser1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/github/paulinhocorazza/icons/delete.png"))); // NOI18N
        btnDeleteUser1.setToolTipText("Excluir Usuário");
        btnDeleteUser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteUser1ActionPerformed(evt);
            }
        });

        jLabel2.setText("ID:");

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(comboUserRole1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(userName1)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel2))
                                .addGap(9, 9, 9)
                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(txtUserName1, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                                        .addComponent(txtUserLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                                .addComponent(jLabel9)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(comboUserProfile1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtUserPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                        .addComponent(txtIdUser1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCreateUser1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditUser1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReadUser1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteUser1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85))))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtIdUser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userName1)
                    .addComponent(txtUserName1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtUserLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtUserPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(comboUserRole1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(comboUserProfile1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(118, 118, 118)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnCreateUser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEditUser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnReadUser1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteUser1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel11.setBackground(new java.awt.Color(204, 0, 0));
        jLabel11.setText("* Campos Obrigatorios");

        btnUpdateUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/github/paulinhocorazza/icons/update.png"))); // NOI18N
        btnUpdateUser.setToolTipText("Editar Usuário");
        btnUpdateUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(comboUserRole, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(userName)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel1))
                                .addGap(9, 9, 9)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(txtUserLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(comboUserProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtUserPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtIdUser, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 2, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addGap(541, 541, 541))))
            .addGroup(layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(btnCreateUser, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUpdateUser, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReadUser, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDeleteUser, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(355, Short.MAX_VALUE)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(375, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIdUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userName)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtUserLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtUserPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comboUserRole, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(comboUserProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDeleteUser, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCreateUser, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateUser, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReadUser, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(67, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(299, Short.MAX_VALUE)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(299, Short.MAX_VALUE)))
        );

        setBounds(0, 0, 732, 621);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUserNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserNameActionPerformed

    private void comboUserRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboUserRoleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboUserRoleActionPerformed

    private void btnCreateUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateUserActionPerformed
        // TODO add your handling code here:
        createUser();
    }//GEN-LAST:event_btnCreateUserActionPerformed

    private void btnReadUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReadUserActionPerformed
        // TODO add your handling code here:
        readUser();
    }//GEN-LAST:event_btnReadUserActionPerformed

    private void btnDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUserActionPerformed
        // TODO add your handling code here:
        deleteUser();
    }//GEN-LAST:event_btnDeleteUserActionPerformed

    private void txtUserPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserPasswordActionPerformed

    private void txtUserName1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserName1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserName1ActionPerformed

    private void btnEditUser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditUser1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnEditUser1ActionPerformed

    private void txtUserPassword1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserPassword1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserPassword1ActionPerformed

    private void comboUserRole1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboUserRole1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboUserRole1ActionPerformed

    private void btnCreateUser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateUser1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCreateUser1ActionPerformed

    private void btnReadUser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReadUser1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnReadUser1ActionPerformed

    private void btnDeleteUser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUser1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteUser1ActionPerformed

    private void btnUpdateUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateUserActionPerformed
        // TODO add your handling code here:
        updateUser();
    }//GEN-LAST:event_btnUpdateUserActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreateUser;
    private javax.swing.JButton btnCreateUser1;
    private javax.swing.JButton btnDeleteUser;
    private javax.swing.JButton btnDeleteUser1;
    private javax.swing.JButton btnEditUser1;
    private javax.swing.JButton btnReadUser;
    private javax.swing.JButton btnReadUser1;
    private javax.swing.JButton btnUpdateUser;
    private javax.swing.JComboBox comboUserProfile;
    private javax.swing.JComboBox comboUserProfile1;
    private javax.swing.JComboBox comboUserRole;
    private javax.swing.JComboBox comboUserRole1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtIdUser;
    private javax.swing.JTextField txtIdUser1;
    private javax.swing.JTextField txtUserLogin;
    private javax.swing.JTextField txtUserLogin1;
    private javax.swing.JTextField txtUserName;
    private javax.swing.JTextField txtUserName1;
    private javax.swing.JPasswordField txtUserPassword;
    private javax.swing.JPasswordField txtUserPassword1;
    private javax.swing.JLabel userName;
    private javax.swing.JLabel userName1;
    // End of variables declaration//GEN-END:variables
}
