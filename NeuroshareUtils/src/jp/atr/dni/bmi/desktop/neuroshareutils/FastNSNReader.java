package jp.atr.dni.bmi.desktop.neuroshareutils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Keiji Harada [*1]</br>[*1] ATR Intl. Conputational Neuroscience Labs, Decoding Group
 * @version 2011/04/22
 */
public class FastNSNReader {

    private Integer entityNum = 0;
    private Date fileStartDate = new Date();
    private Format formatter = new SimpleDateFormat("hh:mm:ss:SSS");
    private DecimalFormat twoDForm = new DecimalFormat("#.###");
    private ArrayList<String> labels = new ArrayList<String>();
    private HashMap<String, HashMap<Integer, Double>> allData = new HashMap<String, HashMap<Integer, Double>>();

    /**
     *
     * @param path
     * @return
     */
    public HashMap<String, HashMap<Integer, Double>> readNSFile(String path) {

        FileInfo fileInfo = null;
        String magicCode = null;
        RandomAccessFile file = null;

        try {
            file = new RandomAccessFile(path, "r");
            file.seek(0);

            magicCode = "";
            for (int i = 0; i < 16; i++) {
                magicCode += (char) file.readByte();
            }

            if (!magicCode.equals("NSN ver00000010 ")) {
                return null;
            }

            // Now read in the file info
            fileInfo = readFileInfo(file);

            if (fileInfo.getEntityCount() < 1) {
                return null;
            }

            fileStartDate.setYear(new Long(fileInfo.getYear()).intValue());
            fileStartDate.setDate(new Long(fileInfo.getDayOfMonth()).intValue());
            fileStartDate.setMonth(new Long(fileInfo.getMonth() - 1).intValue());
            fileStartDate.setHours(new Long(fileInfo.getHourOfDay()).intValue());
            fileStartDate.setMinutes(new Long(fileInfo.getMinOfDay()).intValue());
            fileStartDate.setSeconds(new Long(fileInfo.getSecOfDay()).intValue());
            fileStartDate.setTime(fileStartDate.getTime() + fileInfo.getMilliSecOfDay());

//         System.out.println("num entities: " + fileInfo.getEntityCount());

            // Now read in the entities
            for (int elemNum = 0; elemNum < (fileInfo.getEntityCount() > Constants.MAX_VIEWER_CHANNEL_NUM ? Constants.MAX_VIEWER_CHANNEL_NUM
                    : fileInfo.getEntityCount()); elemNum++) {

                // First read in the element tag
                long elemType = ReaderUtils.readUnsignedInt(file);
                long elemLength = ReaderUtils.readUnsignedInt(file);
                Tag tag = new Tag(ReaderUtils.getElemType(elemType), elemLength);
                // Read in the entity information
                EntityInfo entityNFO = readEntityInfo(file);

                // Now that we have the tag, we should do different things, based on the type
                if (tag.getElemType() == ElemType.ENTITY_EVENT) {
//               System.out.println("event entity");
                    long eventType = ReaderUtils.readUnsignedInt(file);
                    file.skipBytes(136);
                    getEventData(entityNFO, eventType, tag, file);
                } else if (tag.getElemType() == ElemType.ENTITY_ANALOG) {
//               System.out.println("analog entity");
                    // Read in header data
                    AnalogInfo analogNFO = readAnalogInfo(entityNFO, tag, file);

                    // Go through and get all the data. The type of data depends
                    // on the eventNFO's event
                    // type.
                    double timeIncrement = (1.0 / (analogNFO.getSampleRate()) * 1000.0);

                    // The following method has the side-effect of changing
                    // allData.
                    getAnalogData(entityNFO, file, timeIncrement, analogNFO.getProbeInfo());

                } else if (tag.getElemType() == ElemType.ENTITY_SEGMENT) {
//               System.out.println("segment entity");
                    // Read in header data
                    SegmentInfo segmentInfo = readSegmentInfo(entityNFO, tag, file);

                    // Get the segment source headers
                    for (int srcNDX = 0; srcNDX < segmentInfo.getSourceCount(); srcNDX++) {
                        readSegmentSourceInfo(file);
                    }

                    // Get the segment data now
                    getSegmentData(entityNFO, segmentInfo, file);

                } else if (tag.getElemType() == ElemType.ENTITY_NEURAL) {
//               System.out.println("neural entity");
                    // Read in header data
                    NeuralInfo neuralInfo = readNeuralInfo(entityNFO, tag, file);

                    // Get the neural data
                    // The following method has the side-effect of changing
                    // allData.
                    getNeuralData(entityNFO, file, neuralInfo.getProbeInfo());

                } else {
                    // We can't handle this, so just quit, returning data that
                    // has been collected thus far.
                    System.out.println("unknown entity. returning...");
                    return allData;
                }
            }
            return allData;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return allData;
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
        minVal = ReaderUtils.readDouble(file);
        maxVal = ReaderUtils.readDouble(file);

        for (int i = 0; i < 16; i++) {
            units += (char) file.readByte();
        }

        resolution = ReaderUtils.readDouble(file);
        locationX = ReaderUtils.readDouble(file);
        locationY = ReaderUtils.readDouble(file);
        locationZ = ReaderUtils.readDouble(file);
        locationuser = ReaderUtils.readDouble(file);
        highFreqCorner = ReaderUtils.readDouble(file);
        highFreqOrder = ReaderUtils.readUnsignedInt(file);

        for (int i = 0; i < 16; i++) {
            highFilterType += (char) file.readByte();
        }

        lowFreqCorner = ReaderUtils.readDouble(file);
        lowFreqOrder = ReaderUtils.readUnsignedInt(file);

        for (int i = 0; i < 16; i++) {
            lowFiltertype += (char) file.readByte();
        }

        for (int i = 0; i < 128; i++) {
            probeInfo += (char) file.readByte();
        }

        return new AnalogInfo(tag, entityNFO, sampleRate, minVal, maxVal, units, resolution, locationX,
                locationY, locationY, locationuser, highFreqCorner, highFreqOrder, highFilterType, lowFreqCorner,
                lowFreqOrder, lowFiltertype, probeInfo);
    }

    private EntityInfo readEntityInfo(RandomAccessFile file) throws IOException {
        String entityLabel = "";
        for (int i = 0; i < 32; i++) {
            entityLabel += (char) file.readByte();
        }

        long entityType = ReaderUtils.readUnsignedInt(file);

        long itemCount = ReaderUtils.readUnsignedInt(file);

        return new EntityInfo(entityLabel, entityType, itemCount);
    }

    private NeuralInfo readNeuralInfo(EntityInfo entityInfo, Tag tag, RandomAccessFile file) throws IOException {
        long sourceEntityID;
        long sourceUnitID;
        String probeInfo = "";

        sourceEntityID = ReaderUtils.readUnsignedInt(file);

        sourceUnitID = ReaderUtils.readUnsignedInt(file);

        for (int i = 0; i < 128; i++) {
            probeInfo += (char) file.readByte();
        }

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

        minSampleCount = ReaderUtils.readUnsignedInt(file);

        maxSampleCount = ReaderUtils.readUnsignedInt(file);

        sampleRate = ReaderUtils.readDouble(file);

        for (int i = 0; i < 32; i++) {
            units += (char) file.readByte();
        }

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
        maxVal = ReaderUtils.readDouble(file);
        resolution = ReaderUtils.readDouble(file);
        subSampleShift = ReaderUtils.readDouble(file);

        locationX = ReaderUtils.readDouble(file);
        locationY = ReaderUtils.readDouble(file);
        locationZ = ReaderUtils.readDouble(file);
        locationUser = ReaderUtils.readDouble(file);

        highFreqCorner = ReaderUtils.readDouble(file);
        highFreqOrder = ReaderUtils.readUnsignedInt(file);

        for (int i = 0; i < 16; i++) {
            highFilterType += (char) file.readByte();
        }

        lowFreqCorner = ReaderUtils.readDouble(file);

        lowFreqOrder = ReaderUtils.readUnsignedInt(file);

        for (int i = 0; i < 16; i++) {
            lowFilterType += (char) file.readByte();
        }

        for (int i = 0; i < 128; i++) {
            probeInfo += (char) file.readByte();
        }

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

        // Read in entityCount
        entityCount = ReaderUtils.readUnsignedInt(file);

        // Read in timeStampeRes
        timeStampResolution = ReaderUtils.readDouble(file);

        // Read in timespan
        timeSpan = ReaderUtils.readDouble(file);

        // Read in the appName
        for (int i = 0; i < 64; i++) {
            appName += (char) file.readByte();
        }

        // Read in the year
        year = ReaderUtils.readUnsignedInt(file);

        // Read in the month
        month = ReaderUtils.readUnsignedInt(file);

        // Read in the dayOfWeek
        dayOfWeek = ReaderUtils.readUnsignedInt(file);

        dayOfMonth = ReaderUtils.readUnsignedInt(file);

        // Read in the hourOfDay;
        hourOfDay = ReaderUtils.readUnsignedInt(file);

        // Read in the minOfDay
        minOfDay = ReaderUtils.readUnsignedInt(file);

        // Read in the secOfDay;
        secOfDay = ReaderUtils.readUnsignedInt(file);

        // Read in the milliSecOfDay;
        milliSecOfDay = ReaderUtils.readUnsignedInt(file);

        // Read in the comments
        for (int i = 0; i < 256; i++) {
            comments += (char) file.readByte();
        }

        return new FileInfo(fileType, entityCount, timeStampResolution, timeSpan, appName, year, month,
                dayOfWeek, dayOfMonth, hourOfDay, minOfDay, secOfDay, milliSecOfDay, comments);
    }

    private void getAnalogData(EntityInfo entityNFO, RandomAccessFile file, double timeIncrement,
            String probeInfo) throws IOException {
        int count = 0;
        boolean addedData = false;
        while (count < entityNFO.getItemCount()) {

            long timeStamp = new Double((ReaderUtils.readDouble(file) * 1000)).longValue()
                    + fileStartDate.getTime();
            long dataCount = ReaderUtils.readUnsignedInt(file);

            double bucketSize = dataCount / Constants.MAX_DATA_VIEWER_PTS;
            // System.out.println("bucket size:" + Math.round(bucketSize));
            int roundedBucketSize = ((Long) Math.round(bucketSize)).intValue();
            if (roundedBucketSize < 1) {
                roundedBucketSize = 1;
            }
            double[] bucket = new double[roundedBucketSize];
            int bucketNDX = 0;

            // Note: the following code only saves full buckets, so any
            // remainder buckets are not
            // saved. This is OK for a data previewer.
            String time = formatter.format(new Date(timeStamp));
            // System.out.println("data count:" + dataCount);

            for (int valNDX = 0; valNDX < dataCount; valNDX++) {

                bucket[bucketNDX++] = ReaderUtils.readDouble(file);
                count++;
                timeStamp += timeIncrement;

                if (bucketNDX >= roundedBucketSize) {
                    try {
                        Double val = null;
                        try {
                            val = Double.valueOf(twoDForm.format(BMIUtils.mean(bucket)));
                        } catch (NumberFormatException nfe) {
//                     nfe.printStackTrace();
                            val = new Double(0);
                        }

                        if (val != null && val != Double.NaN) {

                            if (allData.containsKey(time)) {
                                allData.get(time).put(entityNum, val);
                                addedData = true;

                            } else {
                                HashMap<Integer, Double> value = new HashMap<Integer, Double>();
                                value.put(entityNum, val);
                                allData.put(time, value);
                                addedData = true;
                            }
                        }
                    } catch (Throwable t) {
//                  t.printStackTrace();
                        // System.out.println("bucket--------------------------------------------");
                        // System.out.println(Arrays.toString(bucket));
                    }
                    bucketNDX = 0;
                    time = formatter.format(new Date(timeStamp));
                }
            }
        }
        if (addedData) {
            entityNum++;
            labels.add((entityNFO.getEntityLabel() == null ? "" : entityNFO.getEntityLabel() + "-")
                    + (probeInfo == null ? entityNum : probeInfo));
        }
    }

    private void getEventData(EntityInfo entityNFO, long eventType, Tag elem, RandomAccessFile file)
            throws IOException {

        for (int dataItemNum = 0; dataItemNum < entityNFO.getItemCount(); dataItemNum++) {
            file.skipBytes(8);

            Long dataByteSize = ReaderUtils.readUnsignedInt(file);

            file.skipBytes(dataByteSize.intValue());

            // if (eventType == 0) {
            // System.out.println("text");
            // // We are dealing with text
            // file.skipBytes(((Long) elem.getElemLength()).intValue());
            // System.out.println();
            // } else if (eventType == 1) {
            // System.out.println("csv");
            // // We are dealing with CSV. What do we do here?! TODO:XXX:FIXME:
            // // XXX: this is not defined in the file format specification
            // } else if (eventType == 2) {
            // System.out.println("1 byte");
            // // We are dealing with 1-byte values
            // file.skipBytes(1);
            // } else if (eventType == 3) {
            // System.out.println("2 byte");
            // // We are dealing with 2-byte values
            // file.skipBytes(2);
            // } else if (eventType == 4) {
            // System.out.println("4 byte");
            // // We are dealing with 4-byte values
            // file.skipBytes(4);
            // } else {
            // System.out.println("other");
            // // We can't handle it, so just quit.
            // return;
            // }
        }
    }

    /**
     * Neural data is simply a list of timestamps that say in seconds, when
     * spikes occurred in the neuronal data.
     *
     * @param file
     * @return
     * @throws IOException
     */
    private void getNeuralData(EntityInfo entityNFO, RandomAccessFile file, String probeInfo)
            throws IOException {
        // XXX: for now just skip the data since the viewer doesn't support it
        // file.skipBytes(((Long) entityNFO.getItemCount()).intValue() * 8);

        // ArrayList<Double> timeStamps = new ArrayList<Double>();

        for (int dataItemNum = 0; dataItemNum < entityNFO.getItemCount(); dataItemNum++) {

            long timeStamp = new Double((ReaderUtils.readDouble(file) * 1000)).longValue()
                    + fileStartDate.getTime();

            // Convert the data into a format the viewer can read
            String time = formatter.format(new Date(timeStamp - 1));
            if (!allData.containsKey(time)) {
                HashMap<Integer, Double> value = new HashMap<Integer, Double>();
                allData.put(time, value);
            }
            allData.get(time).put(entityNum, 0d);

            time = formatter.format(new Date(timeStamp));
            if (!allData.containsKey(time)) {
                HashMap<Integer, Double> value = new HashMap<Integer, Double>();
                allData.put(time, value);
            }
            allData.get(time).put(entityNum, 1d);

            time = formatter.format(new Date(timeStamp + 1));
            if (!allData.containsKey(time)) {
                HashMap<Integer, Double> value = new HashMap<Integer, Double>();
                allData.put(time, value);
            }
            allData.get(time).put(entityNum, 0d);
        }

        if (entityNFO.getItemCount() > 0) {
            entityNum++;
            labels.add((entityNFO.getEntityLabel() == null ? "" : entityNFO.getEntityLabel() + "-")
                    + (probeInfo == null ? entityNum : probeInfo));
        }

    }

    private SegmentData getSegmentData(EntityInfo entityNFO, SegmentInfo segNFO, RandomAccessFile file)
            throws IOException {

        ArrayList<Long> sampleCounts = new ArrayList<Long>();
        ArrayList<Double> timeStamps = new ArrayList<Double>();
        ArrayList<Long> unitIDS = new ArrayList<Long>();
        ArrayList<ArrayList<Double>> vals = new ArrayList<ArrayList<Double>>();

        for (int x = 0; x < segNFO.getSourceCount(); x++) {
            // NOTE: sample count is not defined in the spec because the spec
            // documentation is wrong!
            long sampleCount = ReaderUtils.readUnsignedInt(file);
            sampleCounts.add(sampleCount);

            double timeStamp = ReaderUtils.readDouble(file);
            timeStamps.add(timeStamp);

            long unitID = ReaderUtils.readUnsignedInt(file);
            unitIDS.add(unitID);

            vals.add(x, new ArrayList<Double>());

            for (int y = 0; y < segNFO.getMaxSampleCount(); y++) {
                vals.get(x).add(ReaderUtils.readDouble(file));
            }
        }

        return new SegmentData();
    }

    /**
     *
     * @return
     */
    public Integer getEntityNum() {
        return entityNum;
    }

    /**
     *
     * @param entityNum
     */
    public void setEntityNum(Integer entityNum) {
        this.entityNum = entityNum;
    }

    /**
     * @return the labels
     */
    public ArrayList<String> getLabels() {
        return labels;
    }

    /**
     * @param labels the labels to set
     */
    public void setLabels(ArrayList<String> labels) {
        this.labels = labels;
    }
}
