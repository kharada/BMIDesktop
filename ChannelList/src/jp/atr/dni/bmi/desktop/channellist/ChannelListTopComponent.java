/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.atr.dni.bmi.desktop.channellist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import jp.atr.dni.bmi.desktop.model.Channel;
import jp.atr.dni.bmi.desktop.model.GeneralFileInfo;
import jp.atr.dni.bmi.desktop.model.Workspace;
import jp.atr.dni.bmi.desktop.neuroshareutils.NSReader;
import jp.atr.dni.bmi.desktop.neuroshareutils.NeuroshareFile;
import org.openide.util.LookupEvent;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.openide.util.ImageUtilities;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.util.Lookup;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//jp.atr.dni.bmi.desktop.channellist//ChannelList//EN",
autostore = false)
public final class ChannelListTopComponent extends TopComponent implements LookupListener {

    // Define Lists.
    DefaultListModel unSelectedChannelList = new DefaultListModel();
    DefaultListModel selectedChannelList = new DefaultListModel();
    private static ChannelListTopComponent instance;
    /** path to the icon used by the component and its open action */
    static final String ICON_PATH = "jp/atr/dni/bmi/desktop/channellist/choose.png";
    private static final String PREFERRED_ID = "ChannelListTopComponent";

    public ChannelListTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(ChannelListTopComponent.class, "CTL_ChannelListTopComponent"));
        setToolTipText(NbBundle.getMessage(ChannelListTopComponent.class, "HINT_ChannelListTopComponent"));
        setIcon(ImageUtilities.loadImage(ICON_PATH, true));



    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane(jList1);
        jList1 = new javax.swing.JList(unSelectedChannelList);
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jButton5 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane(jList2);
        jList2 = new javax.swing.JList(selectedChannelList);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setForeground(new java.awt.Color(153, 153, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ChannelListTopComponent.class, "ChannelListTopComponent.jLabel1.text")); // NOI18N

        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jList1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jList1.setModel(unSelectedChannelList);
        jList1.setValueIsAdjusting(true);
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 10.0;
        jPanel1.add(jPanel2, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(ChannelListTopComponent.class, "ChannelListTopComponent.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jButton1, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(ChannelListTopComponent.class, "ChannelListTopComponent.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jButton2, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jButton3, org.openide.util.NbBundle.getMessage(ChannelListTopComponent.class, "ChannelListTopComponent.jButton3.text")); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jButton3, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jButton4, org.openide.util.NbBundle.getMessage(ChannelListTopComponent.class, "ChannelListTopComponent.jButton4.text")); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jButton4, gridBagConstraints);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Neuroshare/Analog", "Neuroshare/Event", "Neuroshare/Segment", "Neuroshare/NeuralEvent" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jComboBox1, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jButton5, org.openide.util.NbBundle.getMessage(ChannelListTopComponent.class, "ChannelListTopComponent.jButton5.text")); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jButton5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel3, gridBagConstraints);

        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(ChannelListTopComponent.class, "ChannelListTopComponent.jLabel2.text")); // NOI18N

        jList2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jList2.setModel(selectedChannelList);
        jList2.setValueIsAdjusting(true);
        jScrollPane2.setViewportView(jList2);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 10.0;
        jPanel1.add(jPanel4, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Set all channels as UnSelected.
        if (selectedChannelList.isEmpty()) {
            return;
        }

        ArrayList<Channel> tmpChannelList = new ArrayList<Channel>();
        for (int ii = 0; ii < selectedChannelList.getSize(); ii++) {
            tmpChannelList.add((Channel) selectedChannelList.getElementAt(ii));
        }

        for (int jj = 0; jj < tmpChannelList.size(); jj++) {
            this.moveChannelToUnSelected(tmpChannelList.get(jj));
        }

        jList1.repaint();
        jList2.repaint();

        // fireselectedChannelListUpdated()
        // here.

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Set chosen channel as UnSelected.
        Object[] chosen = jList2.getSelectedValues();
        if (chosen == null) {
            // Any channel is not chosen.
            return;
        }

        for (int ii = 0; ii < chosen.length; ii++) {
            Channel ch = (Channel) chosen[ii];
            this.moveChannelToUnSelected(ch);
        }

        jList1.repaint();
        jList2.repaint();

        // fireselectedChannelListUpdated()
        // here.

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // Set chosen channel as Selected.
        Object[] chosen = jList1.getSelectedValues();
        if (chosen == null) {
            // Any channel is not chosen.
            return;
        }

        for (int ii = 0; ii < chosen.length; ii++) {
            Channel ch = (Channel) chosen[ii];
            this.moveChannelToSelected(ch);
        }

        jList1.repaint();
        jList2.repaint();

        // fireselectedChannelListUpdated()
        // here.

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // Set all channels as Selected.
        if (unSelectedChannelList.isEmpty()) {
            return;
        }

        ArrayList<Channel> tmpChannelList = new ArrayList<Channel>();
        for (int ii = 0; ii < unSelectedChannelList.getSize(); ii++) {
            tmpChannelList.add((Channel) unSelectedChannelList.getElementAt(ii));
        }

        for (int jj = 0; jj < tmpChannelList.size(); jj++) {
            this.moveChannelToSelected(tmpChannelList.get(jj));
        }

        jList1.repaint();
        jList2.repaint();

        // fireselectedChannelListUpdated()
        // here.

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        JOptionPane.showMessageDialog(null, "Add Channel to Workspace. UC.");
        JDialog jD = new JDialog();
        // Add Selected Channels to the Workspace.
        int length = selectedChannelList.size();
        for (int ii = 0; ii < length; ii++) {
            Workspace.addChannel((Channel)selectedChannelList.get(ii));
        }

        JOptionPane.showMessageDialog(null, "Channel Count On the Workspace : " + Workspace.channels.size());

    }//GEN-LAST:event_jButton5ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized ChannelListTopComponent getDefault() {
        if (instance == null) {
            instance = new ChannelListTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the ChannelListTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized ChannelListTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(ChannelListTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof ChannelListTopComponent) {
            return (ChannelListTopComponent) win;
        }
        Logger.getLogger(ChannelListTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }
    private Lookup.Result result = null;

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    public void componentOpened() {
        result = Utilities.actionsGlobalContext().lookupResult(GeneralFileInfo.class);
        result.addLookupListener(this);
    }

    @Override
    public void componentClosed() {
        result.removeLookupListener(this);
        result = null;
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    Object readProperties(java.util.Properties p) {
        if (instance == null) {
            instance = this;
        }
        instance.readPropertiesImpl(p);
        return instance;
    }

    private void readPropertiesImpl(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    @Override
    public void resultChanged(LookupEvent le) {
//       System.out.println("channel list top comp result chg");
        Lookup.Result r = (Lookup.Result) le.getSource();
        Collection c = r.allInstances();

        if (!c.isEmpty()) {
            for (Iterator i = c.iterator(); i.hasNext();) {
                GeneralFileInfo gfi = (GeneralFileInfo) i.next();
                if (gfi != null) {

                    // Case : Neuroshare.
                    if (gfi.getFileType().equals("File/nsn")) {
                        NeuroshareFile nsf = gfi.getNsObj();
                        if (nsf == null) {
                            // Get nsObj if unload.
                            NSReader nsr = new NSReader();
                            nsf = nsr.readNSFileOnlyInfo(gfi.getFilePath());
                            gfi.setNsObj(nsf);
                        }

                        // Clean all(remove all elements.)
                        unSelectedChannelList.removeAllElements();
                        selectedChannelList.removeAllElements();
                        // Add channels to unSelectedChannelList.
                        for (int ii = 0; ii < nsf.getEntities().size(); ii++) {
                            Channel ch = new Channel(ii, nsf.getEntities().get(ii));
                            unSelectedChannelList.add(ii, ch);
                        }
                    }
                }
            }
        }
    }

    private void moveChannelToSelected(Channel ch) {
        if (selectedChannelList.isEmpty()) {
            // Add to First.
            selectedChannelList.addElement(ch);
        } else {
            int selectedChannelListSize = selectedChannelList.getSize();
            for (int ii = 0; ii < selectedChannelListSize; ii++) {
                Channel search = (Channel) selectedChannelList.getElementAt(ii);
                int diff = ch.getChannelID() - search.getChannelID();

                if (diff < 0) {
                    // Insert at ii.
                    selectedChannelList.insertElementAt(ch, ii);
                    break;
                }

                if (selectedChannelListSize <= ii + 1) {
                    // Add to Last.
                    selectedChannelList.addElement(ch);
                    break;
                }
            }
        }

        unSelectedChannelList.removeElement(ch);
    }

    private void moveChannelToUnSelected(Channel ch) {
        if (unSelectedChannelList.isEmpty()) {
            // Add to First.
            unSelectedChannelList.addElement(ch);
        } else {
            int unSelectedChannelListSize = unSelectedChannelList.getSize();
            for (int ii = 0; ii < unSelectedChannelListSize; ii++) {
                Channel search = (Channel) unSelectedChannelList.getElementAt(ii);
                int diff = ch.getChannelID() - search.getChannelID();

                if (diff < 0) {
                    // Insert at ii.
                    unSelectedChannelList.insertElementAt(ch, ii);
                    break;
                }

                if (unSelectedChannelListSize <= ii + 1) {
                    // Add to Last.
                    unSelectedChannelList.addElement(ch);
                    break;
                }
            }
        }

        selectedChannelList.removeElement(ch);
    }
}
