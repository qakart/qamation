package org.qamation.jmeter.config.data.provider;

import org.apache.jorphan.logging.LoggingManager;
import org.apache.jorphan.util.JMeterStopThreadException;
import org.apache.log.Logger;
import org.qamation.data.provider.DataProvider;
import org.qamation.data.provider.DataProviderFactory;

import java.util.HashMap;

/**
 * Created by Pavel on 2017-05-19.
 */
public class DataProviderContainer {
    private static final Logger log = LoggingManager.getLoggerForClass();
    private static HashMap<String,DataProviderContainer> storedge = new HashMap<String,DataProviderContainer>();

    public static DataProviderContainer getDataProviderContainer(String fileName, String dataProviderImplClassName) {
        return getDataProviderContainer(fileName,dataProviderImplClassName,"");
    }

    public static DataProviderContainer getDataProviderContainer(String fileName, String dataProviderImplClassName, String suffix) {
        String storageId=fileName+suffix;
        DataProviderContainer container;
        if (storedge.containsKey(storageId)) {
            container = storedge.get(storageId);
            return container;
        }
        container = new DataProviderContainer(dataProviderImplClassName, fileName);
        storedge.put(storageId,container);
        return container;
    }

    private int cursor=0;
    private Object[][] data=null;
    private DataProvider dataProvider;
    private String fileName;

    private DataProviderContainer(String dataProviderImplClassName, String fileName) {
        this.dataProvider = DataProviderFactory.createDataProviderInstance(dataProviderImplClassName, fileName);
        this.data = dataProvider.getData();
        this.fileName = fileName;
    }

    public int getDataSize() {
        return data.length;
    }

    public void resetData() {
        cursor = 0;
    }

    public Object[] getNextDataLine(boolean shouldReset) {
        if (cursor > data.length) {
            if (shouldReset) {
                resetData();
            }
            else {
                throw new JMeterStopThreadException("End of "+fileName+" is reached");
            }
        }
        return data[cursor++];
    }

    public DataProvider getDataProvider() { return dataProvider; }

    public int getCursor() { return cursor; }

    public String getFileName() { return fileName; }

    public Object[][] getData() { return data; }

}
