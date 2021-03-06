/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.atr.dni.bmi.desktop.dummymodule3;

import java.util.ArrayList;
import java.util.logging.Logger;
import jp.atr.dni.bmi.desktop.model.Channel;
import jp.atr.dni.bmi.desktop.model.Workspace;
import jp.atr.dni.bmi.desktop.workingfileutils.TIData;
import jp.atr.dni.bmi.desktop.workingfileutils.TIHeader;
import jp.atr.dni.bmi.desktop.workingfileutils.TLData;
import jp.atr.dni.bmi.desktop.workingfileutils.TLHeader;
import jp.atr.dni.bmi.desktop.workingfileutils.TOData;
import jp.atr.dni.bmi.desktop.workingfileutils.TOHeader;
import jp.atr.dni.bmi.desktop.workingfileutils.TSData;
import jp.atr.dni.bmi.desktop.workingfileutils.TSHeader;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.ImageUtilities;
import org.netbeans.api.settings.ConvertAsProperties;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//jp.atr.dni.bmi.desktop.dummymodule3//DummyModule3//EN",
autostore = false)
public final class DummyModule3TopComponent extends TopComponent {

    private TSHeader tsHeader = new TSHeader();
    private TOHeader toHeader = new TOHeader();
    private TIHeader tiHeader = new TIHeader();
    private TLHeader tlHeader = new TLHeader();
    private TSData tsData = new TSData();
    private TOData toData = new TOData();
    private TIData tiData = new TIData();
    private TLData tlData = new TLData();
    private Channel chn = new Channel();
    private static DummyModule3TopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "DummyModule3TopComponent";

    public DummyModule3TopComponent() {
        initComponents();
        setName(NbBundle.getMessage(DummyModule3TopComponent.class, "CTL_DummyModule3TopComponent"));
        setToolTipText(NbBundle.getMessage(DummyModule3TopComponent.class, "HINT_DummyModule3TopComponent"));
//        setIcon(ImageUtilities.loadImage(ICON_PATH, true));



    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(DummyModule3TopComponent.class, "DummyModule3TopComponent.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(DummyModule3TopComponent.class, "DummyModule3TopComponent.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton3, org.openide.util.NbBundle.getMessage(DummyModule3TopComponent.class, "DummyModule3TopComponent.jButton3.text")); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton4, org.openide.util.NbBundle.getMessage(DummyModule3TopComponent.class, "DummyModule3TopComponent.jButton4.text")); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton5, org.openide.util.NbBundle.getMessage(DummyModule3TopComponent.class, "DummyModule3TopComponent.jButton5.text")); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton6, org.openide.util.NbBundle.getMessage(DummyModule3TopComponent.class, "DummyModule3TopComponent.jButton6.text")); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton7, org.openide.util.NbBundle.getMessage(DummyModule3TopComponent.class, "DummyModule3TopComponent.jButton7.text")); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton8, org.openide.util.NbBundle.getMessage(DummyModule3TopComponent.class, "DummyModule3TopComponent.jButton8.text")); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton8))
                .addContainerGap(58, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Channel ch = Workspace.getChannels().get(0);
        if (ch != null) {
            chn = ch;
            tsHeader = ch.getTSHeader();
            tsData = ch.getTSData();

            // Get ch's header and ch's data.
            jTextArea1.setText("[ Header : probeInfo : " + tsHeader.getCommentOfThisProbe() + "] [ DATA : " + "samplingRate :" + tsData.getSamplingRate() + ",timestampsize : " + tsData.getTimeStamps().size() + ",timestamp[0] : " + tsData.getTimeStamp(0) + ",dataValues[0][0] : " + tsData.getValue(0, 0) + "]");

        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        ArrayList<Channel> chs = new ArrayList<Channel>();

        tsData.setValue(0, 0, 200);

        chn.setTSData(tsData);
        chs.add(chn);
        Workspace.setChannels(chs);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Channel ch = Workspace.getChannels().get(0);
        if (ch != null) {
            chn = ch;
            toHeader = ch.getTOHeader();
            toData = ch.getTOData();

            // Get ch's header and ch's data.
            jTextArea1.setText("[ Header : probeInfo : " + toHeader.getCommentAboutThisProbe() + "] [ DATA : " + "timestampsize : " + toData.getTimeStamps().size() + ",timestamp[0] : " + toData.getTimeStamp(0) + "]");

        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        ArrayList<Channel> chs = new ArrayList<Channel>();

        toData.setTimeStamp(0, 200);

        chn.setTOData(toData);
        chs.add(chn);
        Workspace.setChannels(chs);

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        Channel ch = Workspace.getChannels().get(0);
        if (ch != null) {
            chn = ch;
            tiHeader = ch.getTIHeader();
            tiData = ch.getTIData();

            // Get ch's header and ch's data.
            jTextArea1.setText("[ Header : probeInfo : " + tiHeader.getCommentOfThisProbe() + "] [ DATA : " + "samplingRate :" + tiData.getSamplingRate() + ",timestampsize : " + tiData.getTimeStamps().size() + ",timestamp[0] : " + tiData.getTimeStamp(0) + ",dataValues[0][0] : " + tiData.getValue(0, 0) + "]");

        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        ArrayList<Channel> chs = new ArrayList<Channel>();

        tiData.setValue(0, 0, 200);

        chn.setTIData(tiData);
        chs.add(chn);
        Workspace.setChannels(chs);

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        Channel ch = Workspace.getChannels().get(0);
        if (ch != null) {
            chn = ch;
            tlHeader = ch.getTLHeader();
            tlData = ch.getTLData();

            // Get ch's header and ch's data.
            jTextArea1.setText("[ Header : probeInfo : " + tlHeader.getCommentAboutThisProbe() + "] [ DATA : " + "timestampsize : " + tlData.getTimeStamps().size() + ",timestamp[0] : " + tlData.getTimeStamp(0) + ",value[0] : " + tlData.getValue(0).toString() + "]");

        }

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        ArrayList<Channel> chs = new ArrayList<Channel>();

        tlData.setValue(0, "Harada");

        chn.setTLData(tlData);
        chs.add(chn);
        Workspace.setChannels(chs);


    }//GEN-LAST:event_jButton8ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized DummyModule3TopComponent getDefault() {
        if (instance == null) {
            instance = new DummyModule3TopComponent();
        }
        return instance;
    }

    /**
     * Obtain the DummyModule3TopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized DummyModule3TopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(DummyModule3TopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof DummyModule3TopComponent) {
            return (DummyModule3TopComponent) win;
        }
        Logger.getLogger(DummyModule3TopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
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
}
