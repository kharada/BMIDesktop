/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.atr.dni.bmi.desktop.workingfileutils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import jp.atr.dni.bmi.desktop.neuroshareutils.AnalogData;
import jp.atr.dni.bmi.desktop.neuroshareutils.AnalogInfo;
import jp.atr.dni.bmi.desktop.neuroshareutils.ByteEventData;
import jp.atr.dni.bmi.desktop.neuroshareutils.Const_values;
import jp.atr.dni.bmi.desktop.neuroshareutils.CsvReader;
import jp.atr.dni.bmi.desktop.neuroshareutils.DWordEventData;
import jp.atr.dni.bmi.desktop.neuroshareutils.Entity;
import jp.atr.dni.bmi.desktop.neuroshareutils.EntityInfo;
import jp.atr.dni.bmi.desktop.neuroshareutils.EventInfo;
import jp.atr.dni.bmi.desktop.neuroshareutils.NSReader;
import jp.atr.dni.bmi.desktop.neuroshareutils.NevReader;
import jp.atr.dni.bmi.desktop.neuroshareutils.NsxReader;
import jp.atr.dni.bmi.desktop.neuroshareutils.PlxReader;
import jp.atr.dni.bmi.desktop.neuroshareutils.SegmentData;
import jp.atr.dni.bmi.desktop.neuroshareutils.SegmentInfo;
import jp.atr.dni.bmi.desktop.neuroshareutils.SegmentSourceInfo;
import jp.atr.dni.bmi.desktop.neuroshareutils.Tag;
import jp.atr.dni.bmi.desktop.neuroshareutils.TextEventData;
import jp.atr.dni.bmi.desktop.neuroshareutils.WordEventData;

/**
 *
 * @author Keiji Harada [*1]</br>[*1] ATR Intl. Conputational Neuroscience Labs, Decoding Group
 * @version 2011/04/22
 */
public class WorkingFileWriter {

   void createWorkingFileFromNeuroshare(String workingFilePath, String sourceFilePath, Entity entity) {
      int entityType = (int) entity.getEntityInfo().getEntityType();
      switch (entityType) {
         case 0:
            break;
         case 1:
            // Event
            createTLFileFromNeuroshare(workingFilePath, sourceFilePath, entity);
            break;
         case 2:
            // Analog
            createTSFileFromNeuroshare(workingFilePath, sourceFilePath, entity);
            break;
         case 3:
            // Segment
            createTIFileFromNeuroshare(workingFilePath, sourceFilePath, entity);
            break;
         case 4:
            // Neural
            createTOFileFromNeuroshare(workingFilePath, sourceFilePath, entity);
            break;
         case 5:
            break;
         default:
            break;
      }
   }

   void createWorkingFileFromPlexon(String workingFilePath, String sourceFilePath, Entity entity) {
      int entityType = (int) entity.getEntityInfo().getEntityType();
      switch (entityType) {
         case 0:
            break;
         case 1:
            // Event
            // No case
            //createTLFileFromPlexon(workingFilePath, sourceFilePath, entity);
            break;
         case 2:
            // Analog
            // No case
            //createTSFileFromPlexon(workingFilePath, sourceFilePath, entity);
            break;
         case 3:
            // Segment
            createTIFileFromPlexon(workingFilePath, sourceFilePath, entity);
            break;
         case 4:
            // Neural
            createTOFileFromPlexon(workingFilePath, sourceFilePath, entity);
            break;
         case 5:
            break;
         default:
            break;
      }
   }

   void createWorkingFileFromBlackRockNev(String workingFilePath, String sourceFilePath, Entity entity) {
      int entityType = (int) entity.getEntityInfo().getEntityType();
      switch (entityType) {
         case 0:
            break;
         case 1:
            // Event
            createTLFileFromBlackRockNev(workingFilePath, sourceFilePath, entity);
            break;
         case 2:
            // Analog
            // No case
            //createTSFileFromBlackRockNev(workingFilePath, sourceFilePath, entity);
            break;
         case 3:
            // Segment
            createTIFileFromBlackRockNev(workingFilePath, sourceFilePath, entity);
            break;
         case 4:
            // Neural
            createTOFileFromBlackRockNev(workingFilePath, sourceFilePath, entity);
            break;
         case 5:
            break;
         default:
            break;
      }
   }

   void createWorkingFileFromATRCsv(String workingFilePath, String sourceFilePath, Entity entity) {
      int entityType = (int) entity.getEntityInfo().getEntityType();
      switch (entityType) {
         case 0:
            break;
         case 1:
            // Event
            createTLFileFromATRCsv(workingFilePath, sourceFilePath, entity);
            break;
         case 2:
            // Analog
            createTSFileFromATRCsv(workingFilePath, sourceFilePath, entity);
            break;
         case 3:
            // Segment
            createTIFileFromATRCsv(workingFilePath, sourceFilePath, entity);
            break;
         case 4:
            // Neural
            createTOFileFromATRCsv(workingFilePath, sourceFilePath, entity);
            break;
         case 5:
            break;
         default:
            break;
      }
   }

   void createWorkingFileFromBlackRockNsx(String workingFilePath, String sourceFilePath, Entity entity) {
      int entityType = (int) entity.getEntityInfo().getEntityType();
      switch (entityType) {
         case 0:
            break;
         case 1:
            // Event
            // No case.
            //createTLFileFromBlackRockNsx(workingFilePath, sourceFilePath, entity);
            break;
         case 2:
            // Analog
            createTSFileFromBlackRockNsx(workingFilePath, sourceFilePath, entity);
            break;
         case 3:
            // Segment
            // No case.
            //createTIFileFromBlackRockNsx(workingFilePath, sourceFilePath, entity);
            break;
         case 4:
            // Neural
            // No case.
            //createTOFileFromBlackRockNsx(workingFilePath, sourceFilePath, entity);
            break;
         case 5:
            break;
         default:
            break;
      }
   }

