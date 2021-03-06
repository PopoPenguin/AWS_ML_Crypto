package com.codingnomads.AWSMLCrypto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * created by Jialor Cheung on 12/29/17
 *
 * Generic API call for all histo data calls for <url>https://www.cryptocompare.com/api/#-api-data-</url>
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericHistoCall {

    String domain = "https://min-api.cryptocompare.com/data/";
    String type = "histohour";
    String fsym = "BTC";
    String tsym = "USD";
    String e = "CCCAGG";
    String extraParams;
    Boolean sign = false;
    Boolean tryConversion = true;
    Integer aggregate = 1;
    Integer limit = 50;
    long toTs = 0;

    /**
     * Constructor for GenericHistoCall
     *
     * @param type          Type of  histodata call being made; histoday, histominute, histohour, default value histohour - required
     * @param fsym          Name of From Symbol, default value BTC - required
     * @param tsym          Name of To Symbol, default value USD -required
     * @param e             Name of exchange, default value CCCAGG - required
     * @param extraParams   Name of your application - optional
     * @param sign          If set to true, the server will sign the requests. default value false - optional
     * @param tryConversion If set to false, it will try to get values without using any conversion at all, default value true - optional
     * @param aggregate    Number of aggregate to query by, default value 1 - optional
     * @param limit         Limit of results to return - optional
     * @param toTs          toTimestamp - optional
     */
    public GenericHistoCall(String type, String fsym, String tsym, String e, String extraParams,
                            Boolean sign, Boolean tryConversion, Integer aggregate, Integer limit, long toTs) {
        this.type = type;
        this.fsym = fsym;
        this.tsym = tsym;
        this.e = e;
        this.extraParams = extraParams;
        this.sign = sign;
        this.tryConversion = tryConversion;
        this.aggregate = aggregate;
        this.limit = limit;
        this.toTs = toTs;
    }

    //Getters and Setters for variables
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFsym() {
        return fsym;
    }

    public void setFsym(String fsym) {
        this.fsym = fsym;
    }

    public String getTsym() {
        return tsym;
    }

    public void setTsym(String tsym) {
        this.tsym = tsym;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(String extraParams) {
        this.extraParams = extraParams;
    }

    public Boolean getSign() {
        return sign;
    }

    public void setSign(Boolean sign) {
        this.sign = sign;
    }

    public Boolean getTryConversion() {
        return tryConversion;
    }

    public void setTryConversion(Boolean tryConversion) {
        this.tryConversion = tryConversion;
    }

    public Integer getAggregate() {
        return aggregate;
    }

    public void setAggregate(Integer aggregate) {
        this.aggregate = aggregate;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public long getToTs() {
        return toTs;
    }

    public void setToTs(long toTs) {
        this.toTs = toTs;
    }

    /**
     * Method to create the url api call specific to the desired parameters.  Builds the string URL based on the needed
     * parameters required in the cryptocompare api call and appends any additional parameters to the string
     *
     * Currently toTs and allData is causing a bug for all data to be returned regardless of parameters passed in
     * These are commented out for the method until bug fix can be resolved by cryptocompare
     *
     * @return  A string url built on defined parameters passed to be used for the api call
     */
    public String domainParams() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDomain()).append(getType()).append("?fsym=").append(getFsym())
        .append("&tsym=").append(getTsym()).append("&e=").append(getE());
        if (null != getExtraParams()) {
            sb.append("&").append("extraParams").append("=").append(getExtraParams());
        }
        if (false != getSign()) {
            sb.append("&").append("sign").append("=").append(getSign());
        }
        if (true != getTryConversion()) {
            sb.append("&").append("tryConversion").append("=").append(getTryConversion());
        }
        if (null != getAggregate()) {
            sb.append("&").append("aggregate").append("=").append(getAggregate());
        }
        if (null != getLimit()) {
            sb.append("&").append("limit").append("=").append(getLimit());
        }
        if (0 != getToTs()) {
            sb.append("&").append("toTs").append("=").append(getToTs());
        }
        return sb.toString();
    }
}
