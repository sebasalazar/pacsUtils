package cl.medipacs.pacs.cli.service.impl;

import cl.medipacs.pacs.cli.service.DbService;
import cl.medipacs.pacs.cli.utils.DataUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sebastián Salazar Molina.
 */
@Service("dbService")
public class DbServiceImpl implements DbService, Serializable {

    private static final long serialVersionUID = 8044784471283530752L;

    @Resource(name = "dataSource")
    private DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(DbServiceImpl.class);

    @Override
    public List<String> getSeriesAttributes() {
        List<String> attributes = new ArrayList<String>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement("SELECT pk, series_attrs FROM series WHERE institution IS NULL");
            ResultSet rs = prepareStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Long id = rs.getLong("pk");
                    byte[] attrs = rs.getBytes("series_attrs");

                    String data = DataUtils.serialize(attrs);

                    if (StringUtils.isNotBlank(data)) {
                        LOGGER.info(String.format("# ID: '%d' # Datos: '%s' #", id, data));
                        attributes.add(data);
                    }
                }
                rs.close();
            }
        } catch (Exception e) {
            attributes = new ArrayList<String>();
            LOGGER.error("Error al consultar atributos: {}", e.toString());
            LOGGER.debug("Error al consultar atributos: {}", e.toString(), e);
        }
        return attributes;
    }

}
