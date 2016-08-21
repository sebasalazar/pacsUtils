package cl.medipacs.pacs.cli.utils;

import java.io.Serializable;
import org.apache.commons.codec.binary.Hex;
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
}
