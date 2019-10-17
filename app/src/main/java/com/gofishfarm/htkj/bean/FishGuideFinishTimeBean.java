package com.gofishfarm.htkj.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: MrHu
 * Date: 2019-03-22
 * Time: 下午 11:58
 *
 * @Description:
 */
public class FishGuideFinishTimeBean implements Serializable {

    /**
     * list : {"step1":1000,"step2":1000,"step3":1000,"step4":1000,"step5":1000,"step6":1000,"step7":1000,"step8":1000,"step9":1000,"step10":1000,"step11":1000}
     */

    private ListBean list;

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * step1 : 1000
         * step2 : 1000
         * step3 : 1000
         * step4 : 1000
         * step5 : 1000
         * step6 : 1000
         * step7 : 1000
         * step8 : 1000
         * step9 : 1000
         * step10 : 1000
         * step11 : 1000
         */

        private int step1;
        private int step2;
        private int step3;
        private int step4;
        private int step5;
        private int step6;
        private int step7;
        private int step8;
        private int step9;
        private int step10;
        private int step11;

        public int getStep1() {
            return step1;
        }

        public void setStep1(int step1) {
            this.step1 = step1;
        }

        public int getStep2() {
            return step2;
        }

        public void setStep2(int step2) {
            this.step2 = step2;
        }

        public int getStep3() {
            return step3;
        }

        public void setStep3(int step3) {
            this.step3 = step3;
        }

        public int getStep4() {
            return step4;
        }

        public void setStep4(int step4) {
            this.step4 = step4;
        }

        public int getStep5() {
            return step5;
        }

        public void setStep5(int step5) {
            this.step5 = step5;
        }

        public int getStep6() {
            return step6;
        }

        public void setStep6(int step6) {
            this.step6 = step6;
        }

        public int getStep7() {
            return step7;
        }

        public void setStep7(int step7) {
            this.step7 = step7;
        }

        public int getStep8() {
            return step8;
        }

        public void setStep8(int step8) {
            this.step8 = step8;
        }

        public int getStep9() {
            return step9;
        }

        public void setStep9(int step9) {
            this.step9 = step9;
        }

        public int getStep10() {
            return step10;
        }

        public void setStep10(int step10) {
            this.step10 = step10;
        }

        public int getStep11() {
            return step11;
        }

        public void setStep11(int step11) {
            this.step11 = step11;
        }
    }
}
