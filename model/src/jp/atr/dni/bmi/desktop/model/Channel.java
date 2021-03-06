package jp.atr.dni.bmi.desktop.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.atr.dni.bmi.desktop.model.utils.ModelUtils;
import jp.atr.dni.bmi.desktop.neuroshareutils.AnalogData;
import jp.atr.dni.bmi.desktop.neuroshareutils.AnalogInfo;
import jp.atr.dni.bmi.desktop.neuroshareutils.Entity;
import jp.atr.dni.bmi.desktop.neuroshareutils.EventData;
import jp.atr.dni.bmi.desktop.neuroshareutils.EventInfo;
import jp.atr.dni.bmi.desktop.neuroshareutils.NeuralInfo;
import jp.atr.dni.bmi.desktop.neuroshareutils.SegmentData;
import jp.atr.dni.bmi.desktop.neuroshareutils.SegmentInfo;
import jp.atr.dni.bmi.desktop.neuroshareutils.SegmentSourceInfo;
import jp.atr.dni.bmi.desktop.neuroshareutils.TextEventData;

import jp.atr.dni.bmi.desktop.workingfileutils.*;
import jp.atr.dni.bmi.desktop.workingfileutils.TIHeader;
import jp.atr.dni.bmi.desktop.workingfileutils.TLData;
import jp.atr.dni.bmi.desktop.workingfileutils.TLHeader;
import jp.atr.dni.bmi.desktop.workingfileutils.TOData;
import jp.atr.dni.bmi.desktop.workingfileutils.TOHeader;

import jp.atr.dni.bmi.desktop.workingfileutils.TSHeader;

/**
 *
 * @author Keiji Harada [*1]</br>[*1] ATR Intl. Computational Neuroscience Labs, Decoding Group
 * @version 2011/04/22
 */
public class Channel<T> {

   /** Channel ID. system orders channels as this value.*/
   private int channelID;
   /** Display Name. system displays this value.*/
   private String label;
   /** Type of data */
   private ChannelType channelType;
   /** Source File Path*/
   private String sourceFilePath;
   /** Working File Path*/
   private String workingFilePath;
   
   // Edited[ true : is editted. false : is not editted. default : false.]
   private boolean edited;
   
   // Case : Neuroshare
   private Entity entity;

   // Constructor.
   /**
    *
    */
   public Channel() {
      this.channelID = -1;
      this.label = "";
      this.sourceFilePath = "";
      this.workingFilePath = "";
      this.edited = false;
   }

   // Constructor. Case : Neuroshare
   /**
    *
    * @param channelID
    * @param entity
    */
   public Channel(int channelID, Entity entity) {
      this.channelID = channelID;
      this.label = entity.getEntityInfo().getEntityLabel();
//        this.channelType = "Neuroshare/" + entity.getEntityInfo().getEntityTypeLabel();
      this.channelType = ModelUtils.getChannelTypeFromLong(entity.getEntityInfo().getEntityType());
      this.sourceFilePath = entity.getEntityInfo().getFilePath();
      this.workingFilePath = "";
      this.edited = false;
      this.entity = entity;
   }

   /**
    * @return the channelID
    */
   public int getChannelID() {
      return channelID;
   }

   /**
    * @param channelID the channelID to set
    */
//    public void setChannelID(int channelID) {
   //      int old = this.channelID;
   //    this.channelID = channelID;
   //    this.fire("channelID", old, this.channelID);
   // }
   /**
    * @return the displayName
    */
   public String getLabel() {
      return label;
   }

   /**
    * @param displayName the displayName to set
    */
   public void setLabel(String newLabel) {
      String old = label;
      label = newLabel;
      fire("label", old, label);
   }

   /**
    * @return the channelType
    */
   public ChannelType getChannelType() {
      return channelType;
   }

   /**
    * @param channelType the channelType to set
    */
//    public void setChannelType(String channelType) {
   //       String old = this.channelType;
   //      this.channelType = channelType;
   //     this.fire("channelType", old, this.channelType);
   // }
   /**
    * @return the entity
    */
   public Entity getEntity() {
      return entity;
   }

