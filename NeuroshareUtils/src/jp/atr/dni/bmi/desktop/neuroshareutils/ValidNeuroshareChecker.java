package jp.atr.dni.bmi.desktop.neuroshareutils;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Keiji Harada [*1]</br>[*1] ATR Intl. Conputational Neuroscience Labs, Decoding Group
 * @version 2011/04/22
 */
public class ValidNeuroshareChecker {

    /**
     *
     * @param path
     * @return
     */
    public static boolean isValidNSNFile(String path) {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(path, "r");
            file.seek(0);

            String magicCode = "";
            for (int i = 0; i < 16; i++) {
                magicCode += (char) file.readByte();
            }
            return magicCode.equals("NSN ver00000010 ");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
