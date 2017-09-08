package org.qamation.jqm.utils;

import com.enioka.jqm.api.JobInstance;
import com.enioka.jqm.api.JqmClient;
import com.enioka.jqm.api.JqmClientFactory;
import com.enioka.jqm.api.State;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;
import java.util.Properties;

public class TestJQMConnection {
    String url = "http://q9lcaj00006frnt.labcorp.ad.ctc:32929/ws/client";
    //String url = "http://s0966-ftapq01.retail.ad.ctc:32929/ws/client";
    String user = "root";
    String password = "Frontierjqm";
    JqmClient client;
    List<JobInstance> jobs;

    @Before
    public void setUp() {
        Properties properties = getJqmClientProperties();
        JqmClientFactory.setProperties(properties);
        try {
            client = JqmClientFactory.getClient();
            jobs = client.getJobs();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void getJobsList() {
        Assert.assertNotNull(jobs);
        Assert.assertTrue(jobs.size()>0);
    }

    @Test
    public void getJobInfo() {
        jobs
                .stream()
                .filter(job -> job.getQueueName().equals("QBATCH"))
                .forEach(job -> {
                    System.out.println("Job from QBATCH: " + job.getId() + " -- " + job.getApplicationName());
                });

        jobs
                .stream()
                .filter(job -> job.getQueueName().equals("AIMMON"))
                .forEach(job -> {
                    System.out.println("Job from QBATCH: " + job.getId() + " -- " + job.getApplicationName());
                });
        JobInstance job = jobs.get(0);
        State s = job.getState();
        s.name();
    }

    @Test
    public void printQueueTable() {
        String header = getHeader();
        System.out.println(header);
        for(JobInstance job : jobs) {
            if (job != null) System.out.println(convertJob(job));
        }

    }

    private Properties getJqmClientProperties() {
        Properties p = new Properties();
        p.put("com.enioka.jqm.ws.url", url);
        p.put("com.enioka.jqm.ws.login", user);
        p.put("com.enioka.jqm.ws.password", password);
        return p;
    }

    @Test
    public void forJemeterInvocation() {
        String host = "q9lcaj00006frnt.labcorp.ad.ctc";
        String port = ":32929";
        String protocol = "http://";
        String path = "/ws/client";

        String url = protocol+host+port+path;


        String user = "root";
        String password = "Frontierjqm";

        JqmClient client;
        List<JobInstance> jobs;

        Properties p = new Properties();
        p.put("com.enioka.jqm.ws.url", url);
        p.put("com.enioka.jqm.ws.login", user);
        p.put("com.enioka.jqm.ws.password", password);

        try {
            JqmClientFactory.setProperties(p);

            client = JqmClientFactory.getClient();
            jobs = client.getJobs();
            for (JobInstance job: jobs) {
                System.out.println("ID: "+job.getId()+" Queue: "+job.getQueueName()+" App Name: "+job.getApplicationName()+" Status: "+job.getState().name());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private String convertJob(JobInstance job) {
        StringBuffer buff = new StringBuffer();
        buff.append(job.getApplication());buff.append(",");
        buff.append(job.getApplicationName());buff.append(",");
        buff.append(convertCalendar(job.getBeganRunningDate()));buff.append(",");
        buff.append(job.getDefinitionKeyword1());buff.append(",");
        buff.append(job.getDefinitionKeyword2());buff.append(",");
        buff.append(job.getDefinitionKeyword3());buff.append(",");
        buff.append(job.getEmail());buff.append(",");
        buff.append(convertCalendar(job.getEndDate()));buff.append(",");
        buff.append(convertCalendar(job.getEnqueueDate()));buff.append(",");
        buff.append(job.getId());buff.append(",");
        buff.append(job.getKeyword1());buff.append(",");
        buff.append(job.getKeyword2());buff.append(",");
        buff.append(job.getKeyword3());buff.append(",");
        buff.append(job.getModule());buff.append(",");
        buff.append(job.getNodeName());buff.append(",");
        buff.append(String.valueOf(job.getParent()));buff.append(",");
        buff.append(String.valueOf(job.getPosition()));buff.append(",");
        buff.append(String.valueOf(job.getPriority()));buff.append(",");
        buff.append(String.valueOf(job.getProgress()));buff.append(",");
        buff.append(job.getQueueName());buff.append(",");
        buff.append(job.getSessionID());buff.append(",");
        buff.append(job.getState().name());buff.append(",");
        buff.append(job.getUser());buff.append(",");
        buff.append(String.valueOf(job.isFromSchedule()));buff.append(",");
        buff.append(String.valueOf(job.isHighlander()));
        return buff.toString();
    }

    private String convertCalendar(Calendar c) {
        if (c == null) return "null";
        if (c.getTime() == null) return "null";
        return c.getTime().toString();
    }


    private String getHeader() {
        String header =
                "Application,"
                + "Application Name,"
                + "Began Running Date,"
                + "Def Keyword 1,"
                + "Def Keyword 2,"
                + "Def Keyword 3,"
                + "Email,"
                + "End Running Date,"
                + "Enqueued Date,"
                + "Job ID,"
                + "Keyword 1,"
                + "Keyword 2,"
                + "Keyword 3,"
                + "Module,"
                + "Node Name,"
                + "Parent,"
                + "Position,"
                + "Priority,"
                + "Progress,"
                + "Queue Name,"
                + "Session ID,"
                + "State,"
                + "User,"
                + "Is From Shedule,"
                + "Is Highlander";
        return header;
    }


}