   /**
    * @param entity the entity to set
    */
   public void setEntity(Entity entity) {
      Entity old = this.entity;
      this.entity = entity;
      this.fire("entity", old, this.entity);
   }

   /**
    * @return the sourceFilePath
    */
   public String getSourceFilePath() {
      return sourceFilePath;
   }

   /**
    * @param sourceFilePath the sourceFilePath to set
    */
   public void setSourceFilePath(String sourceFilePath) {
      String old = this.sourceFilePath;
      this.sourceFilePath = sourceFilePath;
      this.fire("sourceFilePath", old, this.sourceFilePath);
   }

   /**
    * @return the workingFilePath
    */
   public String getWorkingFilePath() {
      return workingFilePath;
   }

   /**
    * @param workingFilePath the workingFilePath to set
    */
   public void setWorkingFilePath(String workingFilePath) {
      String old = this.workingFilePath;
      this.workingFilePath = workingFilePath;
      this.fire("workingFilePath", old, this.workingFilePath);
   }

   

   /**
    * Override is needed to display displayName on the ChannelList module.
    * @return displayName
    */
   @Override
   public String toString() {
      return label;
   }

   // Get Data methods.
   /**
    *
    * @return
    */
   public ArrayList<AnalogData> getNeuroshareAnalogData() {
      try {
         WorkingFileReader nsCsvReader = new WorkingFileReader();
         return nsCsvReader.getAnalogData(this.workingFilePath);
      } catch (IOException ex) {
         Logger.getLogger(Channel.class.getName()).log(Level.SEVERE, null, ex);
         return null;
      }
   }

   /**
    *
    * @return
    */
   public ArrayList<Double> getNeuroshareNeuralData() {
      try {
         WorkingFileReader nsCsvReader = new WorkingFileReader();
         return nsCsvReader.getNeuralData(this.workingFilePath);
      } catch (IOException ex) {
         Logger.getLogger(Channel.class.getName()).log(Level.SEVERE, null, ex);
         return null;
      }
   }

   /**
    *
    * @return
    */
   public ArrayList<TextEventData> getNeuroshareTextEventData() {
      try {
         WorkingFileReader nsCsvReader = new WorkingFileReader();
         return nsCsvReader.getTextEventData(this.workingFilePath);
      } catch (IOException ex) {
         Logger.getLogger(Channel.class.getName()).log(Level.SEVERE, null, ex);
         return null;
      }
   }

   /**
    *
    * @return
    */
   public SegmentData getNeuroshareSegmentData() {
      try {
         WorkingFileReader nsCsvReader = new WorkingFileReader();
         return nsCsvReader.getSegmentData(this.workingFilePath);
      } catch (IOException ex) {
         Logger.getLogger(Channel.class.getName()).log(Level.SEVERE, null, ex);
         return null;
      }
   }

   // Set Data methods.
   /**
    *
    * @param analogData
    * @return
    */
   public boolean setNeuroshareAnalogData(ArrayList<AnalogData> analogData) {
      WorkingFileWriter nsCsvWriter = new WorkingFileWriter();
      if (nsCsvWriter.overwriteTSFile(this.workingFilePath, analogData, this.entity)) {
         this.setEdited(true);
         return true;
      }
      return false;

   }

   /**
    *
    * @param eventData
    * @return
    */
   public boolean setNeuroshareEventData(ArrayList<EventData> eventData) {
      WorkingFileWriter nsCsvWriter = new WorkingFileWriter();
      Object eventObject = eventData.clone();
      if (nsCsvWriter.overwriteTLFile(this.workingFilePath, eventObject, this.entity)) {
         this.setEdited(true);
         return true;
      }
      return false;
   }

   /**
    *
    * @param segmentData
    * @return
    */
   public boolean setNeuroshareSegmentData(SegmentData segmentData) {
      WorkingFileWriter nsCsvWriter = new WorkingFileWriter();
      if (nsCsvWriter.overwriteTIFile(this.workingFilePath, segmentData, this.entity)) {
         this.setEdited(true);
         return true;
      }
      return false;
   }

