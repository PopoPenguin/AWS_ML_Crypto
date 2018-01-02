package com.codingnomads.AWSMLCrypto.service;

import com.codingnomads.AWSMLCrypto.mapper.TestTableMapper;
import com.codingnomads.AWSMLCrypto.model.Data;
import com.codingnomads.AWSMLCrypto.model.HistoPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;

/**
 * Service methods for cryptocompare api calls.
 */

@Service
public class HistoService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TestTableMapper mapper;

    String domain = "https://min-api.cryptocompare.com/data/";

    /**
     * Creates a generic historical data api call from cryptocompare.com depending on the parameters included
     * By default histohour is set as default historical data type, BTC is set as default fsym, USD is set as default tsym
     *
     * @return histopojo object from restfull api call
     */
    public HistoPojo getHistoData (String type, String fsym, String tsym, String String e, String extraParams, Boolean sign, Boolean tryConversion, Integer aggregrate,
                                   Integer limit, Timestamp toTs, Boolean allData){
        //create a generic HistoDataCall
        GenericHistoCall genericHistoCall = new GenericHistoCall(type, fsym, tsym);
        HistoPojo histoPojo = restTemplate.getForObject(genericHistoCall.domainParams(), HistoPojo.class);
        insertHistoData(histoPojo.getData());
        return histoPojo;
    }

    public HistoPojo getHistoDay(){
        HistoPojo histoPojo =
    }


    //method to check if data exists in DB and if not insert data
    public void insertHistoData (Data[] data){

        Integer time;

        for(Data item: data){

            try {
                time = mapper.getTime(item.getTime());
            } catch (Exception e) {
                time = null;
            }
            // check to see if the time already exists in our DB
            if (time == null) {
                mapper.insertData(item);
            }
        }
    }

    /**
     * This method is used after the initial getHisto call is made, to fill in missing data between the last
     * historical data point and the first data point of the real-time call.
     * @return
     */
    public HistoPojo getBackload() {
        // get the most recent time that data was captured(in unixtime-seconds, convert to hours)
        long latestTime = (mapper.selectLatestTime()) / 3600;

        // get current time (in ms, convert to secs)
        long currentTimeSec =(long) (System.currentTimeMillis() * .001);
        // convert to hours
        long currentTimeHrs = currentTimeSec / 3600;

        // # of time increments between latest and current time (here we're using hrs)
        long timeDiff = (currentTimeHrs - latestTime);

        // call the API w/ limit = timeDiff (this specifies # of time increments we want to go back and get)
        HistoPojo histoPojo = restTemplate.getForObject(
                domain + "histominute?fsym=BTC&tsym=USD&limit=" + timeDiff +"&aggregate=1&e=CCCAGG",
                HistoPojo.class);
//      insertHistoData(histoPojo.getData());
        return histoPojo;
    }
}
