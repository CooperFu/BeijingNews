package io.naotou.beijingnews.bean;

import java.util.List;

/**
 * Created by Jack_Cooper on 2014/11/12.
 * Have a nice day!
 */

public class NewCenter {
    public List<NewCenterItem> data;
    public List<String> extend;
    public String retcode;

    public class NewCenterItem {
        public List<Children> children;
        public String id;
        public String title;
        public String type;
        public String url;
        public String url1;
        public String dayurl;
        public String excurl;
        public String weekurl;
    }

    public class Children {
        public String id;
        public String title;
        public String type;
        public String url;
    }
}
