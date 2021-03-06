/**
 * 
 */
package jp.atr.dni.bmi.desktop.neuroshareutils;

import java.util.logging.Logger;

/**
 *
 * @author Keiji Harada [*1]</br>[*1] ATR Intl. Conputational Neuroscience Labs, Decoding Group
 * @version 2011/04/22
 */
public class Ns_AnalogInfo {

    double dSampleRate;
    double dMinVal;
    double dMaxVal;
    String szUnits;
    double dResolution;
    double dLocationX;
    double dLocationY;
    double dLocationZ;
    double dLocationUser;
    double dHighFreqCorner;
    int dwHighFreqOrder;
    String szHighFilterType;
    double dLowFreqCorner;
    int dwLowFreqOrder;
    String szLowFilterType;
    String szProbeInfo;

    /**
     *
     */
    public Ns_AnalogInfo() {
        this.dSampleRate = 0;
        this.dMinVal = Double.MAX_VALUE;
        this.dMaxVal = Double.MIN_VALUE;
        this.szUnits = "";
        this.dResolution = 0;
        this.dLocationX = 0;
        this.dLocationY = 0;
        this.dLocationZ = 0;
        this.dLocationUser = 0;
        this.dHighFreqCorner = 0;
        this.dwHighFreqOrder = 0;
        this.szHighFilterType = "";
        this.dLowFreqCorner = 0;
        this.dwLowFreqOrder = 0;
        this.szLowFilterType = "";
        this.szProbeInfo = "";
    }

    /**
     * @return the dSampleRate
     */
    private double getDSampleRate() {
        return dSampleRate;
    }

    /**
     * @param dSampleRate the dSampleRate to set
     */
    private void setDSampleRate(double dSampleRate) {
        this.dSampleRate = dSampleRate;
    }

    /**
     * @return the dMinVal
     */
    public double getDMinVal() {
        return dMinVal;
    }

    /**
     * @param dMinVal the dMinVal to set
     */
    public void setDMinVal(double dMinVal) {
        this.dMinVal = dMinVal;
    }

    /**
     * @return the dMaxVal
     */
    public double getDMaxVal() {
        return dMaxVal;
    }

    /**
     * @param dMaxVal the dMaxVal to set
     */
    public void setDMaxVal(double dMaxVal) {
        this.dMaxVal = dMaxVal;
    }

    /**
     * @return the szUnits
     */
    private String getSzUnits() {
        return szUnits;
    }

    /**
     * @param szUnits the szUnits to set
     */
    private void setSzUnits(String szUnits) {
        this.szUnits = szUnits;
    }

    /**
     * @return the dResolution
     */
    private double getDResolution() {
        return dResolution;
    }

    /**
     * @param dResolution the dResolution to set
     */
    private void setDResolution(double dResolution) {
        this.dResolution = dResolution;
    }

    /**
     * @return the dLocationX
     */
    private double getDLocationX() {
        return dLocationX;
    }

    /**
     * @param dLocationX the dLocationX to set
     */
    private void setDLocationX(double dLocationX) {
        this.dLocationX = dLocationX;
    }

    /**
     * @return the dLocationY
     */
    private double getDLocationY() {
        return dLocationY;
    }

    /**
     * @param dLocationY the dLocationY to set
     */
    private void setDLocationY(double dLocationY) {
        this.dLocationY = dLocationY;
    }

    /**
     * @return the dLocationZ
     */
    private double getDLocationZ() {
        return dLocationZ;
    }

    /**
     * @param dLocationZ the dLocationZ to set
     */
    private void setDLocationZ(double dLocationZ) {
        this.dLocationZ = dLocationZ;
    }

    /**
     * @return the dLocationUser
     */
    private double getDLocationUser() {
        return dLocationUser;
    }

    /**
     * @param dLocationUser the dLocationUser to set
     */
    private void setDLocationUser(double dLocationUser) {
        this.dLocationUser = dLocationUser;
    }

    /**
     * @return the dHighFreqCorner
     */
    private double getDHighFreqCorner() {
        return dHighFreqCorner;
    }

    /**
     * @param dHighFreqCorner the dHighFreqCorner to set
     */
    private void setDHighFreqCorner(double dHighFreqCorner) {
        this.dHighFreqCorner = dHighFreqCorner;
    }

    /**
     * @return the dwHighFreqOrder
     */
    private int getDwHighFreqOrder() {
        return dwHighFreqOrder;
    }

