package com.gofishfarm.htkj.ui.main.fishingpage;

import java.util.LinkedList;

/**
 * Created by Android Studio.
 * User: MrHu
 * Date: 2019-03-27
 * Time: 下午 11:29
 *
 * @Description:
 */

/**
 * 固定长度List
 * 如果List里面的元素个数大于了缓存最大容量，则删除链表的顶端元素
 *
 * @author lixiangjing
 */
public class FixSizeLinkedList<T> extends LinkedList<T> {
    private static final long serialVersionUID = 3292612616231532364L;
    // 定义缓存的容量
    private int capacity;

    public FixSizeLinkedList(int capacity) {
        super();
        this.capacity = capacity;
    }

    @Override
    public boolean add(T e) {
        // 超过长度，移除最后一个
        if (size() + 1 > capacity) {
            super.removeFirst();
        }
        return super.add(e);
    }
}
