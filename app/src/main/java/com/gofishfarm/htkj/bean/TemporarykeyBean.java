package com.gofishfarm.htkj.bean;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/15
 */
public class TemporarykeyBean {


    /**
     * credentials : {"sessionToken":"a88a7f72487f7f66ca104f5d2477971fa07d0ebc30001","tmpSecretId":"AKIDr11OczLR1K5bsYR4ZVX8WG1E9CIzgCUd","tmpSecretKey":"qFLt9v44CvQSrJuouOkKcJXns3cl1mSb"}
     * expiredTime : 1547537709
     * startTime : 1547535909
     */

    private CredentialsBean credentials;
    private String expiredTime;
    private String startTime;

    public CredentialsBean getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsBean credentials) {
        this.credentials = credentials;
    }

    public String getExpiredTime() {
        return expiredTime == null ? "" : expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getStartTime() {
        return startTime == null ? "" : startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public static class CredentialsBean {
        /**
         * sessionToken : a88a7f72487f7f66ca104f5d2477971fa07d0ebc30001
         * tmpSecretId : AKIDr11OczLR1K5bsYR4ZVX8WG1E9CIzgCUd
         * tmpSecretKey : qFLt9v44CvQSrJuouOkKcJXns3cl1mSb
         */

        private String sessionToken;
        private String tmpSecretId;
        private String tmpSecretKey;

        public String getSessionToken() {
            return sessionToken == null ? "" : sessionToken;
        }

        public void setSessionToken(String sessionToken) {
            this.sessionToken = sessionToken;
        }

        public String getTmpSecretId() {
            return tmpSecretId == null ? "" : tmpSecretId;
        }

        public void setTmpSecretId(String tmpSecretId) {
            this.tmpSecretId = tmpSecretId;
        }

        public String getTmpSecretKey() {
            return tmpSecretKey == null ? "" : tmpSecretKey;
        }

        public void setTmpSecretKey(String tmpSecretKey) {
            this.tmpSecretKey = tmpSecretKey;
        }
    }
}
