package org.qamation.jmeter.data.provider.config;


import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;

import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.engine.util.NoConfigMerge;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.qamation.data.provider.DataProvider;
import org.qamation.jmeter.data.provider.GuiData;
import org.slf4j.LoggerFactory;


public class ExcelDataConfig extends ConfigTestElement
        implements
        TestBean,
        LoopIterationListener,
        NoConfigMerge, GuiData {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ExcelDataConfig.class);
    private DataProviderConfigSupport support;

    protected String filename;
    protected String dataProviderImplClassName;
    protected boolean resetAtEOF;
    protected boolean resetAtTestStart;
    protected String shareMode;
    protected String tabNumber;
    protected String fieldNames;
    protected boolean isFirstLineHeader;


    @Override
    public void iterationStart(LoopIterationEvent loopIterationEvent) {
        JMeterContext context = JMeterContextService.getContext();
        log.info("iteration start by thread: " + context.getThread().getThreadName());
        this.support = new DataProviderConfigSupport(this,context);
        support.iterationStart();

    }


    @Override
    public <T extends DataProvider> T callDataProviderFactory() {
        return this.support.callDataProviderFactory();
    }

    @Override
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String getDataProviderImplClassName() {
        return dataProviderImplClassName;
    }

    public void setDataProviderImplClassName(String dataProviderImplClassName) {
        this.dataProviderImplClassName = dataProviderImplClassName;
    }

    @Override
    public boolean isResetAtEOF() {
        return resetAtEOF;
    }

    public void setResetAtEOF(boolean resetAtEOF) {
        this.resetAtEOF = resetAtEOF;
    }

    @Override
    public boolean isResetAtTestStart() {
        return resetAtTestStart;
    }

    public void setResetAtTestStart(boolean resetAtTestStart) {
        this.resetAtTestStart = resetAtTestStart;
    }

    @Override
    public String getShareMode() {
        return shareMode;
    }

    public void setShareMode(String shareMode) {
        this.shareMode = shareMode;
    }


    @Override
    public String getTabNumber() {
        return tabNumber;
    }

    public void setTabNumber(String tabNumber) {
        this.tabNumber = tabNumber;
    }

    @Override
    public String getFieldNames() {
        return fieldNames;
    }

    @Override
    public boolean isFirstLineHeader() {
        return getIsFirstLineHeader();
    }

    public void setFieldNames(String fieldNames) {
        this.fieldNames = fieldNames;
    }


    public boolean getIsFirstLineHeader() {
        return isFirstLineHeader;
    }

    public void setIsFirstLineHeader(boolean firstLineHeader) {
        this.isFirstLineHeader = firstLineHeader;
    }
}