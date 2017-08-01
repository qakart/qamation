package org.qamation.jqm.utils;

import com.enioka.jqm.api.JobInstance;
import com.enioka.jqm.api.JqmClient;
import com.enioka.jqm.api.JqmClientFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

public class TestJQMConnection {
    String url = "http://q9lcaj00006frnt.labcorp.ad.ctc:32929/ws/client";
    String user = "root";
    String password = "Frontierjqm";
    JqmClient client;

    @Before
    public void setUp() {
        Properties properties = getJqmClientProperties();
        JqmClientFactory.setProperties(properties);
        try {
            client = JqmClientFactory.getClient();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void getJobsList() {
        List<JobInstance> jobs = client.getJobs();
        Assert.assertNotNull(jobs);
        Assert.assertTrue(jobs.size()>0);
    }


    private Properties getJqmClientProperties() {
        Properties p = new Properties();
        p.put("com.enioka.jqm.ws.url", url);
        p.put("com.enioka.jqm.ws.login", user);
        p.put("com.enioka.jqm.ws.password", password);
        return p;
    }



}
