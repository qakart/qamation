package org.qamation.jmeter.config.data.provider;

import org.apache.jmeter.testbeans.BeanInfoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;

/**
 * Created by Pavel on 2017-04-22.
 */
public abstract class AbstractDataBeanInfo extends BeanInfoSupport {

    private static final Logger log = LoggerFactory.getLogger(AbstractDataBeanInfo.class);

    protected static final String FILENAME = "filename";
    protected static final String CLASSNAME = "dataProviderImplClassName";
    protected static final String RESET = "resetAtEOF";
    protected static final String SOURCENAME = "dataLabel";
    protected static final String SHAREMODE = "shareMode";

    // Access needed from CSVDataSet
    static final String[] SHARE_TAGS = new String[3];
    static final int SHARE_ALL    = 0;
    static final int SHARE_GROUP  = 1;
    static final int SHARE_THREAD = 2;

    // Store the resource keys
    static {
        SHARE_TAGS[SHARE_ALL]    = "shareMode.all"; //$NON-NLS-1$
        SHARE_TAGS[SHARE_GROUP]  = "shareMode.group"; //$NON-NLS-1$
        SHARE_TAGS[SHARE_THREAD] = "shareMode.thread"; //$NON-NLS-1$
    }


    public AbstractDataBeanInfo(Class className) {
        super(className);
        log.info("Bean Info super is done.");
        log.info("creating group");
        setProperties();
    }

    abstract protected void setProperties();

    public static int getShareModeAsInt(String mode) {
        if (mode == null || mode.length() == 0){
            return SHARE_ALL; // default (e.g. if test plan does not have definition)
        }
        for (int i = 0; i < SHARE_TAGS.length; i++) {
            if (SHARE_TAGS[i].equals(mode)) {
                return i;
            }
        }
        return -1;
    }

}
