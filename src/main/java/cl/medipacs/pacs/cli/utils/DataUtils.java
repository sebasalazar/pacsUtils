package cl.medipacs.pacs.cli.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sebastián Salazar Molina.
 */
public class DataUtils implements Serializable {

    private static final long serialVersionUID = 6041875449182612480L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DataUtils.class);

    private DataUtils() {
        throw new AssertionError();
    }

    public static String serialize(byte[] data) {
        String result = StringUtils.EMPTY;
        try {
            if (data != null) {
                result = new String(Hex.encodeHex(data));
            }
        } catch (Exception e) {
            result = StringUtils.EMPTY;
            LOGGER.error("Error al serializar datos: {}", e.toString());
        }
        return result;
    }

    public static File createDcmFile(Long id, byte[] data) {
        File file = null;
        try {
            if (id != null && data != null) {
                String directory = String.format("%s%sdata", System.getProperty("java.io.tmpdir"), System.getProperty("file.separator"));
                File dir = new File(directory);
                dir.mkdirs();

                file = new File(String.format("%s%s%d.dcm", directory, System.getProperty("file.separator"), id));
                OutputStream out = new FileOutputStream(file);
                IOUtils.write(data, out);
                out.close();
            }
        } catch (Exception e) {
            file = null;
            LOGGER.error("Error al crear archivo dcm: {}", e.toString());
        }
        return file;
    }
}