   /**
    *
    * @param neuralData
    * @return
    */
   public boolean setNeuroshareNeuralData(ArrayList<Double> neuralData) {
      WorkingFileWriter nsCsvWriter = new WorkingFileWriter();
      if (nsCsvWriter.overwriteTOFile(this.workingFilePath, neuralData, this.entity)) {
         this.setEdited(true);
         return true;
      }
      return false;
   }

   /**
    *
    * @return
    */
   public TSHeader getTSHeader() {
      return convertEntityToTSHeader();
   }

   /**
    *
    * @return
    */
   public TOHeader getTOHeader() {
      return convertEntityToTOHeader();
   }

   /**
    *
    * @return
    */
   public TIHeader getTIHeader() {
      return convertEntityToTIHeader();
   }

   /**
    *
    * @return
    */
   public TLHeader getTLHeader() {
      return convertEntityToTLHeader();
   }

   /**
    *
    * @return
    */
   public TSData getTSData() {
      WorkingFileReader csvReader = new WorkingFileReader();
      return csvReader.getTSData(this.workingFilePath);
   }

   /**
    *
    * @return
    */
   public TOData getTOData() {
      WorkingFileReader csvReader = new WorkingFileReader();
      return csvReader.getTOData(this.workingFilePath);
   }

   /**
    *
    * @return
    */
   public TIData getTIData() {
      WorkingFileReader csvReader = new WorkingFileReader();
      return csvReader.getTIData(this.workingFilePath);
   }

   /**
    *
    * @return
    */
   public TLData getTLData() {
      WorkingFileReader csvReader = new WorkingFileReader();
      return csvReader.getTLData(this.workingFilePath);
   }

   /**
    *
    * @param data
    * @return
    */
   public boolean setTSData(TSData data) {
      WorkingFileWriter csvWriter = new WorkingFileWriter();
      Entity e = csvWriter.overwriteTSFile(this.workingFilePath, data, this.entity);
      if (e == null) {
         // Case : The workingFile does not exist(delete manually).
         // Case : It can not delete the workingFile(other module is editing it).
         // Case : IOException occur(format error).
         return false;
      }
      this.setEntity(e);
      this.edited = true;
      return true;
   }

   /**
    *
    * @param data
    * @return
    */
   public boolean setTOData(TOData data) {
      WorkingFileWriter csvWriter = new WorkingFileWriter();
      Entity e = csvWriter.overwriteTOFile(this.workingFilePath, data, this.entity);
      if (e == null) {
         // Case : The workingFile does not exist(delete manually).
         // Case : It can not delete the workingFile(other module is editing it).
         // Case : IOException occur(format error).
         return false;
      }
      this.setEntity(e);
      this.edited = true;
      return true;
   }

   /**
    *
    * @param data
    * @return
    */
   public boolean setTIData(TIData data) {
      WorkingFileWriter csvWriter = new WorkingFileWriter();
      Entity e = csvWriter.overwriteTIFile(this.workingFilePath, data, this.entity);
      if (e == null) {
         // Case : The workingFile does not exist(delete manually).
         // Case : It can not delete the workingFile(other module is editing it).
         // Case : IOException occur(format error).
         return false;
      }
      this.setEntity(e);
      this.edited = true;
      return true;
   }

   /**
    *
    * @param data
    * @return
    */
   public boolean setTLData(TLData data) {
      WorkingFileWriter csvWriter = new WorkingFileWriter();
      Entity e = csvWriter.overwriteTLFile(this.workingFilePath, data, this.entity);
      if (e == null) {
         // Case : The workingFile does not exist(delete manually).
         // Case : It can not delete the workingFile(other module is editing it).
         // Case : IOException occur(format error).
         return false;
      }
      this.setEntity(e);
      this.edited = true;
      return true;
   }