    /**
     * @param dwHighFreqOrder the dwHighFreqOrder to set
     */
    private void setDwHighFreqOrder(int dwHighFreqOrder) {
        if (dwHighFreqOrder >= 0) {
            this.dwHighFreqOrder = dwHighFreqOrder;
        } else {
            // Display message.
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info(
                    new Const_messages().getOutOfRange((double) dwHighFreqOrder));
        }
    }

    /**
     * @return the szHighFilterType
     */
    private String getSzHighFilterType() {
        return szHighFilterType;
    }

    /**
     * @param szHighFilterType the szHighFilterType to set
     */
    private void setSzHighFilterType(String szHighFilterType) {
        this.szHighFilterType = szHighFilterType;
    }

    /**
     * @return the dLowFreqCorner
     */
    private double getDLowFreqCorner() {
        return dLowFreqCorner;
    }

    /**
     * @param dLowFreqCorner the dLowFreqCorner to set
     */
    private void setDLowFreqCorner(double dLowFreqCorner) {
        this.dLowFreqCorner = dLowFreqCorner;
    }

    /**
     * @return the dwLowFreqOrder
     */
    private int getDwLowFreqOrder() {
        return dwLowFreqOrder;
    }

    /**
     * @param dwLowFreqOrder the dwLowFreqOrder to set
     */
    private void setDwLowFreqOrder(int dwLowFreqOrder) {
        if (dwLowFreqOrder >= 0) {
            this.dwLowFreqOrder = dwLowFreqOrder;
        } else {
            // Display message.
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info(
                    new Const_messages().getOutOfRange((double) dwLowFreqOrder));
        }
    }

    /**
     * @return the szLowFilterType
     */
    private String getSzLowFilterType() {
        return szLowFilterType;
    }

    /**
     * @param szLowFilterType the szLowFilterType to set
     */
    private void setSzLowFilterType(String szLowFilterType) {
        this.szLowFilterType = szLowFilterType;
    }

    /**
     * @return the szProbeInfo
     */
    private String getSzProbeInfo() {
        return szProbeInfo;
    }

    /**
     * @param szProbeInfo the szProbeInfo to set
     */
    private void setSzProbeInfo(String szProbeInfo) {
        this.szProbeInfo = szProbeInfo;
    }

    /**
     * @return members
     */
    public Nsa_AnalogInfo getMembers() {
        return new Nsa_AnalogInfo(this.getDSampleRate(), this.getDMinVal(), this.getDMaxVal(),
                this.getSzUnits(), this.getDResolution(), this.getDLocationX(), this.getDLocationY(), this.getDLocationZ(), this.getDLocationUser(), this.getDHighFreqCorner(), this.getDwHighFreqOrder(), this.getSzHighFilterType(), this.getDLowFreqCorner(), this.getDwLowFreqOrder(), this.getSzLowFilterType(), this.getSzProbeInfo());
    }

    /**
     *
     * @param nsaAnalogInfo
     * @return
     */
    public int setMembers(Nsa_AnalogInfo nsaAnalogInfo) {
        this.setDSampleRate(nsaAnalogInfo.getDSampleRate());
        this.setDMinVal(nsaAnalogInfo.getDMinVal());
        this.setDMaxVal(nsaAnalogInfo.getDMaxVal());
        this.setSzUnits(nsaAnalogInfo.getSzUnits());
        this.setDResolution(nsaAnalogInfo.getDResolution());
        this.setDLocationX(nsaAnalogInfo.getDLocationX());
        this.setDLocationY(nsaAnalogInfo.getDLocationY());
        this.setDLocationZ(nsaAnalogInfo.getDLocationZ());
        this.setDLocationUser(nsaAnalogInfo.getDLocationUser());
        this.setDHighFreqCorner(nsaAnalogInfo.getDHighFreqCorner());
        this.setDwHighFreqOrder(nsaAnalogInfo.getDwHighFreqOrder());
        this.setSzHighFilterType(nsaAnalogInfo.getSzHighFilterType());
        this.setDLowFreqCorner(nsaAnalogInfo.getDLowFreqCorner());
        this.setDwLowFreqOrder(nsaAnalogInfo.getDwLowFreqOrder());
        this.setSzLowFilterType(nsaAnalogInfo.getSzLowFilterType());
        this.setSzProbeInfo(nsaAnalogInfo.getSzProbeInfo());
        return Const_values.NS_OK;
    }
}
