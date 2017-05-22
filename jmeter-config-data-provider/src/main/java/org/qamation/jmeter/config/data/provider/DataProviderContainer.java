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

    synchronized public static DataProviderContainer getDataProviderContainer(String fileName, String dataProviderImplClassName, String suffix) {
        log.info("\nGetting container for: "+fileName);
        String storageId=fileName+suffix;
        DataProviderContainer container;
        if (storedge.containsKey(storageId)) {
            container = storedge.get(storageId);
            return container;
        }
        log.info("\nCreating new container for: "+fileName);
        container = new DataProviderContainer(dataProviderImplClassName, fileName);
        log.info("Container size: "+container.getDataSize());
        storedge.put(storageId,container);
        return container;
    }

    private int cursor;
    private DataProvider dataProvider;
    private String fileName;
    private String dataProviderImplClassName;
    private String suffix;

    private DataProviderContainer(String dataProviderImplClassName, String fileName) {
        this.dataProvider = DataProviderFactory.createDataProviderInstance(dataProviderImplClassName, fileName);
        this.fileName = fileName;
        this.dataProviderImplClassName=dataProviderImplClassName;
        this.cursor = 0;
    }

    public int getDataSize() {
        return dataProvider.getData().length;
    }

    public void resetData() {
        this.cursor = 0;
    }

    public Object[] getNextDataLine(boolean shouldReset) {
        if (cursor > dataProvider.getData().length-1) {
            if (shouldReset) {
                resetData();
            }
            else {
                throw new JMeterStopThreadException("End of "+fileName+" is reached");
            }
        }
        return dataProvider.getData()[cursor++];
    }

    public DataProvider getDataProvider() { return dataProvider; }

    public int getCursor() { return cursor; }

    public String getFileName() { return fileName; }

    public Object[][] getData() { return dataProvider.getData(); }

}