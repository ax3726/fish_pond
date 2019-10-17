package com.gofishfarm.htkj.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.ArrayList;
import java.util.List;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/10
 */
public class CityBean implements IPickerViewData {

    /**
     * city_id : 1
     * name : 北京
     * sub_city : [{"city_id":35,"name":"北京"}]
     */

    private String city_id;
    private String name;
    private List<SubCityBean> sub_city;

    public String getCity_id() {
        return city_id == null ? "" : city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubCityBean> getSub_city() {
        if (sub_city == null) {
            return new ArrayList<>();
        }
        return sub_city;
    }

    public void setSub_city(List<SubCityBean> sub_city) {
        this.sub_city = sub_city;
    }

    @Override
    public String getPickerViewText() {
        return this.name;
    }

    public static class SubCityBean implements IPickerViewData{
        /**
         * city_id : 35
         * name : 北京
         */

        private String city_id;
        private String name;

        public String getCity_id() {
            return city_id == null ? "" : city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String getPickerViewText() {
            return this.name;
        }
    }
}