   // Channnel's header can not modify.
//    public void setTSHeader(TSHeader tsHeader) {
//        this.setEntity(convertTSHeaderToEntity(tsHeader));
//        this.editFlag = true;
//    }
   // Channnel's header can not modify.
//    public void setTOHeader(TOHeader toHeader) {
//        this.setEntity(convertTOHeaderToEntity(toHeader));
//        this.editFlag = true;
//    }
   // Channnel's header can not modify.
//    public void setTIHeader(TIHeader tiHeader) {
//        this.setEntity(convertTIHeaderToEntity(tiHeader));
//        this.editFlag = true;
//    }
   // Channnel's header can not modify.
//    public void setTLHeader(TSLeader tlHeader) {
//        this.setEntity(convertTLHeaderToEntity(tlHeader));
//        this.editFlag = true;
//    }
   private TSHeader convertEntityToTSHeader() {
      AnalogInfo ai = (AnalogInfo) entity;
      return new TSHeader(ai.getSampleRate(), ai.getMinVal(), ai.getMaxVal(), ai.getUnits(), ai.getResolution(), ai.getLocationX(), ai.getLocationY(), ai.getLocationZ(), ai.getLocationUser(), ai.getHighFreqCorner(), ((Long) ai.getHighFreqOrder()).intValue(), ai.getHighFilterType(), ai.getLowFreqCorner(), ((Long) ai.getLowFreqOrder()).intValue(), ai.getLowFilterType(), ai.getProbeInfo());
   }

   private TOHeader convertEntityToTOHeader() {
      NeuralInfo ni = (NeuralInfo) entity;
      return new TOHeader(((Long) ni.getSourceEntityID()).intValue(), ((Long) ni.getSourceUnitID()).intValue(), ni.getProbeInfo());
   }

   private TIHeader convertEntityToTIHeader() {
      SegmentInfo si = (SegmentInfo) entity;
      SegmentSourceInfo ssi = si.getSegSourceInfos().get(0);
      return new TIHeader(((Long) si.getSourceCount()).intValue(), ((Long) si.getMinSampleCount()).intValue(), ((Long) si.getMaxSampleCount()).intValue(), si.getSampleRate(), si.getUnits(), ssi.getMinVal(), ssi.getMaxVal(), ssi.getResolution(), ssi.getSubSampleShift(), ssi.getLocationX(), ssi.getLocationY(), ssi.getLocationZ(), ssi.getLocationUser(), ssi.getHighFreqCorner(), ((Long) ssi.getHighFreqOrder()).intValue(), ssi.getHighFilterType(), ssi.getLowFreqCorner(), ((Long) ssi.getLowFreqOrder()).intValue(), ssi.getLowFilterType(), ssi.getProbeInfo());
   }

   private TLHeader convertEntityToTLHeader() {
      EventInfo ei = (EventInfo) entity;
      return new TLHeader(((Long) ei.getEventType()).intValue(), ((Long) ei.getMinDataLength()).intValue(), ((Long) ei.getMaxDataLength()).intValue(), ei.getCsvDesc());
   }

   private Entity convertTSHeaderToEntity(TSHeader tsHeader) {
      AnalogInfo ai = (AnalogInfo) entity;
      ai.setSampleRate(tsHeader.getSamplingRate_Hz());
      ai.setMinVal(tsHeader.getMinValue());
      ai.setMaxVal(tsHeader.getMaxValue());
      ai.setUnits(tsHeader.getUnitOfValue());
      ai.setResolution(tsHeader.getResolution());
      ai.setLocationX(tsHeader.getLocationX_m());
      ai.setLocationY(tsHeader.getLocationY_m());
      ai.setLocationZ(tsHeader.getLocationZ_m());
      ai.setLocationUser(tsHeader.getProbeNumber());
      ai.setHighFreqCorner(tsHeader.getHighFreqCutoff_Hz());
      ai.setHighFreqOrder(tsHeader.getOrderOfHighFreqCutoff());
      ai.setHighFilterType(tsHeader.getCommentOfHighFreqCutoff());
      ai.setLowFreqCorner(tsHeader.getLowFreqCutoff_Hz());
      ai.setLowFreqOrder(tsHeader.getOrderOfLowFreqCutoff());
      ai.setLowFilterType(tsHeader.getCommentOfLowFreqCutoff());
      ai.setProbeInfo(tsHeader.getCommentOfThisProbe());
      return (Entity) ai;
   }