   private void createTSFileFromNeuroshare(String workingFilePath, String sourceFilePath, Entity entity) {

      String co = ",";
      String formatCode = "TS";
      double samplingRate = ((AnalogInfo) entity).getSampleRate();
      BufferedWriter bw = null;

      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TS, <SamplingRate>
         bw.write(formatCode + co + samplingRate);
         bw.newLine();

         // Read Neuroshare Data.
         NSReader nsReader = new NSReader();
         ArrayList<AnalogData> analogData = nsReader.getAnalogData(sourceFilePath, entity.getEntityInfo());

         // Write Data to the workingFile(CSV).
         for (int ii = 0; ii < analogData.size(); ii++) {

            // 2nd Line : <timestamp>, <AnalogValue[0]>, <AnalogValue[1]>, ... <AnalogValue[n]>
            AnalogData ad = analogData.get(ii);
            bw.write(((Double) ad.getTimeStamp()).toString());
            ArrayList<Double> analogValues = ad.getAnalogValues();
            for (int jj = 0; jj < analogValues.size(); jj++) {
               bw.write(co + analogValues.get(jj));
            }
            bw.newLine();
         }
         bw.close();
      } catch (IOException iOException) {
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
         }
      }
   }

   private void createTIFileFromNeuroshare(String workingFilePath, String sourceFilePath, Entity entity) {

      String co = ",";
      String formatCode = "TI";
      double samplingRate = ((SegmentInfo) entity).getSampleRate();
      BufferedWriter bw = null;

      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TI, <SamplingRate>
         bw.write(formatCode + co + samplingRate);
         bw.newLine();

         // Read Neuroshare Data.
         NSReader nsReader = new NSReader();
         SegmentData segmentData = nsReader.getSegmentData(sourceFilePath, entity.getEntityInfo(), (SegmentInfo) entity);

         // Write Data to the workingFile(CSV).
         for (int ii = 0; ii < segmentData.getTimeStamp().size(); ii++) {
            // 2nd Line : <timestamp>, <dwUnitID>, <SegmentValue[0]>, <SegmentValue[1]>, ... <SegmentValue[n]>
            Double timeStamp = segmentData.getTimeStamp().get(ii);
            bw.write(timeStamp + co);
            Long unitID = segmentData.getUnitID().get(ii);
            bw.write(unitID.toString());

            ArrayList<Double> segmentValues = segmentData.getValues().get(ii);
            for (int jj = 0; jj < segmentValues.size(); jj++) {
               Double d = segmentValues.get(jj);
               if (d == null) {
                  bw.write(co + "NaN");

               } else {
                  bw.write(co + d);
               }
            }
            bw.newLine();
         }
         bw.close();
      } catch (IOException iOException) {
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
         }
      }
   }

   private void createTLFileFromNeuroshare(String workingFilePath, String sourceFilePath, Entity entity) {

      String co = ",";
      String formatCode = "TL";
      BufferedWriter bw = null;
      ArrayList<TextEventData> textEventData = new ArrayList<TextEventData>();
      ArrayList<ByteEventData> byteEventData = new ArrayList<ByteEventData>();
      ArrayList<WordEventData> wordEventData = new ArrayList<WordEventData>();
      ArrayList<DWordEventData> dwordEventData = new ArrayList<DWordEventData>();

      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TL
         bw.write(formatCode);
         bw.newLine();

         EventInfo ei = (EventInfo) entity;
         // Read Neuroshare Data.
         NSReader nsReader = new NSReader();
         //ArrayList<EventData> eventData = nsReader.getEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);

         switch ((int) ei.getEventType()) {
            case 0:
               textEventData = nsReader.getTextEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
               for (int ii = 0; ii < textEventData.size(); ii++) {
                  bw.write(((Double) textEventData.get(ii).getTimestamp()).toString() + co);
                  bw.write(textEventData.get(ii).getData());
                  bw.newLine();
               }
               break;
            case 1:
               textEventData = nsReader.getTextEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
               for (int ii = 0; ii < textEventData.size(); ii++) {
                  bw.write(((Double) textEventData.get(ii).getTimestamp()).toString() + co);
                  bw.write(textEventData.get(ii).getData());
                  bw.newLine();
               }
               break;
            case 2:
               byteEventData = nsReader.getByteEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
               for (int ii = 0; ii < byteEventData.size(); ii++) {
                  bw.write(((Double) byteEventData.get(ii).getTimestamp()).toString() + co);
                  bw.write(((Byte) (byteEventData.get(ii).getData())).toString());
                  bw.newLine();
               }
               break;
            case 3:
               wordEventData = nsReader.getWordEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
               for (int ii = 0; ii < wordEventData.size(); ii++) {
                  bw.write(((Double) wordEventData.get(ii).getTimestamp()).toString() + co);
                  bw.write(((Integer) (wordEventData.get(ii).getData())).toString());
                  bw.newLine();
               }
               break;
            case 4:
               dwordEventData = nsReader.getDWordEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
               for (int ii = 0; ii < dwordEventData.size(); ii++) {
                  bw.write(((Double) dwordEventData.get(ii).getTimestamp()).toString() + co);
                  bw.write(((Long) (dwordEventData.get(ii).getData())).toString());
                  bw.newLine();
               }
               break;
            default:

               break;
         }

         bw.close();
      } catch (IOException iOException) {
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
         }
      }
   }

   private void createTOFileFromNeuroshare(String workingFilePath, String sourceFilePath, Entity entity) {

      String co = ",";
      String formatCode = "TO";
      BufferedWriter bw = null;

      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TO
         bw.write(formatCode);
         bw.newLine();

         // Read Neuroshare Data.
         NSReader nsReader = new NSReader();
         ArrayList<Double> neuralData = nsReader.getNeuralData(sourceFilePath, entity.getEntityInfo());

         // Write Data to the workingFile(CSV).
         for (int ii = 0; ii < neuralData.size(); ii++) {

            // 2nd Line : <timestamp>
            Double d = neuralData.get(ii);
            if (d == null) {
               bw.write("NaN");
            } else {
               bw.write(d.toString());
            }
            bw.newLine();
         }
         bw.close();
      } catch (IOException iOException) {
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
         }
      }
   }

   private void createTSFileFromPlexon(String workingFilePath, String sourceFilePath, Entity entity) {
      // No case.
//        String co = ",";
//        String formatCode = "TS";
//        double samplingRate = ((AnalogInfo) entity).getSampleRate();
//        BufferedWriter bw = null;
//
//        try {
//            // BufferedWriter to write.
//            bw = new BufferedWriter(new FileWriter(workingFilePath));
//
//            // First Line : TS, <SamplingRate>
//            bw.write(formatCode + co + samplingRate);
//            bw.newLine();
//
//            // Read Neuroshare Data.
//            NSReader nsReader = new NSReader();
//            ArrayList<AnalogData> analogData = nsReader.getAnalogData(sourceFilePath, entity.getEntityInfo());
//
//            // Write Data to the workingFile(CSV).
//            for (int ii = 0; ii < analogData.size(); ii++) {
//
//                // 2nd Line : <timestamp>, <AnalogValue[0]>, <AnalogValue[1]>, ... <AnalogValue[n]>
//                AnalogData ad = analogData.get(ii);
//                bw.write(((Double) ad.getTimeStamp()).toString());
//                ArrayList<Double> analogValues = ad.getAnalogValues();
//                for (int jj = 0; jj < analogValues.size(); jj++) {
//                                        Double d = analogValues.get(jj);
//                    if (d == null) {
//                        bw.write(co + "NaN");
//                    } else {
//                        bw.write(co + d);
//                    }
//
//                }
//                bw.newLine();
//            }
//            bw.close();
//        } catch (IOException iOException) {
//        } finally {
//            try {
//                if (bw != null) {
//                    bw.close();
//                }
//            } catch (IOException iOException) {
//            }
//        }
   }

   private void createTSFileFromBlackRockNev(String workingFilePath, String sourceFilePath, Entity entity) {
      // No case.
//        String co = ",";
//        String formatCode = "TS";
//        double samplingRate = ((AnalogInfo) entity).getSampleRate();
//        BufferedWriter bw = null;
//
//        try {
//            // BufferedWriter to write.
//            bw = new BufferedWriter(new FileWriter(workingFilePath));
//
//            // First Line : TS, <SamplingRate>
//            bw.write(formatCode + co + samplingRate);
//            bw.newLine();
//
//            // Read Neuroshare Data.
//            NevReader nevReader = new NevReader();
//            ArrayList<AnalogData> analogData = nevReader.getAnalogData(sourceFilePath, entity.getEntityInfo());
//
//            // Write Data to the workingFile(CSV).
//            for (int ii = 0; ii < analogData.size(); ii++) {
//
//                // 2nd Line : <timestamp>, <AnalogValue[0]>, <AnalogValue[1]>, ... <AnalogValue[n]>
//                AnalogData ad = analogData.get(ii);
//                bw.write(((Double) ad.getTimeStamp()).toString());
//                ArrayList<Double> analogValues = ad.getAnalogValues();
//                for (int jj = 0; jj < analogValues.size(); jj++) {
//                                        Double d = analogValues.get(jj);
//                    if (d == null) {
//                        bw.write(co + "NaN");
//                    } else {
//                        bw.write(co + d);
//                    }
//
//                }
//                bw.newLine();
//            }
//            bw.close();
//        } catch (IOException iOException) {
//        } finally {
//            try {
//                if (bw != null) {
//                    bw.close();
//                }
//            } catch (IOException iOException) {
//            }
//        }
   }

   private void createTSFileFromATRCsv(String workingFilePath, String sourceFilePath, Entity entity) {
      String co = ",";
      String formatCode = "TS";
      double samplingRate = ((AnalogInfo) entity).getSampleRate();
      BufferedWriter bw = null;

      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TS, <SamplingRate>
         bw.write(formatCode + co + samplingRate);
         bw.newLine();

         // Read Neuroshare Data.
         CsvReader csvReader = new CsvReader();
         ArrayList<AnalogData> analogData = csvReader.getAnalogData(sourceFilePath, entity.getEntityInfo());

         // Write Data to the workingFile(CSV).
         for (int ii = 0; ii < analogData.size(); ii++) {

            // 2nd Line : <timestamp>, <AnalogValue[0]>, <AnalogValue[1]>, ... <AnalogValue[n]>
            AnalogData ad = analogData.get(ii);
            bw.write(((Double) ad.getTimeStamp()).toString());
            ArrayList<Double> analogValues = ad.getAnalogValues();
            for (int jj = 0; jj < analogValues.size(); jj++) {
               Double d = analogValues.get(jj);
               if (d == null) {
                  bw.write(co + "NaN");
               } else {
                  bw.write(co + d);
               }

            }
            bw.newLine();
         }
         bw.close();
      } catch (IOException iOException) {
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
         }
      }
   }

   private void createTSFileFromBlackRockNsx(String workingFilePath, String sourceFilePath, Entity entity) {
      String co = ",";
      String formatCode = "TS";
      double samplingRate = ((AnalogInfo) entity).getSampleRate();
      BufferedWriter bw = null;

      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TS, <SamplingRate>
         bw.write(formatCode + co + samplingRate);
         bw.newLine();

         // Read Neuroshare Data.
         NsxReader nsxReader = new NsxReader();
         ArrayList<AnalogData> analogData = nsxReader.getAnalogData(sourceFilePath, entity.getEntityInfo());

         // Write Data to the workingFile(CSV).
         for (int ii = 0; ii < analogData.size(); ii++) {

            // 2nd Line : <timestamp>, <AnalogValue[0]>, <AnalogValue[1]>, ... <AnalogValue[n]>
            AnalogData ad = analogData.get(ii);
            bw.write(((Double) ad.getTimeStamp()).toString());
            ArrayList<Double> analogValues = ad.getAnalogValues();
            for (int jj = 0; jj < analogValues.size(); jj++) {

               Double d = analogValues.get(jj);
               if (d == null) {
                  bw.write(co + "NaN");
               } else {
                  bw.write(co + d);
               }
            }
            bw.newLine();
         }
         bw.close();
      } catch (IOException iOException) {
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
         }
      }
   }

   private void createTIFileFromPlexon(String workingFilePath, String sourceFilePath, Entity entity) {

      String co = ",";
      String formatCode = "TI";
      double samplingRate = ((SegmentInfo) entity).getSampleRate();
      BufferedWriter bw = null;

      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TI, <SamplingRate>
         bw.write(formatCode + co + samplingRate);
         bw.newLine();

         // Read Neuroshare Data.
         PlxReader plxReader = new PlxReader();
         SegmentData segmentData = plxReader.getSegmentData(sourceFilePath, entity.getEntityInfo(), (SegmentInfo) entity);

         // Write Data to the workingFile(CSV).
         for (int ii = 0; ii < segmentData.getTimeStamp().size(); ii++) {
            // 2nd Line : <timestamp>, <dwUnitID>, <SegmentValue[0]>, <SegmentValue[1]>, ... <SegmentValue[n]>
            Double timeStamp = segmentData.getTimeStamp().get(ii);
            bw.write(timeStamp + co);
            Long unitID = segmentData.getUnitID().get(ii);
            bw.write(unitID.toString());

            ArrayList<Double> segmentValues = segmentData.getValues().get(ii);
            for (int jj = 0; jj < segmentValues.size(); jj++) {
               Double d = segmentValues.get(jj);
               if (d == null) {
                  bw.write(co + "NaN");

               } else {
                  bw.write(co + d);
               }
            }
            bw.newLine();
         }
         bw.close();
      } catch (IOException iOException) {
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
         }
      }
   }

   private void createTIFileFromBlackRockNev(String workingFilePath, String sourceFilePath, Entity entity) {

      String co = ",";
      String formatCode = "TI";
      double samplingRate = ((SegmentInfo) entity).getSampleRate();
      BufferedWriter bw = null;

      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TI, <SamplingRate>
         bw.write(formatCode + co + samplingRate);
         bw.newLine();

         // Read Neuroshare Data.
         NevReader nevReader = new NevReader();
         SegmentData segmentData = nevReader.getSegmentData(sourceFilePath, entity.getEntityInfo(), (SegmentInfo) entity);

         // Write Data to the workingFile(CSV).
         for (int ii = 0; ii < segmentData.getTimeStamp().size(); ii++) {
            // 2nd Line : <timestamp>, <dwUnitID>, <SegmentValue[0]>, <SegmentValue[1]>, ... <SegmentValue[n]>
            Double timeStamp = segmentData.getTimeStamp().get(ii);
            bw.write(timeStamp + co);
            Long unitID = segmentData.getUnitID().get(ii);
            bw.write(unitID.toString());

            ArrayList<Double> segmentValues = segmentData.getValues().get(ii);
            for (int jj = 0; jj < segmentValues.size(); jj++) {
               Double d = segmentValues.get(jj);
               if (d == null) {
                  bw.write(co + "NaN");

               } else {
                  bw.write(co + d);
               }
            }
            bw.newLine();
         }
         bw.close();
      } catch (IOException iOException) {
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
         }
      }
   }

   private void createTIFileFromATRCsv(String workingFilePath, String sourceFilePath, Entity entity) {

      String co = ",";
      String formatCode = "TI";
      double samplingRate = ((SegmentInfo) entity).getSampleRate();
      BufferedWriter bw = null;

      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TI, <SamplingRate>
         bw.write(formatCode + co + samplingRate);
         bw.newLine();

         // Read Neuroshare Data.
         CsvReader csvReader = new CsvReader();
         SegmentData segmentData = csvReader.getSegmentData(sourceFilePath, entity.getEntityInfo(), (SegmentInfo) entity);

         // Write Data to the workingFile(CSV).
         for (int ii = 0; ii < segmentData.getTimeStamp().size(); ii++) {
            // 2nd Line : <timestamp>, <dwUnitID>, <SegmentValue[0]>, <SegmentValue[1]>, ... <SegmentValue[n]>
            Double timeStamp = segmentData.getTimeStamp().get(ii);
            bw.write(timeStamp + co);
            Long unitID = segmentData.getUnitID().get(ii);
            bw.write(unitID.toString());

            ArrayList<Double> segmentValues = segmentData.getValues().get(ii);
            for (int jj = 0; jj < segmentValues.size(); jj++) {
               Double d = segmentValues.get(jj);
               if (d == null) {
                  bw.write(co + "NaN");

               } else {
                  bw.write(co + d);
               }
            }
            bw.newLine();
         }
         bw.close();
      } catch (IOException iOException) {
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
         }
      }
   }

   private void createTIFileFromBlackRockNsx(String workingFilePath, String sourceFilePath, Entity entity) {
      // No Case
//        String co = ",";
//        String formatCode = "TI";
//        double samplingRate = ((SegmentInfo) entity).getSampleRate();
//        BufferedWriter bw = null;
//
//        try {
//            // BufferedWriter to write.
//            bw = new BufferedWriter(new FileWriter(workingFilePath));
//
//            // First Line : TI, <SamplingRate>
//            bw.write(formatCode + co + samplingRate);
//            bw.newLine();
//
//            // Read Neuroshare Data.
//            NsxReader nsxReader = new NsxReader();
//            SegmentData segmentData = nsxReader.getSegmentData(sourceFilePath, entity.getEntityInfo(), (SegmentInfo) entity);
//
//            // Write Data to the workingFile(CSV).
//            for (int ii = 0; ii < segmentData.getTimeStamp().size(); ii++) {
//                // 2nd Line : <timestamp>, <dwUnitID>, <SegmentValue[0]>, <SegmentValue[1]>, ... <SegmentValue[n]>
//                Double timeStamp = segmentData.getTimeStamp().get(ii);
//                bw.write(timeStamp + co);
//                Long unitID = segmentData.getUnitID().get(ii);
//                bw.write(unitID.toString());
//
//                ArrayList<Double> segmentValues = segmentData.getValues().get(ii);
//                for (int jj = 0; jj < segmentValues.size(); jj++) {
//                    Double d = segmentValues.get(jj);
//                    if (d == null) {
//                        bw.write(co + "NaN");
//
//                    } else {
//                        bw.write(co + d);
//                    }
//                }
//                bw.newLine();
//            }
//            bw.close();
//        } catch (IOException iOException) {
//        } finally {
//            try {
//                if (bw != null) {
//                    bw.close();
//                }
//            } catch (IOException iOException) {
//            }
//        }
   }

   private void createTLFileFromPlexon(String workingFilePath, String sourceFilePath, Entity entity) {
      // No case.
//        String co = ",";
//        String formatCode = "TL";
//        BufferedWriter bw = null;
//        ArrayList<TextEventData> textEventData = new ArrayList<TextEventData>();
//        ArrayList<ByteEventData> byteEventData = new ArrayList<ByteEventData>();
//        ArrayList<WordEventData> wordEventData = new ArrayList<WordEventData>();
//        ArrayList<DWordEventData> dwordEventData = new ArrayList<DWordEventData>();
//
//        try {
//            // BufferedWriter to write.
//            bw = new BufferedWriter(new FileWriter(workingFilePath));
//
//            // First Line : TL
//            bw.write(formatCode);
//            bw.newLine();
//
//            EventInfo ei = (EventInfo) entity;
//            // Read Neuroshare Data.
//            PlxReader plxReader = new PlxReader();
//            //ArrayList<EventData> eventData = plxReader.getEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
//
//            switch ((int) ei.getEventType()) {
//                case 0:
//                    textEventData = nsReader.getTextEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
//                    for (int ii = 0; ii < textEventData.size(); ii++) {
//                        bw.write(((Double) textEventData.get(ii).getTimestamp()).toString() + co);
//                        bw.write(textEventData.get(ii).getData());
//                        bw.newLine();
//                    }
//                    break;
//                case 1:
//                    textEventData = nsReader.getTextEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
//                    for (int ii = 0; ii < textEventData.size(); ii++) {
//                        bw.write(((Double) textEventData.get(ii).getTimestamp()).toString() + co);
//                        bw.write(textEventData.get(ii).getData());
//                        bw.newLine();
//                    }
//                    break;
//                case 2:
//                    byteEventData = nsReader.getByteEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
//                    for (int ii = 0; ii < byteEventData.size(); ii++) {
//                        bw.write(((Double) textEventData.get(ii).getTimestamp()).toString() + co);
//                        bw.write(((Byte) (byteEventData.get(ii).getData())).toString());
//                        bw.newLine();
//                    }
//                    break;
//                case 3:
//                    wordEventData = nsReader.getWordEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
//                    for (int ii = 0; ii < wordEventData.size(); ii++) {
//                        bw.write(((Double) textEventData.get(ii).getTimestamp()).toString() + co);
//                        bw.write(((Integer) (wordEventData.get(ii).getData())).toString());
//                        bw.newLine();
//                    }
//                    break;
//                case 4:
//                    dwordEventData = nsReader.getDWordEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
//                    for (int ii = 0; ii < dwordEventData.size(); ii++) {
//                        bw.write(((Double) textEventData.get(ii).getTimestamp()).toString() + co);
//                        bw.write(((Long) (dwordEventData.get(ii).getData())).toString());
//                        bw.newLine();
//                    }
//                    break;
//                default:
//
//                    break;
//            }
//
//            bw.close();
//        } catch (IOException iOException) {
//        } finally {
//            try {
//                if (bw != null) {
//                    bw.close();
//                }
//            } catch (IOException iOException) {
//            }
//        }
   }

   private void createTLFileFromBlackRockNev(String workingFilePath, String sourceFilePath, Entity entity) {
      String co = ",";
      String formatCode = "TL";
      BufferedWriter bw = null;
      ArrayList<TextEventData> textEventData = new ArrayList<TextEventData>();
      ArrayList<ByteEventData> byteEventData = new ArrayList<ByteEventData>();
      ArrayList<WordEventData> wordEventData = new ArrayList<WordEventData>();
      ArrayList<DWordEventData> dwordEventData = new ArrayList<DWordEventData>();

      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TL
         bw.write(formatCode);
         bw.newLine();

         EventInfo ei = (EventInfo) entity;
         // Read BlackRock Data.
         NevReader nevReader = new NevReader();

         switch ((int) ei.getEventType()) {
            case 0:
               textEventData = nevReader.getTextEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
               for (int ii = 0; ii < textEventData.size(); ii++) {
                  bw.write(((Double) textEventData.get(ii).getTimestamp()).toString() + co);
                  bw.write(textEventData.get(ii).getData());
                  bw.newLine();
               }
               break;
            case 1:
               textEventData = nevReader.getTextEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
               for (int ii = 0; ii < textEventData.size(); ii++) {
                  bw.write(((Double) textEventData.get(ii).getTimestamp()).toString() + co);
                  bw.write(textEventData.get(ii).getData());
                  bw.newLine();
               }
               break;
            case 2:
               byteEventData = nevReader.getByteEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
               for (int ii = 0; ii < byteEventData.size(); ii++) {
                  bw.write(((Double) byteEventData.get(ii).getTimestamp()).toString() + co);
                  bw.write(((Byte) (byteEventData.get(ii).getData())).toString());
                  bw.newLine();
               }
               break;
            case 3:
               wordEventData = nevReader.getWordEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
               for (int ii = 0; ii < wordEventData.size(); ii++) {
                  bw.write(((Double) wordEventData.get(ii).getTimestamp()).toString() + co);
                  bw.write(((Integer) (wordEventData.get(ii).getData())).toString());
                  bw.newLine();
               }
               break;
            case 4:
               dwordEventData = nevReader.getDWordEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
               for (int ii = 0; ii < dwordEventData.size(); ii++) {
                  bw.write(((Double) dwordEventData.get(ii).getTimestamp()).toString() + co);
                  bw.write(((Long) (dwordEventData.get(ii).getData())).toString());
                  bw.newLine();
               }
               break;
            default:

               break;
         }

         bw.close();
      } catch (IOException iOException) {
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
         }
      }
   }

   private void createTLFileFromATRCsv(String workingFilePath, String sourceFilePath, Entity entity) {
      String co = ",";
      String formatCode = "TL";
      BufferedWriter bw = null;
      ArrayList<TextEventData> textEventData = new ArrayList<TextEventData>();
      ArrayList<ByteEventData> byteEventData = new ArrayList<ByteEventData>();
      ArrayList<WordEventData> wordEventData = new ArrayList<WordEventData>();
      ArrayList<DWordEventData> dwordEventData = new ArrayList<DWordEventData>();

      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TL
         bw.write(formatCode);
         bw.newLine();

         EventInfo ei = (EventInfo) entity;
         // Read BlackRock Data.
         CsvReader csvReader = new CsvReader();

         switch ((int) ei.getEventType()) {
            case 0:
               textEventData = csvReader.getTextEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
               for (int ii = 0; ii < textEventData.size(); ii++) {
                  bw.write(((Double) textEventData.get(ii).getTimestamp()).toString() + co);
                  bw.write(textEventData.get(ii).getData());
                  bw.newLine();
               }
               break;
            case 1:
               textEventData = csvReader.getTextEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
               for (int ii = 0; ii < textEventData.size(); ii++) {
                  bw.write(((Double) textEventData.get(ii).getTimestamp()).toString() + co);
                  bw.write(textEventData.get(ii).getData());
                  bw.newLine();
               }
               break;
            case 2:
               byteEventData = csvReader.getByteEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
               for (int ii = 0; ii < byteEventData.size(); ii++) {
                  bw.write(((Double) byteEventData.get(ii).getTimestamp()).toString() + co);
                  bw.write(((Byte) (byteEventData.get(ii).getData())).toString());
                  bw.newLine();
               }
               break;
            case 3:
               wordEventData = csvReader.getWordEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
               for (int ii = 0; ii < wordEventData.size(); ii++) {
                  bw.write(((Double) wordEventData.get(ii).getTimestamp()).toString() + co);
                  bw.write(((Integer) (wordEventData.get(ii).getData())).toString());
                  bw.newLine();
               }
               break;
            case 4:
               dwordEventData = csvReader.getDWordEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
               for (int ii = 0; ii < dwordEventData.size(); ii++) {
                  bw.write(((Double) dwordEventData.get(ii).getTimestamp()).toString() + co);
                  bw.write(((Long) (dwordEventData.get(ii).getData())).toString());
                  bw.newLine();
               }
               break;
            default:

               break;
         }

         bw.close();
      } catch (IOException iOException) {
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
         }
      }
   }

   private void createTLFileFromBlackRockNsx(String workingFilePath, String sourceFilePath, Entity entity) {
      // No case.
//        String co = ",";
//        String formatCode = "TL";
//        BufferedWriter bw = null;
      ArrayList<TextEventData> textEventData = new ArrayList<TextEventData>();
      ArrayList<ByteEventData> byteEventData = new ArrayList<ByteEventData>();
      ArrayList<WordEventData> wordEventData = new ArrayList<WordEventData>();
      ArrayList<DWordEventData> dwordEventData = new ArrayList<DWordEventData>();
//
//        try {
//            // BufferedWriter to write.
//            bw = new BufferedWriter(new FileWriter(workingFilePath));
//
//            // First Line : TL
//            bw.write(formatCode);
//            bw.newLine();
//
//            EventInfo ei = (EventInfo) entity;
//            // Read BlackRock Data.
      NsxReader nsxReader = new NsxReader();
//
//            switch ((int) ei.getEventType()) {
//                case 0:
//                    textEventData = nsxReader.getTextEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
//                    for (int ii = 0; ii < textEventData.size(); ii++) {
//                        bw.write(((Double) textEventData.get(ii).getTimestamp()).toString() + co);
//                        bw.write(textEventData.get(ii).getData());
//                        bw.newLine();
//                    }
//                    break;
//                case 1:
//                    textEventData = nsxReader.getTextEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
//                    for (int ii = 0; ii < textEventData.size(); ii++) {
//                        bw.write(((Double) textEventData.get(ii).getTimestamp()).toString() + co);
//                        bw.write(textEventData.get(ii).getData());
//                        bw.newLine();
//                    }
//                    break;
//                case 2:
//                    byteEventData = nsxReader.getByteEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
//                    for (int ii = 0; ii < byteEventData.size(); ii++) {
//                        bw.write(((Double) byteEventData.get(ii).getTimestamp()).toString() + co);
//                        bw.write(((Byte) (byteEventData.get(ii).getData())).toString());
//                        bw.newLine();
//                    }
//                    break;
//                case 3:
//                    wordEventData = nsxReader.getWordEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
//                    for (int ii = 0; ii < wordEventData.size(); ii++) {
//                        bw.write(((Double) wordEventData.get(ii).getTimestamp()).toString() + co);
//                        bw.write(((Integer) (wordEventData.get(ii).getData())).toString());
//                        bw.newLine();
//                    }
//                    break;
//                case 4:
//                    dwordEventData = nsxReader.getDWordEventData(sourceFilePath, entity.getEntityInfo(), (EventInfo) entity);
//                    for (int ii = 0; ii < dwordEventData.size(); ii++) {
//                        bw.write(((Double) dwordEventData.get(ii).getTimestamp()).toString() + co);
//                        bw.write(((Long) (dwordEventData.get(ii).getData())).toString());
//                        bw.newLine();
//                    }
//                    break;
//                default:
//
//                    break;
//            }
//
//            bw.close();
//        } catch (IOException iOException) {
//        } finally {
//            try {
//                if (bw != null) {
//                    bw.close();
//                }
//            } catch (IOException iOException) {
//            }
//        }
   }

   private void createTOFileFromPlexon(String workingFilePath, String sourceFilePath, Entity entity) {

      String co = ",";
      String formatCode = "TO";
      BufferedWriter bw = null;

      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TO
         bw.write(formatCode);
         bw.newLine();

         // Read Neuroshare Data.
         PlxReader plxReader = new PlxReader();
         ArrayList<Double> neuralData = plxReader.getNeuralData(sourceFilePath, entity.getEntityInfo());

         // Write Data to the workingFile(CSV).
         for (int ii = 0; ii < neuralData.size(); ii++) {

            // 2nd Line : <timestamp>
            Double d = neuralData.get(ii);
            if (d == null) {
               bw.write("NaN");
            } else {
               bw.write(d.toString());
            }
            bw.newLine();
         }
         bw.close();
      } catch (IOException iOException) {
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
         }
      }
   }

   private void createTOFileFromBlackRockNev(String workingFilePath, String sourceFilePath, Entity entity) {

      String co = ",";
      String formatCode = "TO";
      BufferedWriter bw = null;

      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TO
         bw.write(formatCode);
         bw.newLine();

         // Read Black Data.
         NevReader nevReader = new NevReader();
         ArrayList<Double> neuralData = nevReader.getNeuralData(sourceFilePath, entity.getEntityInfo());

         // Write Data to the workingFile(CSV).
         for (int ii = 0; ii < neuralData.size(); ii++) {

            // 2nd Line : <timestamp>
            Double d = neuralData.get(ii);
            if (d == null) {
               bw.write("NaN");
            } else {
               bw.write(d.toString());
            }
            bw.newLine();
         }
         bw.close();
      } catch (IOException iOException) {
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
         }
      }
   }

   private void createTOFileFromATRCsv(String workingFilePath, String sourceFilePath, Entity entity) {

      String co = ",";
      String formatCode = "TO";
      BufferedWriter bw = null;

      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TO
         bw.write(formatCode);
         bw.newLine();

         // Read Data.
         CsvReader csvReader = new CsvReader();
         ArrayList<Double> neuralData = csvReader.getNeuralData(sourceFilePath, entity.getEntityInfo());

         // Write Data to the workingFile(CSV).
         for (int ii = 0; ii < neuralData.size(); ii++) {

            // 2nd Line : <timestamp>
            Double d = neuralData.get(ii);
            if (d == null) {
               bw.write("NaN");
            } else {
               bw.write(d.toString());
            }
            bw.newLine();
         }
         bw.close();
      } catch (IOException iOException) {
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
         }
      }
   }

   private void createTOFileFromBlackRockNsx(String workingFilePath, String sourceFilePath, Entity entity) {
      // No case.
//        String co = ",";
//        String formatCode = "TO";
//        BufferedWriter bw = null;
//
//        try {
//            // BufferedWriter to write.
//            bw = new BufferedWriter(new FileWriter(workingFilePath));
//
//            // First Line : TO
//            bw.write(formatCode);
//            bw.newLine();
//
//            // Read Black Data.
//            NsxReader nsxReader = new NsxReader();
//            ArrayList<Double> neuralData = nsxReader.getNeuralData(sourceFilePath, entity.getEntityInfo());
//
//            // Write Data to the workingFile(CSV).
//            for (int ii = 0; ii < neuralData.size(); ii++) {
//
//                // 2nd Line : <timestamp>
//                Double d = neuralData.get(ii);
//                if (d == null) {
//                    bw.write("NaN");
//                } else {
//                    bw.write(d.toString());
//                }
//                bw.newLine();
//            }
//            bw.close();
//        } catch (IOException iOException) {
//        } finally {
//            try {
//                if (bw != null) {
//                    bw.close();
//                }
//            } catch (IOException iOException) {
//            }
//        }
   }

   /**
    *
    * @param workingFilePath
    * @param analogData
    * @param entity
    * @return
    */
   public boolean overwriteTSFile(String workingFilePath, ArrayList<AnalogData> analogData, Entity entity) {

      // if working file didn't exists or cannot be deleted, then false.
      File file = new File(workingFilePath);
      if (!file.exists()) {
         return false;
      }
      if (!file.delete()) {
         return false;
      }

      boolean result = true;
      String co = ",";
      String formatCode = "TS";
      double samplingRate = ((AnalogInfo) entity).getSampleRate();
      BufferedWriter bw = null;

      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TS, <SamplingRate>
         bw.write(formatCode + co + samplingRate);
         bw.newLine();

         // Write Data to the workingFile(CSV).
         for (int ii = 0; ii < analogData.size(); ii++) {

            // 2nd Line : <timestamp>, <AnalogValue[0]>, <AnalogValue[1]>, ... <AnalogValue[n]>
            AnalogData ad = analogData.get(ii);
            bw.write(((Double) ad.getTimeStamp()).toString());
            ArrayList<Double> analogValues = ad.getAnalogValues();
            for (int jj = 0; jj < analogValues.size(); jj++) {
               bw.write(co + analogValues.get(jj));
            }
            bw.newLine();
         }
         bw.close();
      } catch (IOException iOException) {
         result = false;
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
            result = false;
         }
      }

      return result;
   }

   /**
    *
    * @param workingFilePath
    * @param segmentData
    * @param entity
    * @return
    */
   public boolean overwriteTIFile(String workingFilePath, SegmentData segmentData, Entity entity) {

      // if working file didn't exists or cannot be deleted, then false.
      File file = new File(workingFilePath);
      if (!file.exists()) {
         return false;
      }
      if (!file.delete()) {
         return false;
      }

      boolean result = true;
      String co = ",";
      String formatCode = "TI";
      double samplingRate = ((SegmentInfo) entity).getSampleRate();
      BufferedWriter bw = null;

      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TI, <SamplingRate>
         bw.write(formatCode + co + samplingRate);
         bw.newLine();

         // Write Data to the workingFile(CSV).
         for (int ii = 0; ii < segmentData.getTimeStamp().size(); ii++) {

            // 2nd Line : <timestamp>, <dwUnitID>, <SegmentValue[0]>, <SegmentValue[1]>, ... <SegmentValue[n]>
            Double timeStamp = segmentData.getTimeStamp().get(ii);
            bw.write(timeStamp + co);
            Long unitID = segmentData.getUnitID().get(ii);
            bw.write(unitID.toString());

            ArrayList<Double> segmentValues = segmentData.getValues().get(ii);
            for (int jj = 0; jj < segmentValues.size(); jj++) {
               bw.write(co + segmentValues.get(jj));
            }
            bw.newLine();
         }
         bw.close();
      } catch (IOException iOException) {
         result = false;
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
            result = false;
         }
      }

      return result;
   }

   /**
    *
    * @param workingFilePath
    * @param eventObject
    * @param entity
    * @return
    */
   public boolean overwriteTLFile(String workingFilePath, Object eventObject, Entity entity) {

      // if working file didn't exists or cannot be deleted, then false.
      File file = new File(workingFilePath);
      if (!file.exists()) {
         return false;
      }
      if (!file.delete()) {
         return false;
      }

      boolean result = true;
      String co = ",";
      String formatCode = "TL";
      BufferedWriter bw = null;
      ArrayList<TextEventData> ted = null;
      ArrayList<ByteEventData> bed = null;
      ArrayList<WordEventData> wed = null;
      ArrayList<DWordEventData> dwed = null;

      int eventDataSize = 0;

      switch ((int) ((EventInfo) entity).getEventType()) {
         case 0:
            // ns_EVENT_TEXT
            ted = (ArrayList<TextEventData>) eventObject;
            eventDataSize = ted.size();
            break;
         case 1:
            // ns_EVENT_CSV
            return false;
         case 2:
            // ns_EVENT_BYTE
            bed = (ArrayList<ByteEventData>) eventObject;
            eventDataSize = bed.size();
            break;
         case 3:
            // ns_EVENT_WORD
            wed = (ArrayList<WordEventData>) eventObject;
            eventDataSize = wed.size();
            break;
         case 4:
            // ns_EVENT_DWORD
            dwed = (ArrayList<DWordEventData>) eventObject;
            eventDataSize = dwed.size();
            break;
         default:
            return false;
      }


      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TL
         bw.write(formatCode);
         bw.newLine();

         // Write Data to the workingFile(CSV).
         for (int ii = 0; ii < eventDataSize; ii++) {
            // 2nd Line : <timestamp>, <EventValue>

            Double timeStamp = (double) 0;

            switch ((int) ((EventInfo) entity).getEventType()) {
               case 0:
                  // ns_EVENT_TEXT
                  timeStamp = ted.get(ii).getTimestamp();
                  bw.write(timeStamp + co);
                  bw.write(ted.get(ii).getData());
                  break;
               case 1:
                  // ns_EVENT_CSV
                  return false;
               case 2:
                  // ns_EVENT_BYTE
                  timeStamp = bed.get(ii).getTimestamp();
                  bw.write(timeStamp + co);
                  bw.write(((Byte) bed.get(ii).getData()).toString());
                  break;
               case 3:
                  // ns_EVENT_WORD
                  timeStamp = wed.get(ii).getTimestamp();
                  bw.write(timeStamp + co);
                  bw.write(((Integer) wed.get(ii).getData()).toString());
                  break;
               case 4:
                  // ns_EVENT_DWORD
                  timeStamp = dwed.get(ii).getTimestamp();
                  bw.write(timeStamp + co);
                  bw.write(((Long) dwed.get(ii).getData()).toString());
                  break;
               default:
                  return false;

            }
            bw.newLine();
         }
         bw.close();
      } catch (IOException iOException) {
         result = false;
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
            result = false;
         }
      }

      return result;
   }

   /**
    *
    * @param workingFilePath
    * @param neuralData
    * @param entity
    * @return
    */
   public boolean overwriteTOFile(String workingFilePath, ArrayList<Double> neuralData, Entity entity) {

      // if working file didn't exists or cannot be deleted, then false.
      File file = new File(workingFilePath);
      if (!file.exists()) {
         return false;
      }
      if (!file.delete()) {
         return false;
      }

      boolean result = true;
      String co = ",";
      String formatCode = "TO";
      BufferedWriter bw = null;

      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TO
         bw.write(formatCode);
         bw.newLine();

         // Write Data to the workingFile(CSV).
         for (int ii = 0; ii < neuralData.size(); ii++) {

            // 2nd Line : <timestamp>
            bw.write(neuralData.get(ii).toString());
            bw.newLine();
         }
         bw.close();
      } catch (IOException iOException) {
         result = false;
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
            result = false;
         }
      }

      return result;
   }

   /**
    *
    * @param workingFilePath
    * @param data
    * @param entity
    * @return
    */
   public Entity overwriteTSFile(String workingFilePath, TSData data, Entity entity) {

      Entity retEntity = entity;

      // if working file didn't exists, then null.
      File file = new File(workingFilePath);
      if (!file.exists()) {
         return null;
      }
      // if it could not delete working file , then null.
      if (!file.delete()) {
         return null;
      }

      String co = ",";
      String formatCode = data.getFormatCode();
      double samplingRate = data.getSamplingRate();
      double maxValue = Double.MIN_VALUE;
      double minValue = Double.MAX_VALUE;
      int rowSize = data.getTimeStamps().size();
      int dataCounter = 0;
      BufferedWriter bw = null;

      // Write data to the workingFilePath.
      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TS, <SamplingRate>
         bw.write(formatCode + co + samplingRate);
         bw.newLine();

         // Write Data to the workingFile(CSV).
         for (int ii = 0; ii < rowSize; ii++) {

            // 2nd Line : <timestamp>, <AnalogValue[0]>, <AnalogValue[1]>, ... <AnalogValue[n]>
            bw.write(((Double) data.getTimeStamp(ii)).toString());
            ArrayList<Double> analogValues = data.getValues(ii);
            for (int jj = 0; jj < analogValues.size(); jj++) {
               dataCounter++;
               bw.write(co + analogValues.get(jj));
               if (analogValues.get(ii) > maxValue) {
                  maxValue = analogValues.get(ii);
               }
               if (analogValues.get(ii) < minValue) {
                  minValue = analogValues.get(ii);
               }
            }
            bw.newLine();
         }
         bw.close();
      } catch (IOException iOException) {
         return null;
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
            return null;
         }
      }

      // Edit Headers

      // Edit Tag [dwElemLength]
      Tag tag = entity.getTag();
      // elemLength : 40(ns_EntityInfo) + 264(ns_AnalogInfo) + row * 12(dTimestamp and dwDataCount) + dataCount * 8(dAnalogValue)
      int elemLength = Const_values.LENGTH_OF_NS_ENTITYINFO + Const_values.LENGTH_OF_NS_ANALOGINFO + rowSize * 12 + dataCounter * 8;
      tag.setElemLength(elemLength);
      retEntity.setTag(tag);

      // Edit EntityInfo [dwItemCount]
      EntityInfo ei = retEntity.getEntityInfo();
      // dwItemCount : dataCount
      ei.setItemCount(dataCounter);

      // Edit AnalogInfo [dSampleRate, dMinVal, dMaxVal]
      AnalogInfo analogInfo = (AnalogInfo) retEntity;
      analogInfo.setSampleRate(samplingRate);
      analogInfo.setMinVal(minValue);
      analogInfo.setMaxVal(maxValue);
      retEntity = (Entity) analogInfo;

      return retEntity;
   }

   /**
    *
    * @param workingFilePath
    * @param data
    * @param entity
    * @return
    */
   public Entity overwriteTOFile(String workingFilePath, TOData data, Entity entity) {

      Entity retEntity = entity;

      // if working file didn't exists, then null.
      File file = new File(workingFilePath);
      if (!file.exists()) {
         return null;
      }
      // if it could not delete working file , then null.
      if (!file.delete()) {
         return null;
      }

      String co = ",";
      String formatCode = data.getFormatCode();
      int rowSize = data.getTimeStamps().size();
      int dataCounter = 0;
      BufferedWriter bw = null;

      // Write data to the workingFilePath.
      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TO
         bw.write(formatCode);
         bw.newLine();

         // Write Data to the workingFile(CSV).
         for (int ii = 0; ii < rowSize; ii++) {

            // 2nd Line : <timestamp>
            bw.write(((Double) data.getTimeStamp(ii)).toString());
            dataCounter++;
            bw.newLine();
         }
         bw.close();
      } catch (IOException iOException) {
         return null;
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
            return null;
         }
      }

      // Edit Headers

      // Edit Tag [dwElemLength]
      Tag tag = entity.getTag();
      // elemLength : 40(ns_EntityInfo) + 136(ns_NeuralInfo) + dataCount * 8(dTimeStamp)
      int elemLength = Const_values.LENGTH_OF_NS_ENTITYINFO + Const_values.LENGTH_OF_NS_NEURALINFO + dataCounter * 8;
      tag.setElemLength(elemLength);
      retEntity.setTag(tag);

      // Edit EntityInfo [dwItemCount]
      EntityInfo ei = retEntity.getEntityInfo();
      // dwItemCount : dataCount
      ei.setItemCount(dataCounter);

      // Edit NeuralInfo []
      // Nothing.

      return retEntity;
   }

   /**
    *
    * @param workingFilePath
    * @param data
    * @param entity
    * @return
    */
   public Entity overwriteTIFile(String workingFilePath, TIData data, Entity entity) {

      Entity retEntity = entity;

      // if working file didn't exists, then null.
      File file = new File(workingFilePath);
      if (!file.exists()) {
         return null;
      }
      // if it could not delete working file , then null.
      if (!file.delete()) {
         return null;
      }

      String co = ",";
      String formatCode = data.getFormatCode();
      double samplingRate = data.getSamplingRate();
      int maxSampleCount = 0;
      int minSampleCount = Integer.MAX_VALUE;
      double maxValue = Double.MIN_VALUE;
      double minValue = Double.MAX_VALUE;
      int rowSize = data.getTimeStamps().size();
      int dataCounter = 0;
      BufferedWriter bw = null;

      // Write data to the workingFilePath.
      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TI, <SamplingRate>
         bw.write(formatCode + co + samplingRate);
         bw.newLine();

         // Write Data to the workingFile(CSV).
         for (int ii = 0; ii < rowSize; ii++) {

            // 2nd Line : <timestamp>, <unitID>, <SegmentValue[0]>, <SegmentValue[1]>, ... <SegmentValue[n]>
            bw.write(((Double) data.getTimeStamp(ii)).toString());
            bw.write(co + ((Integer) data.getUnitID(ii)).toString());
            ArrayList<Double> segmentValues = data.getValues(ii);
            if (segmentValues.size() > maxSampleCount) {
               maxSampleCount = segmentValues.size();
            }
            if (segmentValues.size() < minSampleCount) {
               minSampleCount = segmentValues.size();
            }
            for (int jj = 0; jj < segmentValues.size(); jj++) {
               dataCounter++;
               bw.write(co + segmentValues.get(jj));
               if (segmentValues.get(ii) > maxValue) {
                  maxValue = segmentValues.get(ii);
               }
               if (segmentValues.get(ii) < minValue) {
                  minValue = segmentValues.get(ii);
               }
            }
            bw.newLine();
         }
         bw.close();
      } catch (IOException iOException) {
         return null;
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
            return null;
         }
      }

      // Edit Headers

      // Edit Tag [dwElemLength]
      Tag tag = entity.getTag();
      // elemLength : 40(ns_EntityInfo) + 264(ns_SegmentInfo) + 248(ns_SegSourceInfo) + row * 16(dTimestamp, dwDataCount and dwUnitID) + dataCount * 8(dSegmentValue)
      int elemLength = Const_values.LENGTH_OF_NS_ENTITYINFO + Const_values.LENGTH_OF_NS_SEGMENTINFO + Const_values.LENGTH_OF_NS_SEGSOURCEINFO + rowSize * 16 + dataCounter * 8;
      tag.setElemLength(elemLength);
      retEntity.setTag(tag);

      // Edit EntityInfo [dwItemCount]
      EntityInfo ei = retEntity.getEntityInfo();
      // dwItemCount : dataCount
      ei.setItemCount(dataCounter);

      // Edit SegmentInfo [dwMinSampleCount, dwMaxSampleCount, dSampleRate]
      // Edit SegSourceInfo [dMinVal, dMaxVal]
      SegmentInfo segmentInfo = (SegmentInfo) retEntity;
      segmentInfo.setMinSampleCount(minSampleCount);
      segmentInfo.setMaxSampleCount(maxSampleCount);
      segmentInfo.setSampleRate(samplingRate);
      ArrayList<SegmentSourceInfo> segSourceInfos = segmentInfo.getSegSourceInfos();
      SegmentSourceInfo ssi = segSourceInfos.get(0);
      ssi.setMinVal(minValue);
      ssi.setMaxVal(maxValue);
      segmentInfo.setSegSourceInfos(segSourceInfos);
      retEntity = (Entity) segmentInfo;

      return retEntity;
   }

   /**
    *
    * @param workingFilePath
    * @param data
    * @param entity
    * @return
    */
   public Entity overwriteTLFile(String workingFilePath, TLData data, Entity entity) {

      Entity retEntity = entity;

      // if working file didn't exists, then null.
      File file = new File(workingFilePath);
      if (!file.exists()) {
         return null;
      }
      // if it could not delete working file , then null.
      if (!file.delete()) {
         return null;
      }

      String co = ",";
      String formatCode = data.getFormatCode();
      int minDataLength = Integer.MAX_VALUE;
      int maxDataLength = 0;
      int valueSize = 0;
      int totalValueSize = 0;
      int rowSize = data.getTimeStamps().size();
      int dataCounter = 0;
      BufferedWriter bw = null;

      // Write data to the workingFilePath.
      try {
         // BufferedWriter to write.
         bw = new BufferedWriter(new FileWriter(workingFilePath));

         // First Line : TL
         bw.write(formatCode);
         bw.newLine();

         // Write Data to the workingFile(CSV).
         for (int ii = 0; ii < rowSize; ii++) {
            dataCounter++;

            // 2nd Line : <timestamp>, <EventValue>
            bw.write(((Double) data.getTimeStamp(ii)).toString() + co);

            switch ((int) ((EventInfo) entity).getEventType()) {
               case 0:
                  bw.write(data.getValue(ii).toString());
                  valueSize = data.getValue(ii).toString().length();
                  break;
               case 1:
                  bw.write(data.getValue(ii).toString());
                  valueSize = data.getValue(ii).toString().length();
                  break;
               case 2:
                  bw.write(((Byte) data.getValue(ii)).toString());
                  valueSize = 1;
                  break;
               case 3:
                  bw.write(((Short) data.getValue(ii)).toString());
                  valueSize = 2;
                  break;
               case 4:
                  bw.write(((Integer) data.getValue(ii)).toString());
                  valueSize = 4;
                  break;
               default:
                  return null;
            }
            if (valueSize > maxDataLength) {
               maxDataLength = valueSize;
            }
            if (valueSize < minDataLength) {
               minDataLength = valueSize;
            }
            totalValueSize = totalValueSize + valueSize;

            bw.newLine();
         }
         bw.close();
      } catch (IOException iOException) {
         return null;
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException iOException) {
            return null;
         }
      }

      // Edit Headers

      // Edit Tag [dwElemLength]
      Tag tag = entity.getTag();
      // elemLength : 40(ns_EntityInfo) + 140(ns_EventInfo) + row * 12(dTimestamp + dwDataByteSize) + totalDataSize
      int elemLength = Const_values.LENGTH_OF_NS_ENTITYINFO + Const_values.LENGTH_OF_NS_EVENTINFO + rowSize * 12 + totalValueSize;
      tag.setElemLength(elemLength);
      retEntity.setTag(tag);

      // Edit EntityInfo [dwItemCount]
      EntityInfo ei = retEntity.getEntityInfo();
      // dwItemCount : dataCount
      ei.setItemCount(dataCounter);

      // Edit EventInfo [dwMinDataLength, dwMaxDataLength]
      EventInfo eventInfo = (EventInfo) retEntity;
      eventInfo.setMinDataLength(minDataLength);
      eventInfo.setMaxDataLength(maxDataLength);
      retEntity = (Entity) eventInfo;

      return retEntity;
   }
}
