package org.qamation.data.provider.excel;

import org.qamation.data.provider.DataProviderAdapter;
import org.qamation.excel.utils.ExcelReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;


public class ExcelDataAdapter extends DataProviderAdapter implements ExcelDataProvider {
    private static final Logger log = LoggerFactory.getLogger(ExcelDataAdapter.class);

    private int sheetIndex;
    protected ExcelReader provider;


    public ExcelDataAdapter(String fileName, int sheetIncex) {
        super(fileName);
        this.sheetIndex = sheetIncex;
        this.fileName = fileName;
        provider = new ExcelReader(fileName,sheetIncex);
    }


    public String[] getFieldNames() {
        return provider.getFieldNames();
    }


    public int getDataSize() {
        return getSize()-1;
    }

    public Iterator<String[]> getIterator() {
        return provider.iterator();
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

}