   private Entity convertTOHeaderToEntity(TOHeader toHeader) {
      NeuralInfo ni = (NeuralInfo) entity;
      ni.setSourceEntityID(toHeader.getSourceEntityID());
      ni.setSourceUnitID(toHeader.getSourceUnitID());
      ni.setProbeInfo(toHeader.getCommentAboutThisProbe());
      return (Entity) ni;
   }

   private Entity convertTIHeaderToEntity(TIHeader tiHeader) {
      SegmentInfo si = (SegmentInfo) entity;
      ArrayList<SegmentSourceInfo> segSourceInfos = si.getSegSourceInfos();
      SegmentSourceInfo ssi = segSourceInfos.get(0);
      si.setSourceCount(tiHeader.getSourceCount());
      si.setMinSampleCount(tiHeader.getMinSampleCount());
      si.setMaxSampleCount(tiHeader.getMaxSampleCount());
      si.setSampleRate(tiHeader.getSamplingRate_Hz());
      si.setUnits(tiHeader.getUnitOfValue());
      ssi.setMinVal(tiHeader.getMinValue());
      ssi.setMaxVal(tiHeader.getMaxValue());
      ssi.setResolution(tiHeader.getResolution());
      ssi.setLocationX(tiHeader.getLocationX_m());
      ssi.setLocationY(tiHeader.getLocationY_m());
      ssi.setLocationZ(tiHeader.getLocationZ_m());
      ssi.setLocationUser(tiHeader.getProbeNumber());
      ssi.setHighFreqCorner(tiHeader.getHighFreqCutoff_Hz());
      ssi.setHighFreqOrder(tiHeader.getOrderOfHighFreqCutoff());
      ssi.setHighFilterType(tiHeader.getCommentOfHighFreqCutoff());
      ssi.setLowFreqCorner(tiHeader.getLowFreqCutoff_Hz());
      ssi.setLowFreqOrder(tiHeader.getOrderOfLowFreqCutoff());
      ssi.setLowFilterType(tiHeader.getCommentOfLowFreqCutoff());
      ssi.setProbeInfo(tiHeader.getCommentOfThisProbe());
      segSourceInfos.set(0, ssi);
      si.setSegSourceInfos(segSourceInfos);
      return (Entity) si;
   }

   private Entity convertTLHeaderToEntity(TLHeader tlHeader) {
      EventInfo ei = (EventInfo) entity;
      ei.setEventType(tlHeader.getEventType());
      ei.setMinDataLength(tlHeader.getMinDataLength());
      ei.setMaxDataLength(tlHeader.getMaxDataLength());
      ei.setCsvDesc(tlHeader.getCommentAboutThisProbe());
      return (Entity) ei;
   }

    /**
     * @return the edited
     */
    public boolean isEdited() {
        return edited;
    }

    /**
     * @param edited the edited to set
     */
    public void setEdited(boolean edited) {
        this.edited = edited;
    }
    
    //Handle listeners
    
     private List listeners = Collections.synchronizedList(new LinkedList());

   /**
    *
    * @param pcl
    */
   public void addPropertyChangeListener(PropertyChangeListener pcl) {
      listeners.add(pcl);
   }

   /**
    *
    * @param pcl
    */
   public void removePropertyChangeListener(PropertyChangeListener pcl) {
      listeners.remove(pcl);
   }

   private void fire(String propertyName, Object old, Object neu) {
      PropertyChangeListener[] pcls = (PropertyChangeListener[]) listeners.toArray(new PropertyChangeListener[0]);
      for (int i = 0; i < pcls.length; i++) {
         pcls[i].propertyChange(new PropertyChangeEvent(this, propertyName, old, neu));
      }
   }
}
