package jp.atr.dni.bmi.desktop.neuroshareutils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Keiji Harada [*1]</br>[*1] ATR Intl. Conputational Neuroscience Labs, Decoding Group
 * @version 2011/04/22
 */
public class NSReader {

    /** Logger
     * @param path
     * @return
     */
//   private static final Logger LOGGER = LoggerFactory.getLogger(NSReader.class);
    public NeuroshareFile readNSFileAllData(String path) {

        FileInfo fileInfo = null;
        String magicCode = null;
        ArrayList<Entity> entities = new ArrayList<Entity>();

        try {
            RandomAccessFile file = new RandomAccessFile(path, "r");
            file.seek(0);

            magicCode = "";
            for (int i = 0; i < 16; i++) {
                magicCode += (char) file.readByte();
            }

//         LOGGER.debug("magicCode: " + magicCode);
            if (!magicCode.equals("NSN ver00000010 ")) {
//            LOGGER.error("The FILE we are trying to read is not compatible with "
//                  + "the ver 1.0 format of the Neuroshare Native Datafile "
//                  + "Specificiation.\n\nSorry, but there is nothing else we can do now.\n\n" + "Exiting...");
                System.exit(1);
            }

            magicCode = magicCode.trim();

            // Now read in the FILE info
            fileInfo = readFileInfo(file);

            if (fileInfo.getEntityCount() < 1) {
//            LOGGER.error("The FILE has no entities to read in." + "Exiting...");
                System.exit(1);
            }

            // Now read in the entities
            for (int elemNum = 0; elemNum < fileInfo.getEntityCount(); elemNum++) {
                // First read in the element tag
                long elemType = ReaderUtils.readUnsignedInt(file);
//            LOGGER.debug("------------------------------");
//            LOGGER.debug("elemType: " + elemType);
                long elemLength = ReaderUtils.readUnsignedInt(file);
//            LOGGER.debug("elemLength: " + elemLength);
                Tag tag = new Tag(ReaderUtils.getElemType(elemType), elemLength);
                // Read in the entity information
                EntityInfo entityNFO = readEntityInfo(file);

                // Save filepath info here.
                entityNFO.setFilePath(path);

                // Now that we have the tag, we should do different things, based on the type
                if (tag.getElemType() == ElemType.ENTITY_EVENT) {

                    // Read in FN_HEADER data
                    EventInfo eventNFO = readEventInfo(entityNFO, tag, file);
                    entities.add(eventNFO);

                    // Save position info here.
                    entityNFO.setDataPosition(file.getFilePointer());

                    // if (!lazyLoad) {
                    // Go through and get all the data. The type of data depends on the eventNFO's
                    // EVENT type.
                    ArrayList<EventData> data = getEventData(entityNFO, eventNFO, tag, file);
                    // }
                    eventNFO.setData(data);

                } else if (tag.getElemType() == ElemType.ENTITY_ANALOG) {
                    // Read in FN_HEADER data
                    AnalogInfo analogNFO = readAnalogInfo(entityNFO, tag, file);
                    entities.add(analogNFO);

                    // Save position info here.
                    entityNFO.setDataPosition(file.getFilePointer());


                    // Go through and get all the data. The type of data depends on the eventNFO's EVENT
                    // type.
                    ArrayList<AnalogData> data = getAnalogData(entityNFO, file);
                    analogNFO.setData(data);

                } else if (tag.getElemType() == ElemType.ENTITY_SEGMENT) {
                    // Read in FN_HEADER data
                    SegmentInfo segmentInfo = readSegmentInfo(entityNFO, tag, file);
                    entities.add(segmentInfo);

                    ArrayList<SegmentSourceInfo> segSourceInfos = new ArrayList<SegmentSourceInfo>();
                    // Get the SEGMENT source headers
                    for (int srcNDX = 0; srcNDX < segmentInfo.getSourceCount(); srcNDX++) {
//                  LOGGER.debug("Reading in SEGMENT source FN_HEADER------------------");
                        segSourceInfos.add(readSegmentSourceInfo(file));
                    }

                    segmentInfo.setSegSourceInfos(segSourceInfos);

                    // Save position info here.
                    entityNFO.setDataPosition(file.getFilePointer());

                    // Get the SEGMENT data now
                    SegmentData segData = getSegmentData(entityNFO, segmentInfo, file);
                    segmentInfo.setSegData(segData);

                } else if (tag.getElemType() == ElemType.ENTITY_NEURAL) {
                    // Read in FN_HEADER data
                    NeuralInfo neuralInfo = readNeuralInfo(entityNFO, tag, file);
                    entities.add(neuralInfo);

                    // Save position info here.
                    entityNFO.setDataPosition(file.getFilePointer());

                    // Get the NEURAL data
                    ArrayList<Double> nData = getNeuralData(entityNFO, file);
                    neuralInfo.setData(nData);

                } else {
                    // We can't handle this, so just quit.
                    // NOTE: we would just be able to skip this tag and move on, but since the Neuroshare
                    // .nsn files are binary and not XML-based, we cannot just skip a tag and move on.
                    // This is one good reason not to use binary FILE formats like this.
//               LOGGER.error("An element tag has an unknown datatype, so we have to quit.");
                    System.exit(1);
                }
            }

            file.close();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, "Code : File Read Error\n" + "Todo : Check your file format.\n" + "StackTR : " + ex, "Error", JOptionPane.ERROR_MESSAGE);
            return null;

        }
        return new NeuroshareFile(magicCode, fileInfo, entities, false);
    }

    /**
     *
     * @param path
     * @return
     */
    public NeuroshareFile readNSFileOnlyInfo(String path) {

        FileInfo fileInfo = null;
        String magicCode = null;
        ArrayList<Entity> entities = new ArrayList<Entity>();

        try {
            RandomAccessFile file = new RandomAccessFile(path, "r");
            file.seek(0);

            magicCode = "";
            for (int i = 0; i < 16; i++) {
                magicCode += (char) file.readByte();
            }

//         LOGGER.debug("magicCode: " + magicCode);
            if (!magicCode.equals("NSN ver00000010 ")) {
//            LOGGER.error("The FILE we are trying to read is not compatible with "
//                  + "the ver 1.0 format of the Neuroshare Native Datafile "
//                  + "Specificiation.\n\nSorry, but there is nothing else we can do now.\n\n" + "Exiting...");
                System.exit(1);
            }

            magicCode = magicCode.trim();

            // Now read in the FILE info
            fileInfo = readFileInfo(file);

            if (fileInfo.getEntityCount() < 1) {
//            LOGGER.error("The FILE has no entities to read in." + "Exiting...");
                System.exit(1);
            }

            // Now read in the entities
            for (int elemNum = 0; elemNum < fileInfo.getEntityCount(); elemNum++) {
                // First read in the element tag
                long elemType = ReaderUtils.readUnsignedInt(file);
//            LOGGER.debug("------------------------------");
//            LOGGER.debug("elemType: " + elemType);
                long elemLength = ReaderUtils.readUnsignedInt(file);
//            LOGGER.debug("elemLength: " + elemLength);
                Tag tag = new Tag(ReaderUtils.getElemType(elemType), elemLength);
                // Read in the entity information
                EntityInfo entityNFO = readEntityInfo(file);

                // Save filepath info here.
                entityNFO.setFilePath(path);

                // Now that we have the tag, we should do different things, based on the type
                if (tag.getElemType() == ElemType.ENTITY_EVENT) {

                    // Read in FN_HEADER data
                    EventInfo eventNFO = readEventInfo(entityNFO, tag, file);
                    entities.add(eventNFO);

                    // Save position info here.
                    entityNFO.setDataPosition(file.getFilePointer());

                    //Skip Bytes
                    int skipBytes = (int) tag.getElemLength() - (Const_values.LENGTH_OF_NS_ENTITYINFO + Const_values.LENGTH_OF_NS_EVENTINFO);
                    file.skipBytes(skipBytes);

                } else if (tag.getElemType() == ElemType.ENTITY_ANALOG) {
                    // Read in FN_HEADER data
                    AnalogInfo analogNFO = readAnalogInfo(entityNFO, tag, file);
                    entities.add(analogNFO);

                    // Save position info here.
                    entityNFO.setDataPosition(file.getFilePointer());

                    //Skip Bytes
                    int skipBytes = (int) tag.getElemLength() - (Const_values.LENGTH_OF_NS_ENTITYINFO + Const_values.LENGTH_OF_NS_ANALOGINFO);
                    file.skipBytes(skipBytes);

                } else if (tag.getElemType() == ElemType.ENTITY_SEGMENT) {
                    // Read in FN_HEADER data
                    SegmentInfo segmentInfo = readSegmentInfo(entityNFO, tag, file);
                    entities.add(segmentInfo);

                    ArrayList<SegmentSourceInfo> segSourceInfos = new ArrayList<SegmentSourceInfo>();
                    // Get the SEGMENT source headers
                    for (int srcNDX = 0; srcNDX < segmentInfo.getSourceCount(); srcNDX++) {
//                  LOGGER.debug("Reading in SEGMENT source FN_HEADER------------------");
                        segSourceInfos.add(readSegmentSourceInfo(file));
                    }

                    segmentInfo.setSegSourceInfos(segSourceInfos);

                    // Save position info here.
                    entityNFO.setDataPosition(file.getFilePointer());

                    //Skip Bytes
                    int skipBytes = (int) tag.getElemLength() - (Const_values.LENGTH_OF_NS_ENTITYINFO + Const_values.LENGTH_OF_NS_SEGMENTINFO + (int) segmentInfo.getSourceCount() * Const_values.LENGTH_OF_NS_SEGSOURCEINFO);
                    file.skipBytes(skipBytes);

                } else if (tag.getElemType() == ElemType.ENTITY_NEURAL) {
                    // Read in FN_HEADER data
                    NeuralInfo neuralInfo = readNeuralInfo(entityNFO, tag, file);
                    entities.add(neuralInfo);

                    // Save position info here.
                    entityNFO.setDataPosition(file.getFilePointer());

                    //Skip Bytes
                    int skipBytes = (int) tag.getElemLength() - (Const_values.LENGTH_OF_NS_ENTITYINFO + Const_values.LENGTH_OF_NS_NEURALINFO);
                    file.skipBytes(skipBytes);

                } else {
                    // We can't handle this, so just quit.
                    // NOTE: we would just be able to skip this tag and move on, but since the Neuroshare
                    // .nsn files are binary and not XML-based, we cannot just skip a tag and move on.
                    // This is one good reason not to use binary FILE formats like this.
//               LOGGER.error("An element tag has an unknown datatype, so we have to quit.");
                    System.exit(1);
                }
            }

            file.close();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, "Code : File Read Error\n" + "Todo : Check your file format.\n" + "StackTR : " + ex, "Error", JOptionPane.ERROR_MESSAGE);
            return null;

        }
        return new NeuroshareFile(magicCode, fileInfo, entities, false);
    }

    private AnalogInfo readAnalogInfo(EntityInfo entityNFO, Tag tag, RandomAccessFile file) throws IOException {

        double sampleRate;
        double minVal;
        double maxVal;
        String units = "";
        double resolution;
        double locationX;
        double locationY;
        double locationZ;
        double locationuser;
        double highFreqCorner;
        long highFreqOrder;
        String highFilterType = "";
        double lowFreqCorner;
        long lowFreqOrder;
        String lowFiltertype = "";
        String probeInfo = "";

        sampleRate = ReaderUtils.readDouble(file);
//      LOGGER.debug("sampleRate: " + sampleRate);

        minVal = ReaderUtils.readDouble(file);
//      LOGGER.debug("minVal: " + minVal);

        maxVal = ReaderUtils.readDouble(file);
//      LOGGER.debug("maxVal: " + maxVal);

        for (int i = 0; i < 16; i++) {
            units += (char) file.readByte();
        }
//      LOGGER.debug("units: " + units);

        resolution = ReaderUtils.readDouble(file);
//      LOGGER.debug("resolution: " + resolution);

        locationX = ReaderUtils.readDouble(file);
//      LOGGER.debug("locationX: " + locationX);

        locationY = ReaderUtils.readDouble(file);
//      LOGGER.debug("locationY: " + locationY);

        locationZ = ReaderUtils.readDouble(file);
//      LOGGER.debug("locationZ: " + locationZ);

        locationuser = ReaderUtils.readDouble(file);
//      LOGGER.debug("locationuser: " + locationuser);

        highFreqCorner = ReaderUtils.readDouble(file);
//      LOGGER.debug("highFreqCorner: " + highFreqCorner);

        highFreqOrder = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("highFreqOrder: " + highFreqOrder);

        for (int i = 0; i < 16; i++) {
            highFilterType += (char) file.readByte();
        }
//      LOGGER.debug("highFilterType: " + highFilterType);

        lowFreqCorner = ReaderUtils.readDouble(file);
//      LOGGER.debug("lowFreqCorner: " + lowFreqCorner);

        lowFreqOrder = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("lowFreqOrder: " + lowFreqOrder);

        for (int i = 0; i < 16; i++) {
            lowFiltertype += (char) file.readByte();
        }
//      LOGGER.debug("lowFiltertype: " + lowFiltertype);

        for (int i = 0; i < 128; i++) {
            probeInfo += (char) file.readByte();
        }

//      LOGGER.debug("probeInfo: " + probeInfo);

        return new AnalogInfo(tag, entityNFO, sampleRate, minVal, maxVal, units, resolution, locationX,
                locationY, locationZ, locationuser, highFreqCorner, highFreqOrder, highFilterType, lowFreqCorner,
                lowFreqOrder, lowFiltertype, probeInfo);
    }

    private EntityInfo readEntityInfo(RandomAccessFile file) throws IOException {
        String entityLabel = "";
        for (int i = 0; i < 32; i++) {
            entityLabel += (char) file.readByte();
        }
//      LOGGER.debug("entityLabel: " + entityLabel);

        long entityType = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("entityType: " + entityType);

        long itemCount = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("itemCount: " + itemCount);

        return new EntityInfo(entityLabel, entityType, itemCount);
    }

    private EventInfo readEventInfo(EntityInfo entityInfo, Tag tag, RandomAccessFile file) throws IOException {
        // Now process the EVENT information FN_HEADER
        long eventType = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("eventType: " + eventType);

        long minDataLength = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("minDataLength: " + minDataLength);

        long maxDataLength = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("maxDataLength: " + maxDataLength);

        String csvDesc = "";

        for (int i = 0; i < 128; i++) {
            csvDesc += (char) file.readByte();
        }
//      LOGGER.debug("csvDesc: " + csvDesc);

        return new EventInfo(tag, entityInfo, eventType, minDataLength, maxDataLength, csvDesc);
    }

    private NeuralInfo readNeuralInfo(EntityInfo entityInfo, Tag tag, RandomAccessFile file) throws IOException {
        long sourceEntityID;
        long sourceUnitID;
        String probeInfo = "";

        sourceEntityID = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("sourceEntityID: " + sourceEntityID);

        sourceUnitID = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("sourceUnitID: " + sourceUnitID);

        for (int i = 0; i < 128; i++) {
            probeInfo += (char) file.readByte();
        }
//      LOGGER.debug("probeInfo: " + probeInfo);

        return new NeuralInfo(tag, entityInfo, sourceEntityID, sourceUnitID, probeInfo);
    }

    private SegmentInfo readSegmentInfo(EntityInfo entityInfo, Tag tag, RandomAccessFile file)
            throws IOException {
        long sourceCount;
        long minSampleCount;
        long maxSampleCount;
        double sampleRate;
        String units = "";

        sourceCount = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("sourceCount: " + sourceCount);

        minSampleCount = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("minSampleCount: " + minSampleCount);

        maxSampleCount = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("maxSampleCount: " + maxSampleCount);

        sampleRate = ReaderUtils.readDouble(file);
//      LOGGER.debug("sampleRate: " + sampleRate);

        for (int i = 0; i < 32; i++) {
            units += (char) file.readByte();
        }
//      LOGGER.debug("units: " + units);

        return new SegmentInfo(tag, entityInfo, sourceCount, minSampleCount, maxSampleCount, sampleRate, units);
    }

    private SegmentSourceInfo readSegmentSourceInfo(RandomAccessFile file) throws IOException {

        double minVal;
        double maxVal;
        double resolution;
        double subSampleShift;
        double locationX;
        double locationY;
        double locationZ;
        double locationUser;
        double highFreqCorner;
        long highFreqOrder;
        String highFilterType = "";
        double lowFreqCorner;
        long lowFreqOrder;
        String lowFilterType = "";
        String probeInfo = "";

        minVal = ReaderUtils.readDouble(file);
//      LOGGER.debug("minVal: " + minVal);

        maxVal = ReaderUtils.readDouble(file);
//      LOGGER.debug("maxVal: " + maxVal);

        resolution = ReaderUtils.readDouble(file);
//      LOGGER.debug("resolution: " + resolution);

        subSampleShift = ReaderUtils.readDouble(file);
//      LOGGER.debug("subSampleShift: " + subSampleShift);

        locationX = ReaderUtils.readDouble(file);
//      LOGGER.debug("locationX" + locationX);

        locationY = ReaderUtils.readDouble(file);
//      LOGGER.debug("locationY: " + locationY);

        locationZ = ReaderUtils.readDouble(file);
//      LOGGER.debug("locationZ: " + locationZ);

        locationUser = ReaderUtils.readDouble(file);
//      LOGGER.debug("locationUser: " + locationUser);

        highFreqCorner = ReaderUtils.readDouble(file);
//      LOGGER.debug("highFreqCorner: " + highFreqCorner);

        highFreqOrder = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("highFreqOrder: " + highFreqOrder);

        for (int i = 0; i < 16; i++) {
            highFilterType += (char) file.readByte();
        }
//      LOGGER.debug("highFilterType: " + highFilterType);

        lowFreqCorner = ReaderUtils.readDouble(file);
//      LOGGER.debug("lowFreqCorner: " + lowFreqCorner);

        lowFreqOrder = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("lowFreqOrder: " + lowFreqOrder);

        for (int i = 0; i < 16; i++) {
            lowFilterType += (char) file.readByte();
        }
//      LOGGER.debug("lowFilterType: " + lowFilterType);

        for (int i = 0; i < 128; i++) {
            probeInfo += (char) file.readByte();
        }
//      LOGGER.debug("probeInfo: " + probeInfo);

        return new SegmentSourceInfo(minVal, maxVal, resolution, subSampleShift, locationX, locationY,
                locationZ, locationUser, highFreqCorner, highFreqOrder, highFilterType, lowFreqCorner,
                lowFreqOrder, lowFilterType, probeInfo);
    }

    private FileInfo readFileInfo(RandomAccessFile file) throws IOException {
        String fileType = "";
        long entityCount = 0;
        double timeStampResolution;
        double timeSpan;
        String appName = "";
        long year = 0;
        long month = 0;
        long dayOfWeek = 0;
        long dayOfMonth = 0;
        long hourOfDay = 0;
        long minOfDay = 0;
        long secOfDay = 0;
        long milliSecOfDay = 0;
        String comments = "";

        file.seek(16); // Seek to guarantee we are where we want to be

        // Read in fileType
        for (int i = 0; i < 32; i++) {
            fileType += (char) file.readByte();
        }
//      LOGGER.debug("fileType: " + fileType);

        // Read in entityCount
        entityCount = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("entityCount: " + Long.toString(entityCount & 0xffffffffL));
//      LOGGER.debug("entityCount: " + entityCount);

        // Read in timeStampeRes
        timeStampResolution = ReaderUtils.readDouble(file);
//      LOGGER.debug("timeStampeResolution: " + timeStampResolution);

        // Read in timespan
        timeSpan = ReaderUtils.readDouble(file);
//      LOGGER.debug("timeSpan: " + timeSpan);

        // Read in the appName
        for (int i = 0; i < 64; i++) {
            appName += (char) file.readByte();
        }
//      LOGGER.debug("appName: " + appName);

        // Read in the year
        year = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("year: " + year);

        // Read in the month
        month = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("month: " + month);

        // Read in the dayOfWeek
        dayOfWeek = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("dayOfWeek: " + dayOfWeek);

        dayOfMonth = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("dayOfMonth: " + dayOfMonth);

        // Read in the hourOfDay;
        hourOfDay = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("hourOfDay: " + hourOfDay);

        // Read in the minOfDay
        minOfDay = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("minOfDay: " + minOfDay);

        // Read in the secOfDay;
        secOfDay = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("secOfDay: " + secOfDay);

        // Read in the milliSecOfDay;
        milliSecOfDay = ReaderUtils.readUnsignedInt(file);
//      LOGGER.debug("milliSecOfDay: " + milliSecOfDay);

        // Read in the comments
        for (int i = 0; i < 256; i++) {
            comments += (char) file.readByte();
        }
//      LOGGER.debug("comments: " + comments);

        return new FileInfo(fileType, entityCount, timeStampResolution, timeSpan, appName, year, month,
                dayOfWeek, dayOfMonth, hourOfDay, minOfDay, secOfDay, milliSecOfDay, comments);
    }

    private ArrayList<AnalogData> getAnalogData(EntityInfo entityNFO, RandomAccessFile file) throws IOException {
        ArrayList<AnalogData> data = new ArrayList<AnalogData>();
        int count = 0;

        while (count < entityNFO.getItemCount()) {

            double timeStamp = ReaderUtils.readDouble(file);
//         LOGGER.debug("timeStamp: " + timeStamp);

            long dataCount = ReaderUtils.readUnsignedInt(file);
//         LOGGER.debug("dataCount: " + dataCount);

            ArrayList<Double> values = new ArrayList<Double>();

            for (int valNDX = 0; valNDX < dataCount; valNDX++) {
                values.add(ReaderUtils.readDouble(file));
                count++;
            }
            data.add(new AnalogData(timeStamp, dataCount, values));
//         LOGGER.debug("values: " + values);
        }

        return data;
    }

    private ArrayList<EventData> getEventData(EntityInfo entityNFO, EventInfo eventNFO, Tag elem,
            RandomAccessFile file) throws IOException {
        ArrayList<EventData> data = new ArrayList<EventData>();

        for (int dataItemNum = 0; dataItemNum < entityNFO.getItemCount(); dataItemNum++) {

            double timeStamp = ReaderUtils.readDouble(file);
//         LOGGER.debug("timeStamp: " + timeStamp);
            long byteSize = ReaderUtils.readUnsignedInt(file);
//         LOGGER.debug("byteSize: " + byteSize);

            if (eventNFO.getEventType() == EventType.EVENT_TEXT.ordinal()) {
                // We are dealing with text
                String dataStr = "";

                for (int i = 0; i < byteSize; i++) {
                    dataStr += (char) file.readByte();
                }
//            LOGGER.debug("Data String: " + dataStr);
                data.add(new TextEventData(timeStamp, byteSize, dataStr));

            } else if (eventNFO.getEventType() == 1) {
                // We are dealing with CSV. What do we do here?! TODO:XXX:FIXME:
                // XXX: this is not defined in the FILE format specification
            } else if (eventNFO.getEventType() == EventType.EVENT_BYTE.ordinal()) {
                // We are dealing with 1-byte values
                // NOTE: We use the wordevent data for 1 and 2 byte values, because both are stored as
                // ints.
                int binData = file.readUnsignedByte();
//            LOGGER.debug("binData: " + binData);
                data.add(new WordEventData(timeStamp, byteSize, binData));

            } else if (eventNFO.getEventType() == EventType.EVENT_WORD.ordinal()) {
                // We are dealing with 2-byte values
                int binData = file.readUnsignedShort();
//            LOGGER.debug("binData: " + binData);
                data.add(new WordEventData(timeStamp, byteSize, binData));

            } else if (eventNFO.getEventType() == EventType.EVENT_DWORD.ordinal()) {
                // We are dealing with 4-byte values
                long binData = ReaderUtils.readUnsignedInt(file);
//            LOGGER.debug("binData: " + binData);
                data.add(new DWordEventData(timeStamp, byteSize, binData));

            } else {
                // We can't handle it, so just quit.
//            LOGGER.error("An unexpected EVENT type was encountered, so we have to quit.");
                System.exit(1);
            }
        }
        file.close();

        return data;
    }

    private ArrayList<Double> getNeuralData(EntityInfo entityNFO, RandomAccessFile file) throws IOException {
        ArrayList<Double> timeStamps = new ArrayList<Double>();

        for (int dataItemNum = 0; dataItemNum < entityNFO.getItemCount(); dataItemNum++) {
            double timeStamp = ReaderUtils.readDouble(file);
//         LOGGER.debug("timeStamp: " + timeStamp);
            timeStamps.add(timeStamp);
        }
        file.close();

        return timeStamps;
    }

    private SegmentData getSegmentData(EntityInfo entityNFO, SegmentInfo segNFO, RandomAccessFile file)
            throws IOException {

        ArrayList<Long> sampleCounts = new ArrayList<Long>();
        ArrayList<Double> timeStamps = new ArrayList<Double>();
        ArrayList<Long> unitIDS = new ArrayList<Long>();
        ArrayList<ArrayList<Double>> vals = new ArrayList<ArrayList<Double>>();

        for (int x = 0; x < segNFO.getSourceCount(); x++) {
            // NOTE: sample count is not defined in the spec because the spec documentation is wrong!
            long sampleCount = ReaderUtils.readUnsignedInt(file);
//         LOGGER.debug("sampleCount: " + sampleCount);
            sampleCounts.add(sampleCount);

            double timeStamp = ReaderUtils.readDouble(file);
//         LOGGER.debug("timeStamp: " + timeStamp);
            timeStamps.add(timeStamp);

            long unitID = ReaderUtils.readUnsignedInt(file);
//         LOGGER.debug("unitID: " + unitID);
            unitIDS.add(unitID);

            vals.add(x, new ArrayList<Double>());

//            for (int y = 0; y < segNFO.getMaxSampleCount(); y++) {
            for (int y = 0; y < sampleCount; y++) {
                vals.get(x).add(ReaderUtils.readDouble(file));
            }
//         LOGGER.debug("vals: " + vals.get(x));
        }
        file.close();

        return new SegmentData(sampleCounts, timeStamps, unitIDS, vals);
    }

    /**
     *
     * @param fileFullPath
     * @param entityNFO
     * @return
     * @throws IOException
     */
    public ArrayList<AnalogData> getAnalogData(String fileFullPath, EntityInfo entityNFO) throws IOException {
        RandomAccessFile file = new RandomAccessFile(fileFullPath, "r");
        file.seek(entityNFO.getDataPosition());

        ArrayList<AnalogData> data = new ArrayList<AnalogData>();
        int count = 0;

        while (count < entityNFO.getItemCount()) {

            double timeStamp = ReaderUtils.readDouble(file);
//         LOGGER.debug("timeStamp: " + timeStamp);

            long dataCount = ReaderUtils.readUnsignedInt(file);
//         LOGGER.debug("dataCount: " + dataCount);

            ArrayList<Double> values = new ArrayList<Double>();

            for (int valNDX = 0; valNDX < dataCount; valNDX++) {
                values.add(ReaderUtils.readDouble(file));
                count++;
            }
            data.add(new AnalogData(timeStamp, dataCount, values));
//         LOGGER.debug("values: " + values);
        }
        file.close();

        return data;
    }

    /**
     *
     * @param fileFullPath
     * @param entityNFO
     * @param eventNFO
     * @return
     * @throws IOException
     */
    public ArrayList<EventData> getEventData(String fileFullPath, EntityInfo entityNFO, EventInfo eventNFO) throws IOException {
        RandomAccessFile file = new RandomAccessFile(fileFullPath, "r");
        file.seek(entityNFO.getDataPosition());

        ArrayList<EventData> data = new ArrayList<EventData>();

        for (int dataItemNum = 0; dataItemNum < entityNFO.getItemCount(); dataItemNum++) {

            double timeStamp = ReaderUtils.readDouble(file);
//         LOGGER.debug("timeStamp: " + timeStamp);
            long byteSize = ReaderUtils.readUnsignedInt(file);
//         LOGGER.debug("byteSize: " + byteSize);

            if (eventNFO.getEventType() == EventType.EVENT_TEXT.ordinal()) {
                // We are dealing with text
                String dataStr = "";

                for (int i = 0; i < byteSize; i++) {
                    dataStr += (char) file.readByte();
                }
//            LOGGER.debug("Data String: " + dataStr);
                data.add(new TextEventData(timeStamp, byteSize, dataStr));

            } else if (eventNFO.getEventType() == 1) {
                // We are dealing with CSV. What do we do here?! TODO:XXX:FIXME:
                // XXX: this is not defined in the FILE format specification
            } else if (eventNFO.getEventType() == EventType.EVENT_BYTE.ordinal()) {
                // We are dealing with 1-byte values
                // NOTE: We use the wordevent data for 1 and 2 byte values, because both are stored as
                // ints.
                int binData = file.readUnsignedByte();
//            LOGGER.debug("binData: " + binData);
                data.add(new WordEventData(timeStamp, byteSize, binData));

            } else if (eventNFO.getEventType() == EventType.EVENT_WORD.ordinal()) {
                // We are dealing with 2-byte values
                int binData = file.readUnsignedShort();
//            LOGGER.debug("binData: " + binData);
                data.add(new WordEventData(timeStamp, byteSize, binData));

            } else if (eventNFO.getEventType() == EventType.EVENT_DWORD.ordinal()) {
                // We are dealing with 4-byte values
                long binData = ReaderUtils.readUnsignedInt(file);
//            LOGGER.debug("binData: " + binData);
                data.add(new DWordEventData(timeStamp, byteSize, binData));

            } else {
                // We can't handle it, so just quit.
//            LOGGER.error("An unexpected EVENT type was encountered, so we have to quit.");
                System.exit(1);
            }
        }
        file.close();

        return data;
    }

    /**
     *
     * @param fileFullPath
     * @param entityNFO
     * @param eventNFO
     * @return
     * @throws IOException
     */
    public ArrayList<TextEventData> getTextEventData(String fileFullPath, EntityInfo entityNFO, EventInfo eventNFO) throws IOException {
        RandomAccessFile file = new RandomAccessFile(fileFullPath, "r");
        file.seek(entityNFO.getDataPosition());

        ArrayList<TextEventData> data = new ArrayList<TextEventData>();

        for (int dataItemNum = 0; dataItemNum < entityNFO.getItemCount(); dataItemNum++) {

            double timeStamp = ReaderUtils.readDouble(file);
//         LOGGER.debug("timeStamp: " + timeStamp);
            long byteSize = ReaderUtils.readUnsignedInt(file);
//         LOGGER.debug("byteSize: " + byteSize);

            String dataStr = "";

            for (int i = 0; i < byteSize; i++) {
                dataStr += (char) file.readByte();
            }
//            LOGGER.debug("Data String: " + dataStr);
            data.add(new TextEventData(timeStamp, byteSize, dataStr));

        }
        file.close();

        return data;
    }

    /**
     *
     * @param fileFullPath
     * @param entityNFO
     * @param eventNFO
     * @return
     * @throws IOException
     */
    public ArrayList<ByteEventData> getByteEventData(String fileFullPath, EntityInfo entityNFO, EventInfo eventNFO) throws IOException {
        RandomAccessFile file = new RandomAccessFile(fileFullPath, "r");
        file.seek(entityNFO.getDataPosition());

        ArrayList<ByteEventData> data = new ArrayList<ByteEventData>();

        for (int dataItemNum = 0; dataItemNum < entityNFO.getItemCount(); dataItemNum++) {

            double timeStamp = ReaderUtils.readDouble(file);
//         LOGGER.debug("timeStamp: " + timeStamp);
            long byteSize = ReaderUtils.readUnsignedInt(file);
//         LOGGER.debug("byteSize: " + byteSize);

            int binData = file.readUnsignedByte();
//            LOGGER.debug("binData: " + binData);
            data.add(new ByteEventData(timeStamp, byteSize, ((Integer) binData).byteValue()));

        }
        file.close();

        return data;
    }

    /**
     *
     * @param fileFullPath
     * @param entityNFO
     * @param eventNFO
     * @return
     * @throws IOException
     */
    public ArrayList<WordEventData> getWordEventData(String fileFullPath, EntityInfo entityNFO, EventInfo eventNFO) throws IOException {
        RandomAccessFile file = new RandomAccessFile(fileFullPath, "r");
        file.seek(entityNFO.getDataPosition());

        ArrayList<WordEventData> data = new ArrayList<WordEventData>();

        for (int dataItemNum = 0; dataItemNum < entityNFO.getItemCount(); dataItemNum++) {

            double timeStamp = ReaderUtils.readDouble(file);
//         LOGGER.debug("timeStamp: " + timeStamp);
            long byteSize = ReaderUtils.readUnsignedInt(file);
//         LOGGER.debug("byteSize: " + byteSize);

            // We are dealing with 2-byte values
            int binData = file.readUnsignedShort();
//            LOGGER.debug("binData: " + binData);
            data.add(new WordEventData(timeStamp, byteSize, binData));

        }
        file.close();

        return data;
    }

    /**
     *
     * @param fileFullPath
     * @param entityNFO
     * @param eventNFO
     * @return
     * @throws IOException
     */
    public ArrayList<DWordEventData> getDWordEventData(String fileFullPath, EntityInfo entityNFO, EventInfo eventNFO) throws IOException {
        RandomAccessFile file = new RandomAccessFile(fileFullPath, "r");
        file.seek(entityNFO.getDataPosition());

        ArrayList<DWordEventData> data = new ArrayList<DWordEventData>();

        for (int dataItemNum = 0; dataItemNum < entityNFO.getItemCount(); dataItemNum++) {

            double timeStamp = ReaderUtils.readDouble(file);
//         LOGGER.debug("timeStamp: " + timeStamp);
            long byteSize = ReaderUtils.readUnsignedInt(file);
//         LOGGER.debug("byteSize: " + byteSize);

            // We are dealing with 4-byte values
            long binData = ReaderUtils.readUnsignedInt(file);
//            LOGGER.debug("binData: " + binData);
            data.add(new DWordEventData(timeStamp, byteSize, binData));
        }
        file.close();

        return data;
    }

    /**
     *
     * @param fileFullPath
     * @param entityNFO
     * @return
     * @throws IOException
     */
    public ArrayList<Double> getNeuralData(String fileFullPath, EntityInfo entityNFO) throws IOException {
        RandomAccessFile file = new RandomAccessFile(fileFullPath, "r");
        file.seek(entityNFO.getDataPosition());

        ArrayList<Double> timeStamps = new ArrayList<Double>();

        for (int dataItemNum = 0; dataItemNum < entityNFO.getItemCount(); dataItemNum++) {
            double timeStamp = ReaderUtils.readDouble(file);
//         LOGGER.debug("timeStamp: " + timeStamp);
            timeStamps.add(timeStamp);
        }
        file.close();

        return timeStamps;
    }

    /**
     *
     * @param fileFullPath
     * @param entityNFO
     * @param segNFO
     * @return
     * @throws IOException
     */
    public SegmentData getSegmentData(String fileFullPath, EntityInfo entityNFO, SegmentInfo segNFO)
            throws IOException {
        RandomAccessFile file = new RandomAccessFile(fileFullPath, "r");
        file.seek(entityNFO.getDataPosition());

        ArrayList<Long> sampleCounts = new ArrayList<Long>();
        ArrayList<Double> timeStamps = new ArrayList<Double>();
        ArrayList<Long> unitIDS = new ArrayList<Long>();
        ArrayList<ArrayList<Double>> vals = new ArrayList<ArrayList<Double>>();

        for (int x = 0; x < segNFO.getSourceCount(); x++) {
            // NOTE: sample count is not defined in the spec because the spec documentation is wrong!
            long sampleCount = ReaderUtils.readUnsignedInt(file);
//         LOGGER.debug("sampleCount: " + sampleCount);
            sampleCounts.add(sampleCount);

            double timeStamp = ReaderUtils.readDouble(file);
//         LOGGER.debug("timeStamp: " + timeStamp);
            timeStamps.add(timeStamp);

            long unitID = ReaderUtils.readUnsignedInt(file);
//         LOGGER.debug("unitID: " + unitID);
            unitIDS.add(unitID);

            vals.add(x, new ArrayList<Double>());

//            for (int y = 0; y < segNFO.getMaxSampleCount(); y++) {
            for (int y = 0; y < sampleCount; y++) {
                vals.get(x).add(ReaderUtils.readDouble(file));
            }
//         LOGGER.debug("vals: " + vals.get(x));
        }
        file.close();

        return new SegmentData(sampleCounts, timeStamps, unitIDS, vals);
    }
}
