package com.gofishfarm.htkj.bean;

/**
 * @author：MrHu
 * @Date ：2019-01-09
 * @Describtion ：
 */
public class ChargeBean {

    /**
     * order_id : 2019010218021701878365067224273
     * phone : 18267857539
     * buyer : 67224273
     * buy_time : 5
     * price : 20
     * items : {"package_id":"20190102","pack_name":"5小时套餐","pack_time":5,"price":20,"number":"1"}
     */

    private String order_id;
    private String phone;
    private String buyer;
    private int buy_time;
    private int price;
    private ItemsBean items;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public int getBuy_time() {
        return buy_time;
    }

    public void setBuy_time(int buy_time) {
        this.buy_time = buy_time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ItemsBean getItems() {
        return items;
    }

    public void setItems(ItemsBean items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * package_id : 20190102
         * pack_name : 5小时套餐
         * pack_time : 5
         * price : 20
         * number : 1
         */

        private String package_id;
        private String pack_name;
        private int pack_time;
        private int price;
        private String number;

        public String getPackage_id() {
            return package_id;
        }

        public void setPackage_id(String package_id) {
            this.package_id = package_id;
        }

        public String getPack_name() {
            return pack_name;
        }

        public void setPack_name(String pack_name) {
            this.pack_name = pack_name;
        }

        public int getPack_time() {
            return pack_time;
        }

        public void setPack_time(int pack_time) {
            this.pack_time = pack_time;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
